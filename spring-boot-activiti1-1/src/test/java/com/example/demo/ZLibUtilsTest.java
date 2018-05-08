package com.example.demo;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

/**
 * ZLib压缩测试用例
 * @author Administrator
 *
 */
public class ZLibUtilsTest {
	
     
//	@Test
//	public final void testBytes(){
//		System.out.println("字节压缩/解压缩测试");
//		String inputStr = "snowolf@zlex.org;dongliang@zlex.org;zlex.dongliang@zlex.org";
//		System.out.println("输入字符串:\t"+inputStr);
//		byte[] input = inputStr.getBytes();
//		System.out.println("输入字节长度:\t"+input.length);
//		
//		byte[] data = ZLibUtils.compress(input);
//		System.out.println("压缩后字节长度:\t" + data.length); 
//		
//		byte[] output = ZLibUtils.decompress(data);
//		System.out.println("解压缩后字节长度:\t"+output.length);
//		String outputStr = new String(output);
//		System.err.println("输出字符串:\t"+outputStr);
//	    assertEquals(inputStr, outputStr);
//	}
	
	@Test
	public final void testFile(){
		String fileName = "D:/Test/Test2/ZLibss.gzip";
		File file = new File(fileName);
		
		System.out.println("文件压缩/解压缩测试");
		String inputStr = "snowolf@zlex.org;dongliang@zlex.org;zlex.dongliang@zlex.org";  
		System.err.println("输入字符串:\t" + inputStr); 
		byte[] input = inputStr.getBytes();
		System.out.println("输入字节长度:\t"+input.length);
		try{
			FileOutputStream fos = new FileOutputStream(file);
			ZLibUtils.compress(input,fos);
			fos.close();
			System.err.println("压缩后字节长度:"+file.length());
		  }catch(Exception e){
			fail(e.getMessage());
		}
		/*byte[] output = null;
		try {
			FileInputStream fis = new FileInputStream(file);
			output = ZLibUtils.decompress(fis);
		   fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("解压缩后字节长度:\t"+output.length);
		String outputStr = new String(output);
		System.err.println("输出字符串:\t"+outputStr);
		assertEquals(inputStr, outputStr);*/
	}
	   
	     
	
}
