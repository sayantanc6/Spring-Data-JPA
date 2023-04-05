package dummy.projection;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorProjection {

	private Long authorid;
	private String firstname;
	private String lastname;
	
	
}
