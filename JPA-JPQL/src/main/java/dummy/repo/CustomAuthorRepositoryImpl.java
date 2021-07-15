package dummy.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dummy.entity.Author;

@Transactional
public class CustomAuthorRepositoryImpl implements CustomAuthorRepository {
	
	@Autowired
	EntityManager em; 
	
	@Override
	public void updateAllAuthor(List<Author> authors,String firstname,String lastname,int batchsize) {
		TypedQuery<Author> authorQuery =  em.createQuery("SELECT a from AUTHOR a", Author.class);
	    List<Author> author1 = authorQuery.getResultList();
	    for (Author author : author1) {
	    	author.setFirstname(firstname);
	    	author.setLastname(lastname);
	    	batchsize--;
	    	if (batchsize ==0) {
				em.flush();
				em.clear();
			}
	    }
	}
	
  // select a.* from author a join book b where b.isbn = :isbn order by a.authorid;
	@Override
	public List<Author> findAllAuthorsgivenISBN(long isbn) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Author> cq = cb.createQuery(Author.class);
		Root<Author> authoroot = cq.from(Author.class);
		cq.multiselect(authoroot.get("AuthorID"),authoroot.get("firstname"),authoroot.get("lastname")).distinct(true);
		Join<Object, Object> book = authoroot.joinSet("books",JoinType.INNER);
		ParameterExpression<Long> pisbn = cb.parameter(long.class);
		cq.where(cb.equal(book.get("isbn"), pisbn));
		cq.orderBy(cb.asc(authoroot.get("AuthorID")));
		return em.createQuery(cq).setParameter(pisbn, isbn).getResultList(); 
	}

}
