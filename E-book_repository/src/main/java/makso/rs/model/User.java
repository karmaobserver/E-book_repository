package makso.rs.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "USERS")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "USER_ID")
	private Long id;

	@Column(name = "FIRST_NAME")
	private String firstName;

	@Column(name = "LAST_NAME")
	private String lastName;

	@Column(name = "USERNAME", unique = true)
	private String username;

	@Column(name = "PASSWORD")
	private String password;

	@Column(name = "USER_TYPE")
	private String userType;
	
	@OneToMany(mappedBy = "users", cascade = CascadeType.ALL)
	@JsonBackReference(value="users-ebooks")
	private List<Ebook> ebooks;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CATEGORY")
	@JsonManagedReference(value="category-users")
	private Category category;

	public User(Long id, String firstName, String lastName, String username, String password, String userType,
			List<Ebook> ebooks, Category category) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.username = username;
		this.password = password;
		this.userType = userType;
		this.ebooks = ebooks;
		this.category = category;
	}

	public User() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public List<Ebook> getEbooks() {
		return ebooks;
	}

	public void setEbooks(List<Ebook> ebooks) {
		this.ebooks = ebooks;
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

	
	
	
}
