package makso.rs.model;

import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import makso.rs.model.Language;

@Entity
public class Ebook implements Serializable {

	private static final long serialVersionUID = 1L;

	@javax.persistence.Id
	@org.springframework.data.annotation.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "KEYWORDS")
	private String keywords;
	
	@Column(name = "PUBLICATION_YEAR")	
	private Integer publicationYear;
	
	@Column(name = "FILE_NAME", unique = true)
	private String fileName;
	
	@Column(name = "MIME")
	private String mime;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "LANGUAGE")
	@JsonManagedReference(value="language-ebooks")
	private Language language;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "USERS")
	@JsonManagedReference(value="users-ebooks")
	private User users;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY")
	@JsonManagedReference(value="category-ebooks")
	private Category category;
	
	private String highlight;
	
	public Ebook(Long id, String title, String author, String keywords, Integer publicationYear, String fileName,
			String mime, Language language, User users, Category category) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.keywords = keywords;
		this.publicationYear = publicationYear;
		this.fileName = fileName;
		this.mime = mime;
		this.language = language;
		this.users = users;
		this.category = category;
	}

	public String getHighlight() {
		return highlight;
	}

	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}

	public Ebook() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getMime() {
		return mime;
	}

	public void setMime(String mime) {
		this.mime = mime;
	}

	public Language getLanguage() {
		return language;
	}

	public void setLanguage(Language language) {
		this.language = language;
	}

	public User getUsers() {
		return users;
	}

	public void setUsers(User users) {
		this.users = users;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Ebook [id=" + id + ", title=" + title + ", author=" + author + ", keywords=" + keywords
				+ ", publicationYear=" + publicationYear + ", fileName=" + fileName + ", mime=" + mime + ", language="
				+ language + ", users=" + users + ", category=" + category + ", highlight=" + highlight + "]";
	}
	
	
	
}
