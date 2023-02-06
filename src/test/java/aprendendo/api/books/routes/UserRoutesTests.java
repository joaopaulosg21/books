package aprendendo.api.books.routes;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import aprendendo.api.books.model.User;
import aprendendo.api.books.model.DTO.LoginDTO;
import aprendendo.api.books.model.DTO.TokenDTO;
import aprendendo.api.books.model.DTO.UserDTO;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class UserRoutesTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addUserRouteTest() {
        User user = new User();
        user.setName("User test route");
        user.setEmail("usertestroute@email.com");
        user.setPassword("123");
        
        ResponseEntity<UserDTO> response = restTemplate.postForEntity("/users", user, UserDTO.class);
        
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
        Assertions.assertEquals(user.getEmail(), response.getBody().getEmail());
    }

    @Test
    public void loginUserRouteTest() {
        LoginDTO loginDTO = new LoginDTO("usertestroute@email.com","123");

        ResponseEntity<TokenDTO> response = restTemplate.postForEntity("/users/login",loginDTO,TokenDTO.class);

        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assertions.assertEquals("Bearer",response.getBody().getType());
    }
}
