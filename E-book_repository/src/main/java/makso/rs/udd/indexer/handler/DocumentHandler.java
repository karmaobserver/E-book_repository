package makso.rs.udd.indexer.handler;

import java.io.File;

import org.apache.lucene.document.Document;

import makso.rs.dto.EbookViewModel;
import makso.rs.model.Ebook;
import makso.rs.udd.exception.IncompleteIndexDocumentException;

public abstract class DocumentHandler {
	public abstract Document getDocument(File file) throws IncompleteIndexDocumentException;
	
	public abstract Document getDocument(File file, Ebook ebook) throws IncompleteIndexDocumentException;
}
