package com.example.demo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.junit.Test;

public class ZipUtils {

	private static final int BUFFER_SIZE = 2 * 1024;
	
	/**
	 * 压缩成Zip方法1
	 * @param srcDir 压缩文件夹路径
	 * @param out 压缩文件输出流
	 * @param keepDirStructure 是否保留原来的目录结构,true保留目录结构;false:所有文件跑到压缩包根目录下(注意：不保留目录结构可能会出现同名的文件，压缩失败)
	 * throws RuntimeExcetpion 压缩失败会抛出运行时异常
	 * @throws IOException 
	 */
	@Test
	public void toZip() throws IOException{
		/**压缩方法1 **/
		File file = new File("D:/Test/Test2/cc.zip");
//		if(!file.exists()){
//			file.createNewFile();
//		}
		FileOutputStream out = new FileOutputStream(file);
		String srcDir = "D:/Test/Test1/test";
		boolean keepDirStructure = true;		
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try{
			zos = new ZipOutputStream(out);
			File sourceFile = new File(srcDir);
			compress(sourceFile,zos,sourceFile.getName(),keepDirStructure);
			long end = System.currentTimeMillis();
			System.out.println("压缩完成耗时:"+(end - start) + "ms");
		}catch(Exception e){
			throw new RuntimeException("zip error from ZipUtil",e);
		}finally{
			if(zos != null){
				try{
					zos.close();
				}catch(IOException e){
				   e.printStackTrace();		
				}
			}
		}
	}

	/**
	 * 压缩zip方法2
	 * @param srcFiles
	 * @param out
	 * @throws RuntimeException
	 */
	public void toZip(List<File> srcFiles,OutputStream out) throws RuntimeException{
		long start = System.currentTimeMillis();
		ZipOutputStream zos = null;
		try{
			zos = new ZipOutputStream(out);
			for(File srcFile:srcFiles){
				byte[] buf = new byte[BUFFER_SIZE];
				zos.putNextEntry(new ZipEntry(srcFile.getName()));
				int len;
				FileInputStream in = new FileInputStream(srcFile);
				while((len = in.read(buf)) != -1){
					zos.write(buf, 0, len);
				}
				zos.closeEntry();
				in.close();
			}
			long end = System.currentTimeMillis();
			System.out.println("压缩完成,耗时："+(end - start) + "ms");
		 }catch(Exception e){
			throw new RuntimeException("zip error from ZipUtls"+ e);
		 }finally {
			if(zos != null){
				try {
					zos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} 
	}
	
	/**
	 * 递归压缩方法
	 * @param sourceFile
	 * @param zos
	 * @param name
	 * @param keepDirStructure
	 * @throws IOException 
	 */
	private void compress(File sourceFile, ZipOutputStream zos, String name, boolean keepDirStructure) throws Exception {
		byte[] buf = new byte[BUFFER_SIZE];
		if(sourceFile.isFile()){
			//向zip输出流中加一个zip实体,构造器中name为zip实体的文件名字
			zos.putNextEntry(new ZipEntry(name));
			//copy文件到zip输出流
			int len;
			FileInputStream in = new FileInputStream(sourceFile);
			while((len = in.read(buf)) != -1){
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
			in.close();
		}else{
		   File[] listFiles = sourceFile.listFiles();
		   if(listFiles == null || listFiles.length == 0){
			   //需要保留原来的文件结构时，需要对空文件夹处理
			   if(keepDirStructure){
				   //空文件夹的处理
				   zos.putNextEntry(new ZipEntry(name + "/"));
				   zos.closeEntry();
			   }
		   }else{
			   for(File file:listFiles){
				   //判断是否需要保留原来的文件结构
				   if(keepDirStructure){
					   //注意:file.getName()前面需要带上父文件夹的名字加一斜杠
					   //不然最后压缩包中就不能保留原来的文件结构,即所有文件都跑到压缩包根目录了
					   compress(file,zos,name+"/"+file.getName(),keepDirStructure);
				   }else{
					   compress(file,zos,name+file.getName(),keepDirStructure);
				   }
			   }
		   }
		}
	}
	
	
	
}
