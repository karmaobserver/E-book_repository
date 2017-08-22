package makso.rs.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

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

}
