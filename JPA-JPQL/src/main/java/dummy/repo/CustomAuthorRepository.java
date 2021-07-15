package dummy.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import dummy.entity.Author;

@Repository
public interface CustomAuthorRepository {

	void updateAllAuthor(List<Author> authors,String firstname,String lastname,int batchSize);
	List<Author> findAllAuthorsgivenISBN(long isbn); 
}
