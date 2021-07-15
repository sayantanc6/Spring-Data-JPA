package dummy.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import dummy.entity.Author;

@Repository
public interface CustomUserAuthorRepository extends CrudRepository<Author, Long>,CustomAuthorRepository {

}
