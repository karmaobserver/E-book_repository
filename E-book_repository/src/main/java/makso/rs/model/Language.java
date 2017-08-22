package makso.rs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import makso.rs.model.Ebook;

@Entity
@Table(name = "LANGUAGE")
public class Language implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "LANGUAGE_ID")
	private Long id;

	@Column(name = "NAME")
	private String name;
	
	@OneToMany(mappedBy = "language", cascade = CascadeType.ALL)
	private List<Ebook> ebooks;


	public Language(Long id, String name, List<Ebook> ebooks) {
		super();
		this.id = id;
		this.name = name;
		this.ebooks = ebooks;
	}

	public Language() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Ebook> getEbooks() {
		return ebooks;
	}

	public void setEbooks(List<Ebook> ebooks) {
		this.ebooks = ebooks;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	

}
