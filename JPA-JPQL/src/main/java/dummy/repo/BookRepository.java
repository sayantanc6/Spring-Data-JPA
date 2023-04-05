package dummy.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dummy.entity.Book;
import dummy.projection.AuthorProjection;

@Repository
@Transactional(rollbackFor = {Exception.class}) 
public interface BookRepository extends CrudRepository<Book, Long> {

	@Query(value = "select b from Book as b inner join Author as a on a.authorid = :authorID ")
	public List<Book> findAllBooksgivenauthorID(@Param("authorID")long authorID);

	@Query("select new dummy.projection.AuthorProjection(a.authorid,a.firstname,a.lastname)"
			+ " from Book as b inner join Author as a "
			+ "on a.authorid in (?1)")
	public List<AuthorProjection> findAllBooksgivenAuthorids(List<Long> authorids);
}
