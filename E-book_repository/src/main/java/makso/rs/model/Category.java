package makso.rs.model;

import java.io.Serializable;
import javax.persistence.*;

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

	public Category(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}

	public Category() {
		super();
	}
	
	

}