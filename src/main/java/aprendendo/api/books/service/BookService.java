package aprendendo.api.books.service;

import aprendendo.api.books.enums.Status;
import aprendendo.api.books.model.Book;
import aprendendo.api.books.model.DTO.BookDTO;
import aprendendo.api.books.model.DTO.UserDTO;
import aprendendo.api.books.model.User;
import aprendendo.api.books.repository.BookRepository;
import aprendendo.api.books.repository.UserRepository;

import java.util.List;
import java.util.Optional;

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
        book.setStatus(Status.NAO_LIDO);
        return bookRepository.save(book).toDTO();
    }

    public List<BookDTO> allBooks(UserDTO userDTO) {
        List<Book> books = bookRepository.findAllByUserId(userDTO.getId());
        return books.stream().map((book) -> book.toDTO()).toList();
    }

    public BookDTO changeStatus(long id, UserDTO userDTO) {
        Optional<Book> optionalBook = bookRepository.findByIdAndUserId(id, userDTO.getId());
        if(optionalBook.isPresent()) {
            Book book = optionalBook.get();
            book.setStatus(Status.LIDO);
            
            return bookRepository.save(book).toDTO();
        } 
        
        throw new RuntimeException("Book com esse id não existe");
    }
} 
