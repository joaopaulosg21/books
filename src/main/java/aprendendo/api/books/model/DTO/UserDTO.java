package aprendendo.api.books.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Accessors(chain=true)
public class UserDTO {
    private long id;

    private String name;

    private String email;
}
