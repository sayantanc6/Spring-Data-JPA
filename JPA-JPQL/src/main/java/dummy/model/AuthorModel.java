package dummy.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.SerializedName;

import dummy.entity.Book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Component
@Data
@AllArgsConstructor 
@NoArgsConstructor
public class AuthorModel {
	
	@SerializedName("authorid")
	private long authorid;
	
	@NotNull
	@SerializedName("firstname")
	private String firstname;
	
	@NotNull
	@SerializedName("lastname")
	private String lastname;
	
	@JsonProperty("books")
	public Set<Book> books = new HashSet<>();
}
