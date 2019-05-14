package com.xing.paper.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

public class FileUtils {
	/**
	 * 读取word\pdf文件内容
	 * 
	 * @param path
	 * @return buffer
	 */

	public static String reaFile(File path) {
		String buffer = "";
		try {
			if (path.getPath().endsWith(".doc")) {
				InputStream is = new FileInputStream(path);
				WordExtractor ex = new WordExtractor(is);
				buffer = ex.getText();
				is.close();
			} else if (path.getPath().endsWith("docx")) {
				OPCPackage opcPackage = POIXMLDocument.openPackage(path.getPath());
				POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
				buffer = extractor.getText();
				opcPackage.close();
			} else if (path.getPath().endsWith("pdf")) {
				buffer = PdfReader.readPdf(path);
			} else {
				System.out.println("此文件不是word/pdf文件！");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return buffer;
	}

	public static void inputStreamToFile(InputStream ins, File file) {
		try {
			OutputStream os = new FileOutputStream(file);
			int bytesRead = 0;
			byte[] buffer = new byte[8192];
			while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
				os.write(buffer, 0, bytesRead);
			}
			os.close();
			ins.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
//		String content = readWord("D:\\test01.doc");
//		System.out.println("content====" + content);
	}

}
