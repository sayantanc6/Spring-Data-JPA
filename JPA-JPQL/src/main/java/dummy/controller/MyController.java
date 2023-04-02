package dummy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dummy.entity.Author;
import dummy.entity.Book;
import dummy.model.AuthorModel;
import dummy.model.BookModel;
import dummy.repo.AuthorRepository;
import dummy.repo.BookRepository;

@RestController
public class MyController {
	
	@Autowired
	BookRepository bookRepository;
	
	@Autowired
	AuthorRepository authRepository;
	
	@Autowired
	Gson gson;
	
	@ConditionalOnProperty(name = "withprojections",havingValue = "no")
	@GetMapping(value = "/findbooks",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json",consumes = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> findAllbooks(@RequestParam("authorid")Long authorid) {
		return bookRepository.findAllBooksgivenauthorID(authorid); 
	}
	 
	@PostMapping(value = "/addbooks",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json")
	public void addAllbooks(@Validated @RequestBody List<BookModel> books) {
		System.out.println("ser bookmodel : \n"+gson.toJson(books)); 
		System.out.println("deser bookmodel : \n"+gson.fromJson(gson.toJson(books), new TypeToken<List<Book>>() {}.getType()));
			bookRepository.saveAll(gson.fromJson(gson.toJson(books), new TypeToken<List<Book>>() {}.getType()));  
	}
	
	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> addPostExceptions(MethodArgumentNotValidException ex){
		Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
	}
	
	@PostMapping(value = "/addauthors",produces = MediaType.APPLICATION_JSON_VALUE,
			headers = "Accept=application/json")
	public void addAllauthors(@Validated @RequestBody List<AuthorModel> authors) {
		System.out.println("ser authormodel : \n"+gson.toJson(authors));  
		System.out.println("deser authormodel : \n"+gson.fromJson(gson.toJson(authors), new TypeToken<List<Author>>() {}.getType()));
			authRepository.saveAll(gson.fromJson(gson.toJson(authors), new TypeToken<List<Author>>() {}.getType()));
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
