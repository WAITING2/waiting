package com.xing.paper.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

public class PdfReader {
	/** 
     * simply reader all the text from a pdf file.  
     * You have to deal with the format of the output text by yourself. 
     * 2008-2-25 
     * @param pdfFilePath file path 
     * @return all text in the pdf file 
     */  
	public static String readPdf(File path) { 
		String content = null; 
		try { 
			 // 是否排序 
			 boolean sort = false; 
			 // 开始提取页数 
			 int startPage = 1; // 结束提取页数 
			 int endPage = Integer.MAX_VALUE;
			 PDDocument document = PDDocument.load(path); 
			 PDFTextStripper pts = new PDFTextStripper(); 
			 endPage = document.getNumberOfPages();
			 System.out.println("Total Page: " + endPage); 
			 pts.setStartPage(startPage); 
			 pts.setEndPage(endPage); 
			 try { 
				 content = pts.getText(document); 
			 } catch (Exception e) {
					 e.printStackTrace();
			 } finally { 
				 if (null != document) {
						document.close(); 
				 }
			 }
		 }catch(Exception e) {
			 e.printStackTrace();
		 }
		System.out.println("Get PDF Content ...");
		return content;
	}

	public static void main(String[] args) {
//		String content = readPdf("D:\\test01.pdf");
//		System.err.println("content==="+content);
	}
}