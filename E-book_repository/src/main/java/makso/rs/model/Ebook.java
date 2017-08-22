package makso.rs.model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "EBOOK")
public class Ebook implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "EBOOK_ID")
	private Long id;

	@Column(name = "TITLE")
	private String title;

	@Column(name = "LANGUAGE")
	private String language;

	@Column(name = "CATEGORY")
	private String category;

	@Column(name = "AUTHOR")
	private String author;

	@Column(name = "KEYWORDS")
	private String keywords;
	
	@Column(name = "PUBLICATION_YEAR")
	private Integer publicationYear;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "MIME")
	private String mime;
	
}
