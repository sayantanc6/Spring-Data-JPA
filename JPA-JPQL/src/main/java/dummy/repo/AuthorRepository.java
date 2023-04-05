package dummy.repo;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dummy.entity.Author;
import dummy.projection.BookProjection;

@Repository
@Transactional(rollbackFor = {Exception.class})
public interface AuthorRepository extends CrudRepository<Author, Long> {
	
	@Query("select a from Author a inner join Book b on b.isbn = :isbn")
	List<Author> findAllAuthorsgivenISBN(@Param("isbn")long isbn); 
	
	@Query("select new dummy.projection.BookProjection(b.isbn,b.author,b.price,b.title)" 
			     + " from Author as a inner join Book as b"
			     + " on b.isbn in (?1)")
	List<BookProjection> findAllAuthorsgivenISBNs(List<Long> isbns);
}
