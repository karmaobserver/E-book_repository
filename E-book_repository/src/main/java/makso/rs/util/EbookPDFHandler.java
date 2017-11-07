package makso.rs.util;

import java.io.File;

import org.apache.lucene.document.Document;
import org.springframework.web.multipart.MultipartFile;

import makso.rs.model.Ebook;
import makso.rs.udd.exception.IncompleteIndexDocumentException;
import makso.rs.udd.indexer.handler.PDFHandler;

public class EbookPDFHandler {

	public static Ebook createEbookFromPDF(File file,Long ebookId){
		
		PDFHandler pdfHandler = new PDFHandler();
		Document document = null;
		try {
			document = pdfHandler.getDocument(file);
		} catch (IncompleteIndexDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Ebook ebook = new Ebook();
		ebook.setAuthor(document.get("author"));
		ebook.setTitle(document.get("title"));
		if(ebookId!=null){
			ebook.setId(ebookId);
		}else{
			ebook.setId(System.currentTimeMillis());
		}
		String[] allKeywords = document.getValues("keyword");
		String 	temp = "";
		for(String keyword : allKeywords){
			temp += keyword + " ";
		}
		
		ebook.setKeywords(temp);
			
		return ebook;
		
	}
	
public static Ebook createEbookFromPDF(MultipartFile file){
		
		PDFHandler pdfHandler = new PDFHandler();
		Document document = null;
		try {
			document = pdfHandler.getDocument(file);
		} catch (IncompleteIndexDocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Ebook ebook = new Ebook();
		ebook.setAuthor(document.get("author"));
		ebook.setTitle(document.get("title"));
		
			ebook.setId(System.currentTimeMillis());
		
		String[] allKeywords = document.getValues("keyword");
		String 	temp = "";
		for(String keyword : allKeywords){
			temp += keyword + " ";
		}
		
		ebook.setKeywords(temp);
			
		return ebook;
		
	}
	
	
}
