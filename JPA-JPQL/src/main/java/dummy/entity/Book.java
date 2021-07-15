package dummy.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ColumnTransformer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "BOOK")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ISBN")
	private long isbn;
	
	@Column(name = "AUTHOR")
	private String author;
	  
	@ColumnTransformer(read = "pgp_sym_decrypt(title, 'encrypt.key')",
					   write = "pgp_sym_encrypt(?, 'encrypt.key')")
	@Column(name = "TITLE")
	private String title;
	
	@Column(name = "PRICE")
	private double price;
	
	@ManyToMany(targetEntity = Author.class,
			cascade = {CascadeType.PERSIST,CascadeType.DETACH,CascadeType.MERGE,CascadeType.REFRESH})
	@JoinTable(name = "BOOK_AUTHOR",
			  joinColumns = @JoinColumn(name = "ISBN"),
			  inverseJoinColumns = @JoinColumn(name = "AUTHORID"))
	private Set<Author> authors = new HashSet<>();
}
