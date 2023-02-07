package aprendendo.api.books.routes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import aprendendo.api.books.enums.Status;
import aprendendo.api.books.model.Book;
import aprendendo.api.books.model.DTO.BookDTO;
import aprendendo.api.books.model.DTO.LoginDTO;
import aprendendo.api.books.model.DTO.TokenDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class BookRoutesTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    private ResponseEntity<TokenDTO> tokenDTO;

    private HttpHeaders headers;

    private Book book;

    @BeforeEach
    public void setup() {
        LoginDTO loginDTO = new LoginDTO("usertestroute@email.com","123");
        tokenDTO = restTemplate.postForEntity("/users/login", loginDTO, TokenDTO.class);
        headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + tokenDTO.getBody().getToken());
    }

    @Test
    public void addBookRouteTest() {

        book = new Book();
        book.setTitle("Test title");
        book.setAuthor("Test author");

        HttpEntity<Book> entity = new HttpEntity<>(book,headers);

        ResponseEntity<BookDTO> response = restTemplate.exchange("/books",HttpMethod.POST,entity,BookDTO.class);

        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals("usertestroute@email.com", response.getBody().getUser().getEmail());

    }

    @Test
    public void findAllBooksRouteTest() {

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<BookDTO[]> response = restTemplate.exchange("/books/find/all",HttpMethod.GET,entity,BookDTO[].class);
        
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("usertestroute@email.com", response.getBody()[0].getUser().getEmail());
    }

    @Test
    public void changeStatusRouteTest() {

        book = new Book();
        book.setTitle("Test title for change status test");
        book.setAuthor("Test author for change status test");

        HttpEntity<Book> bookEntity = new HttpEntity<>(book,headers);
        ResponseEntity<BookDTO> bookResponse = restTemplate.exchange("/books",HttpMethod.POST,bookEntity,BookDTO.class);

        HttpEntity<Void> entity = new HttpEntity<>(headers);
        ResponseEntity<BookDTO> response = restTemplate.exchange("/books/status/change/" + bookResponse.getBody().getId(),HttpMethod.PUT,entity,BookDTO.class);
        
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals(Status.LIDO, response.getBody().getStatus());
    }
}