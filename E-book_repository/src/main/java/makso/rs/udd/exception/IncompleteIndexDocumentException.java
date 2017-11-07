package makso.rs.udd.exception;

@SuppressWarnings("serial")
public class IncompleteIndexDocumentException extends Exception {
	
	public IncompleteIndexDocumentException(){
		super("Document is incomplete. Default metadata is not present");
	}
	
	public IncompleteIndexDocumentException(String message){
		super(message);
	}

}
