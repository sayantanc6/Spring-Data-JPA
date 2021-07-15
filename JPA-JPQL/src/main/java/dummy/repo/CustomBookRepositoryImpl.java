package dummy.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import dummy.entity.Book;
@Transactional
public class CustomBookRepositoryImpl implements CustomBookRepository {
	
	@Autowired
	EntityManager em;

	@Override
	public void updateAll(List<Book> books,String author,String title,int batchSize) {
		
		TypedQuery<Book> bookQuery =  em.createQuery("SELECT s from School s", Book.class);
			    List<Book> books1 = bookQuery.getResultList();
			    for (Book book : books1) {
			    	book.setAuthor(author);
			    	book.setTitle(title);
			    	batchSize--;
			    	if (batchSize ==0) {
						em.flush();
						em.clear();
					}
			    }
	}

	@Override
	public void insertAllItems(List<?> collections,int batchSize) {
		EntityTransaction entityTransaction = em.getTransaction();
		try { 
		    entityTransaction.begin();
		 
		    for (int i = 0; i < collections.size(); i++) {
		        if (i > 0 && i % batchSize == 0) {
		            entityTransaction.commit();
		            entityTransaction.begin();
		 
		            em.clear();
		        }
		         
		        em.persist(collections.get(i)); 
		    }
		 
		    entityTransaction.commit();
		} catch (RuntimeException e) {
		    if (entityTransaction.isActive()) {
		        entityTransaction.rollback();
		        
		    }
		    throw e;
		} finally {
		    em.close();
		}
	}
	
// select b.* from book b join author a where a.authorid = :authorid order by b.isbn;
	@Override
	public List<Book> findAllBooksgivenauthorID(long authorID) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Book> cq = cb.createQuery(Book.class);
		Root<Book> bookroot = cq.from(Book.class);
		cq.multiselect(bookroot.get("ISBN"),bookroot.get("title"),bookroot.get("price"),bookroot.get("author")).distinct(true);
		Join<Object, Object> author = bookroot.joinSet("authors",JoinType.INNER);
		ParameterExpression<Long> pauthorid = cb.parameter(long.class);
		cq.where(cb.equal(author.get("authorid"), pauthorid));
		cq.orderBy(cb.asc(bookroot.get("ISBN")));
		return em.createQuery(cq).setParameter(pauthorid, authorID).getResultList();
	}
}
