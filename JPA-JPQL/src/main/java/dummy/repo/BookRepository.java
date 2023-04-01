package dummy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dummy.entity.Book;

@Repository
@Transactional(noRollbackFor = {Exception.class}) 
public interface BookRepository extends CrudRepository<Book, Long> {

	public void updateAll(List<Book> books,String author,String title,int batchSize);	
	
	@Query(value = "select b from Book as b inner join Author as a on a.Authorid = :authorID ")
	public List<Book> findAllBooksgivenauthorID(@Param("authorID")long authorID);
}
