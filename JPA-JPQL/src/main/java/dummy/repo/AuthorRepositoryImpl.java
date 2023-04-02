package dummy.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import dummy.entity.Author;

@Component
public class AuthorRepositoryImpl {

	@PersistenceContext
	EntityManager em; 
	
	@SuppressWarnings("unused")
	public void updateAllAuthor(List<Author> authors) {
		int batchsize = 100;
		TypedQuery<Author> authorQuery =  em.createQuery("SELECT a from AUTHOR a", Author.class);
	    List<Author> author1 = authorQuery.getResultList();
	    for (Author author : author1) {
	    	author.setAuthorid(author.getAuthorid()); 
	    	author.setFirstname(author.getFirstname());
	    	author.setLastname(author.getLastname());
	    	batchsize--;
	    	if (batchsize ==0) {
				em.flush();
				em.clear();
			}
	    }
	}
}
