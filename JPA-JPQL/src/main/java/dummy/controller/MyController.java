package dummy.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
import dummy.repo.CustomUserAuthorRepository;
import dummy.repo.CustomUserBookRepository;

@RestController
public class MyController {
	
	@Autowired
	CustomUserBookRepository bookRepository;
	
	@Autowired
	CustomUserAuthorRepository authRepository;
	
	@GetMapping(value = "/findbooks",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> findAllbooks(Long authorid) {
		return bookRepository.findAllBooksgivenauthorID(authorid);
	}
	
	@PostMapping(value = "/addbooks",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json")
	public void addAllbooks(@RequestBody List<Book> books) {
		 bookRepository.insertAllItems(books, 5);
	}
	
	@PostMapping(value = "/addauthors",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json")
	public void addAllauthors(@RequestBody List<Author> authors) {
		 bookRepository.insertAllItems(authors, 0);
	}
	
	@GetMapping(value = "/addauthors",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Author> findAllauthors(Long isbn) {
		 return authRepository.findAllAuthorsgivenISBN(isbn);
	} 
	
	@PutMapping(value = "/updateauthors",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public void updateauthors(@RequestParam("firstname") String firstname, @RequestParam("lastname") String lastname,@RequestParam("isbn") Long isbn) {
		
		 authRepository.updateAllAuthor(findAllauthors(isbn), firstname, lastname, 5);
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