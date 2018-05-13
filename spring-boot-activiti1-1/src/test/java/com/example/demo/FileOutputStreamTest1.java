package com.example.demo;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

/** FileOutputStreamTest1**/
public class FileOutputStreamTest1 {

	@Test
	public void test1() throws IOException{
		FileOutputStream os = new FileOutputStream("D:/Test/Test1/testfile1.txt");
		StringBuilder sb = new StringBuilder();
		FileInputStream is = new FileInputStream("D:/Test/Test2/testfile2.txt");
		int b;
		while((b = is.read()) != -1){
			sb.append(b);
			os.write(b);
		}
		is.close();
		os.close();
		System.out.println(sb.toString());
	}
}
