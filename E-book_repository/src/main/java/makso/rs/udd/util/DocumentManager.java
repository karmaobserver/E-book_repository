package makso.rs.udd.util;

import org.apache.lucene.document.Document;

public class DocumentManager {

	
	public static void printDocument(Document doc){
		
		doc.forEach(System.out::println);
		
	}
	
	
	
}
