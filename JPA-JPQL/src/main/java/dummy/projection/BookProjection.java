package dummy.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class BookProjection {

	 private Long isbn;
	 private String author;
	 private String price;
	 private String title;

	 
	 
}
