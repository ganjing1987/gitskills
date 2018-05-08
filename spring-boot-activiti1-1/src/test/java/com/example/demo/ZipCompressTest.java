package com.example.demo;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

public class ZipCompressTest {

	
	@Test
    public  void compress(){
		String srcFilePath = "D:/Test/Test1/test";
		String destFilePath = "D:/Test/Test2/ee.zip";
    	File src = new File(srcFilePath);
    	if(!src.exists()){
    		throw new RuntimeException(srcFilePath+"不存在");
    	}
    	File zipFile= new File(destFilePath);
    	try {
			FileOutputStream os = new FileOutputStream(zipFile);
			CheckedOutputStream cos = new CheckedOutputStream(os, new CRC32());
			ZipOutputStream zos = new ZipOutputStream(cos);
			String baseDir = "";
			compressbyType(src,zos,baseDir);
			zos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }

	private  void compressbyType(File src, ZipOutputStream zos, String baseDir) {
		System.out.println("压缩"+baseDir+src.getName());
		/**文件或者目录 **/
		if(src.isFile()){
			compressFile(src,zos,baseDir);
		}else if(src.isDirectory()){
			compressDirectory(src,zos,baseDir);
		}	
		
	}

	/**
	 * 压缩文件
	 * @param src
	 * @param zos
	 * @param baseDir
	 */
	private  void compressFile(File file, ZipOutputStream zos, String baseDir) {
	     try {
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
			ZipEntry entry = new ZipEntry(baseDir + file.getName());
			zos.putNextEntry(entry);
			int count;
			byte[] buf = new byte[1024];
			while((count = bis.read(buf)) != -1){
				zos.write(buf, 0, count);
			}
			bis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
	
	/**
	 * 压缩文件夹
	 * @param src
	 * @param zos
	 * @param baseDir
	 */
	private  void compressDirectory(File dir, ZipOutputStream zos, String baseDir) {
		File[] files = dir.listFiles();
		if(files.length  == 0){
			try {
				zos.putNextEntry(new ZipEntry(baseDir + dir.getName() + File.separator));
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
	  for(File file:files){
		  compressFile(file,zos,baseDir+dir.getName()+File.separator);
	  }
	}	
	
}
