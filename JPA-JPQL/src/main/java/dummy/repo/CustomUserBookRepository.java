package dummy.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dummy.entity.Book;

@Repository
public interface CustomUserBookRepository extends CrudRepository<Book, Long>,CustomBookRepository {

}
