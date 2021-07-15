package dummy.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHOR")
public class Author {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "AUTHORID")
	private long AuthorID;
	
	@Column(name = "FIRSTNAME")
	private String firstname;
	
	@Column(name = "LASTNAME")
	private String lastname;
	
	@ManyToMany(targetEntity = Book.class,
			cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "authors")
	public Set<Book> books = new HashSet<>();
}
