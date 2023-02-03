package aprendendo.api.books.service;

import aprendendo.api.books.model.Book;
import aprendendo.api.books.model.DTO.BookDTO;
import aprendendo.api.books.model.DTO.UserDTO;
import aprendendo.api.books.model.User;
import aprendendo.api.books.repository.BookRepository;
import aprendendo.api.books.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    public BookService(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public BookDTO addBook(Book book, UserDTO userDTO) {
        User user = userRepository.findById(userDTO.getId()).get();
        book.setUser(user);
        book.setStatus("Não lido");
        return bookRepository.save(book).toDTO();
    }
}
