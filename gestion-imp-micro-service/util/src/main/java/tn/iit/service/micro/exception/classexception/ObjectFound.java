package tn.iit.service.micro.exception.classexception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ObjectFound extends RuntimeException{
    public ObjectFound(String message) {
        super(message);
    }
}
