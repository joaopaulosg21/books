package aprendendo.api.books.controller;

import aprendendo.api.books.model.Book;
import aprendendo.api.books.model.DTO.BookDTO;
import aprendendo.api.books.model.DTO.UserDTO;
import aprendendo.api.books.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<BookDTO> addBook(@Valid @RequestBody Book book, @AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.addBook(book, userDTO));
    }

    @GetMapping("/find/all")
    public ResponseEntity<List<BookDTO>> findAllBooks(@AuthenticationPrincipal UserDTO userDTO) {
        return ResponseEntity.ok(bookService.allBooks(userDTO));
    }
}
