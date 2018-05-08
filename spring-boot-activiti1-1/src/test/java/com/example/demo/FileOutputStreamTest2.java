package com.example.demo;

import java.io.FileInputStream;
import java.io.IOException;

import org.junit.Test;

public class FileOutputStreamTest2 {

	@Test
	public void test2() throws IOException{
		StringBuilder sb1  = new StringBuilder();
		StringBuilder sb2 = new StringBuilder();
		FileInputStream is = new FileInputStream("D:/Test/Test1/testfile1.txt");
		int b;
		while((b = is.read()) != -1){
			sb1.append((byte)b);
			sb2.append((char)b);
		}
		System.out.println(new String(sb1));
		System.out.println(new String(sb2));
		is.close();
	}
}
