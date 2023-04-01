package dummy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dummy.entity.Author;
import dummy.entity.Book;
import dummy.repo.AuthorRepository;
import dummy.repo.BookRepository;

@RestController
public class MyController {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	AuthorRepository authRepository;
	
	@ConditionalOnProperty(name = "withprojections",havingValue = "no")
	@GetMapping(value = "/findbooks",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> findAllbooks(@RequestParam("authorid")Long authorid) {
		return bookRepository.findAllBooksgivenauthorID(authorid); 
	}
	
	@PostMapping(value = "/addbooks",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json")
	public void addAllbooks(@RequestBody List<Book> books) {
		 bookRepository.saveAll(books); 
	}
	
	@PostMapping(value = "/addauthors",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json")
	public void addAllauthors(@RequestBody List<Author> authors) {
		 authRepository.saveAll(authors); 
	}
	
	@ConditionalOnProperty(name = "withprojections",havingValue = "no")
	@GetMapping(value = "/findallauthors",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Author> findAllauthors(@RequestParam("isbn") Long isbn) {
		 return authRepository.findAllAuthorsgivenISBN(isbn);
	}
	
	@PutMapping(value = "/updateauthors",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateauthors(@RequestParam("authors")List<Author> authors) {
		
		 authRepository.updateAllAuthor(authors);
	} 
	
	@DeleteMapping(value = "/deletebooks",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deletebooks() {
		 bookRepository.deleteAll();
	} 
	
	@DeleteMapping(value = "/deleteauthors",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void deleteauthors() {
		 authRepository.deleteAll();
	}
	
}
