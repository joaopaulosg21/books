package aprendendo.api.books.model.DTO;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class LoginDTO {
    @NotBlank(message = "Email não pode ser null")
    private String email;
    
    @NotBlank(message = "Password não pode ser null")
    private String password;
}
