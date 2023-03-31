
package dummy.projection;

// used as a Convertor from extracting managed Entity via Repository to a JPA Projection
public interface BookProjection {

	Long getIsbn(); // primitive is not allowed
	String getPrice();
	String getTitle();
	String getAuthor();
}
