package dummy.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dummy.entity.Author;

@Repository
@Transactional(rollbackFor = {Exception.class})
public interface AuthorRepository extends CrudRepository<Author, Long> {

	void updateAllAuthor(List<Author> authors);
	
	@Query("select a from Author a inner join Book b on b.isbn = :isbn")
	List<Author> findAllAuthorsgivenISBN(@Param("isbn")long isbn); 
}
