package dummy.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

//import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"authors"}) // here's also another choice for solving infinite recursion
public class Book {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long ISBN;
	
	private String author;
	  

	private String price;
	
	private String title;
	
	@ManyToMany(targetEntity = Author.class,fetch = FetchType.EAGER,
			cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name = "book_author",
			  joinColumns = @JoinColumn(name = "Book"),
			  inverseJoinColumns = @JoinColumn(name = "Author"))
	 // to avoid stackoverflowerror for recursive calls using manytomany
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	/* to avoid avoid infinite recursion 
	 * without ignoring getters/setters during serialization.
	 * It's the back part of reference.
	 * Also used in @ManyToOne
	 */
	//@JsonBackReference
	private Set<Author> authors = new HashSet<>();
}
