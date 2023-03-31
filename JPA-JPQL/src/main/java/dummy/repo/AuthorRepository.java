package dummy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dummy.entity.Author;
import dummy.projection.AuthorProjection;

@Repository
@Transactional
public interface AuthorRepository extends CrudRepository<Author, Long> {

	void updateAllAuthor(List<Author> authors);
	
	/* using naming convention for query alias value depends on get*() method for JPA close Projection
	  * So whatever naming convention for column name,change it as alias name for all lowercase letters..
	  * then getter methods for JPA close Projection should be get<first-uppercase-letter><rest-all-lowercase-letters>
	  */
	@Query("select a.Authorid as authorid,a.firstname as firstname,a.lastname as lastname"
			+ " from Author a "
			+ "inner join Book b "
			+ "on b.ISBN = :isbn")
	List<AuthorProjection> findAllAuthorsgivenISBN(@Param("isbn")long isbn); 
}
