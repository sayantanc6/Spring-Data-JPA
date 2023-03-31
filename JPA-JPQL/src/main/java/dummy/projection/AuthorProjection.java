package dummy.projection;

//used as a Convertor from extracting managed Entity via Repository to a JPA Projection
public interface AuthorProjection {
	
	Long getAuthorid(); // primitive is not allowed
	String getFirstname(); 
	String getLastname();

}
