package aprendendo.api.books;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import aprendendo.api.books.model.Book;
import aprendendo.api.books.model.User;
import aprendendo.api.books.model.DTO.BookDTO;
import aprendendo.api.books.repository.BookRepository;
import aprendendo.api.books.repository.UserRepository;
import aprendendo.api.books.service.BookService;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookUnitTests {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    private BookService bookService;

    private Book book;

    private User user;

    @BeforeEach
    public void setup() {

        bookService = new BookService(bookRepository,userRepository);

        user = new User(1L,"Test Name","testEmail@email.com","123");

        book = new Book(1L,"Test Title","Test author",null,user);

        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        
        List<Book> books = new ArrayList<>();
        books.add(book);
        when(bookRepository.findAllByUserId(user.getId())).thenReturn(books);
    }

    @Test
    public void addBookTest() {

        BookDTO bookDTO = bookService.addBook(book, user.toDTO());

        Assertions.assertEquals(book.getId(), bookDTO.getId());
        Assertions.assertEquals(user.getEmail(), bookDTO.getUser().getEmail());
    }

    @Test
    public void allBooksTest() {
        
        List<BookDTO> booksDTO = bookService.allBooks(user.toDTO());

        Assertions.assertEquals(book.getId(), booksDTO.get(0).getId());
        Assertions.assertEquals(user.getId(), booksDTO.get(0).getUser().getId());
    }
}
