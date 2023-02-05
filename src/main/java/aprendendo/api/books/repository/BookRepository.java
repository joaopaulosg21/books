package aprendendo.api.books.repository;

import aprendendo.api.books.model.Book;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
    List<Book> findAllByUserId(long userId);
}
