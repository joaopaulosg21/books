package aprendendo.api.books.model;

import aprendendo.api.books.model.DTO.BookDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="books")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message="Title não pode ser null")
    private String title;

    @NotBlank(message="Author não pode ser null")
    private String author;

    private String status;

    @ManyToOne
    @JoinColumn(referencedColumnName = "id",columnDefinition = "user_id")
    private User user;

    public BookDTO toDTO() {
        return new BookDTO(id,title,author,status,user);
    }
}
