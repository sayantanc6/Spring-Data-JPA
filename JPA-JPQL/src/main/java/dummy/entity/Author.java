package dummy.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = {"books","authorid"}) // here's also another choice for solving infinite recursion
public class Author {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private long authorid;
	
	private String firstname;
	
	private String lastname;
	
	@ManyToMany(targetEntity = Book.class,fetch = FetchType.EAGER,
			cascade = {CascadeType.ALL}, //to delete entities
			mappedBy = "authors")
	// to avoid stackoverflowerror for recursive calls using manytomany
	@EqualsAndHashCode.Exclude
	@ToString.Exclude
	/* to avoid avoid infinite recursion 
	 * without ignoring getters/setters during serialization.
	 * It's the forward part of reference.
	 * Also used in @OneToMany
	 */
	//@JsonManagedReference
	public Set<Book> books = new HashSet<>();
	
}
