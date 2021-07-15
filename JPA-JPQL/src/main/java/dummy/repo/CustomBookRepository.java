package dummy.repo;

import java.util.List;

import org.springframework.stereotype.Repository;

import dummy.entity.Author;
import dummy.entity.Book;

@Repository
public interface CustomBookRepository {

	void insertAllItems(List<?> collections,int BatchSize);
	 void updateAll(List<Book> books,String author,String title,int batchSize);
	 List<Book> findAllBooksgivenauthorID(long authorID);
}
