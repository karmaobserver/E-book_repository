package makso.rs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "CATEGORY")
public class Category implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "CATEGORY_ID")
	private long id;

	@Column(name = "NAME")
	private String name;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<Ebook> ebooks;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonBackReference
	private List<User> users;

	public long getId() {
		return id;
	}

	public void setId(long id) {
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
	

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Category(long id, String name, List<Ebook> ebooks, List<User> users) {
		super();
		this.id = id;
		this.name = name;
		this.ebooks = ebooks;
		this.users = users;
	}

	public Category() {
		super();
	}
	
	

}