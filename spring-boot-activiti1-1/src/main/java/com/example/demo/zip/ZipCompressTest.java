package com.example.demo.zip;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class ZipCompressTest {

	private static final int BUFFER_SIZE = 1024;
	
	private File zipFile;  
	
	/**ZipCompressTest **/
	public ZipCompressTest(String pathName){
		 try {
			 zipFile = new File(pathName); 
			 if(!zipFile.exists())
			 {
			   zipFile.createNewFile();
			 }
		  } catch (IOException e) {
			e.printStackTrace();
	   }
	}
	
	public void compress(String srcPathName){
		File file = new File(srcPathName);
		if(!file.exists()){
			throw new RuntimeException(srcPathName);
		}
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(fos,new CRC32());
			ZipOutputStream zos = new ZipOutputStream(cos);
			//System.getProperties().put("file.encoding", "GBK");
			System.getProperties().list(System.out);
			zos.setEncoding("utf-8");
			String basedir = "";
			compress(file,zos,basedir);
			zos.close();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void compress(File file, ZipOutputStream zos, String basedir) {
		/**压缩目录 **/
		if(file.isDirectory()){
			System.out.println("压缩目录:"+basedir+file.getName());
			this.compressDirectory(file,zos,basedir);
		}else{
			System.out.println("压缩文件:"+basedir+file.getName());
			this.compressFile(file,zos,basedir);
		}
	}
   
	/**压缩文件 **/
	private void compressFile(File file, ZipOutputStream zos, String basedir) {
		if(!file.exists()){
			return;
		}
		try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(basedir + file.getName());
			zos.putNextEntry(entry);
			int count;
			byte data[] = new byte[BUFFER_SIZE];
			while((count = bis.read(data,0,BUFFER_SIZE)) != -1){
				zos.write(data, 0, count);
			}
			bis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
     
	
	private void compressDirectory(File file, ZipOutputStream zos, String basedir) {
		if(!file.exists()){
			return;
		}
		File[] files = file.listFiles();
		for(int i=0;i<files.length;i++){
			compress(files[i],zos,basedir+file.getName()+"/");
		}
	}
	
	public static void main(String[] args){
		ZipCompressTest zc = new ZipCompressTest("D:/Test/Test2/test.zip");
		zc.compress("D:/Test/Test1");
//		try {
//			//RandomAccessFile raf = new RandomAccessFile("D:/Test/Test1/发生师傅啊ЧЩЩЩЩЩннптччч.txt","r");
//			//System.out.println(new String(raf.readLine().getBytes("iso8859-1"),"gbk"));
//			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("D:/Test/Test1/发生师傅啊ЧЩЩЩЩЩннптччч.txt")));
//		//	OutputStream os = new OutputStream(new )
//			System.out.println(bis.read());
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		File file = new File("D:/Test/Test1/发生师傅啊ЧЩЩЩЩЩннптччч.txt");
//        InputStream input;
//		try {
//			input = new FileInputStream(file);
//			StringBuffer buffer = new StringBuffer();
//			System.getProperties().list(System.out);
//	        byte[] bytes = new byte[1024];
//	        for(int n ; (n = input.read(bytes))!=-1 ; ){
//	            buffer.append(new String(bytes,0,n,"gbk"));
//	        }
//	        System.out.println(buffer);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		
//		try{
//			//String file = "D:/Test/Test1/发生师傅啊ЧЩЩЩЩЩннптччч.txt"; 
//			File file = new File("D:/Test/Test1/发生师傅啊ЧЩЩЩЩЩннптччч.txt");
//	        String charset = "UTF-8"; 
//	        // 写字符换转成字节流
//	        FileOutputStream outputStream = new FileOutputStream(file); 
//	        OutputStreamWriter writer = new OutputStreamWriter(outputStream, charset); 
//	        try { 
//	           writer.write("我是 cm"); 
//	        } finally { 
//	           writer.close(); 
//	        } 
//	        // 读取字节转换成字符
//	        FileInputStream inputStream = new FileInputStream(file); 
//	        InputStreamReader reader = new InputStreamReader( 
//	        inputStream, charset); 
//	        StringBuffer buffer = new StringBuffer(); 
//	        char[] buf = new char[64]; 
//	        int count = 0; 
//	        try { 
//	           while ((count = reader.read(buf)) != -1) { 
//	               buffer.append(buf, 0, count); 
//	           } 
//	        } finally { 
//	           reader.close(); 
//	        }
//	        System.out.println(buffer);
//		}catch(Exception e){
//			System.out.println(e);
//		}
	}
	
}
