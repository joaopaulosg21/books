package aprendendo.api.books.model.DTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class TokenDTO {
    private String type;
    private String token;
}
