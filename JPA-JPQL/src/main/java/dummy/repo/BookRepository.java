package dummy.repo;

import java.util.List;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dummy.entity.Book;
import dummy.projection.BookProjection;

@Repository
@Transactional
public interface BookRepository extends CrudRepository<Book, Long> {

	public void updateAll(List<Book> books,String author,String title,int batchSize);
	public void insertAllItems(List<?> collections,int batchSize);
	
	 /* using naming convention for query alias value depends on get*() method for JPA close Projection
	  * So whatever naming convention for column name,change it as alias name for all lowercase letters..
	  * then getter methods for JPA close Projection should be get<first-uppercase-letter><rest-all-lowercase-letters>
	  */
	@Query(value = "select b.ISBN as isbn,b.author as author,b.title as title,b.price as price"
			+ " from Book as b"
			+ " inner join Author as a"
			+ " on a.Authorid = :authorID ")
	public List<BookProjection> findAllBooksgivenauthorID(@Param("authorID")long authorID);
}
