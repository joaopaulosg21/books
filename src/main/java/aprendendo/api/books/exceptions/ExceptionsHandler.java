package aprendendo.api.books.exceptions;

import org.springframework.http.HttpHeaders;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler {

    private Map<String,String> error = new HashMap<>();

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException exc, WebRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        error.put("ERROR",exc.getMessage());
        return handleExceptionInternal(exc,error,headers, BAD_REQUEST,req);
    }

    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc, HttpHeaders headers, HttpStatus status,WebRequest request) {
        exc.getBindingResult().getAllErrors().forEach((errors) -> {
            String fieldname = ((FieldError) errors).getField();
            String message = errors.getDefaultMessage();
            error.put(fieldname,message);
        });
        return handleExceptionInternal(exc,error,headers,status,request);
    }
}
