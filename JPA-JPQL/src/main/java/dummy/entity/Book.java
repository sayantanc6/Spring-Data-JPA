package dummy.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long ISBN;
	
	private String author;
	  
	private String price;
	
	private String title;
	
	@ManyToMany(targetEntity = Author.class,
			cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name = "book_author",
			  joinColumns = @JoinColumn(name = "ISBN"),
			  inverseJoinColumns = @JoinColumn(name = "authorid"))
	private Set<Author> authors = new HashSet<>();
}
