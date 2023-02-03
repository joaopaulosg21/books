package aprendendo.api.books.model.DTO;

import aprendendo.api.books.enums.Status;
import aprendendo.api.books.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Accessors(chain = true)
public class BookDTO {
    private Long id;

    private String title;

    private String author;

    private Status status;

    private User user;

    public UserDTO getUser() {
        return new UserDTO(user.getId(),user.getName(),user.getEmail());
    }
}
