package dummy.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.google.gson.annotations.SerializedName;

import dummy.entity.Author;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookModel {
	
	@SerializedName("isbn") 
	private long isbn;
	
	@NotNull
	@SerializedName("author")
	private String author;
	  
	@NotNull
	@SerializedName("price")
	private String price;
	
	@NotNull
	@SerializedName("title")
	private String title;
	
	@SerializedName("authors")
	private Set<Author> authors = new HashSet<>();
}
