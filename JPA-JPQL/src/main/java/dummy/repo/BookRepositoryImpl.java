package dummy.repo;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import dummy.entity.Book;

@Component
public class BookRepositoryImpl{

	@PersistenceContext
	EntityManager em;
	
	@SuppressWarnings("unused")
	public void updateAll(List<Book> books,String author,String title,int batchSize) {
		
		TypedQuery<Book> bookQuery =  em.createQuery("SELECT s from School s", Book.class);
			    books = bookQuery.getResultList();
			    for (Book book : books) {
			    	book.setAuthor(author);
			    	book.setTitle(title);
			    	batchSize--;
			    	if (batchSize ==0) {
						em.flush();
						em.clear();
					}
			    }
	}

	@SuppressWarnings("unused")
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
}
