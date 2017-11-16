package makso.rs.udd.model;

public class EbookDocument {

	private Integer ebookId;	
	private String title;
	private String author;
	private String keywords;
	private Integer publicationYear;
	private String filename;
	private String mime;
	private String language;
	private String category;
	private String user;
	
	public EbookDocument(Integer ebookId, String title, String author, String keywords, Integer publicationYear,
			String filename, String mime, String language, String category, String user) {
		super();
		this.ebookId = ebookId;
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.publicationYear = publicationYear;
		this.filename = filename;
		this.mime = mime;
		this.language = language;
		this.category = category;
		this.user = user;
	}

	public EbookDocument() {
		super();
	}

	public Integer getEbookId() {
		return ebookId;
	}

	public void setEbookId(Integer ebookId) {
		this.ebookId = ebookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Integer getPublicationYear() {
		return publicationYear;
	}

	public void setPublicationYear(Integer publicationYear) {
		this.publicationYear = publicationYear;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
		
}
