package tn.iit.service.micro.exception.classexception;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import tn.iit.service.micro.utilclass.RequestValidation;
import tn.iit.service.micro.utilclass.ResponseMessage;
import tn.iit.service.micro.utilenum.ResponseBehavior;

@RestControllerAdvice
public class InvalidArgumentException extends RuntimeException{
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseMessage handelInvalidArgumentException (MethodArgumentNotValidException exception){
        ResponseMessage errorMessage = new ResponseMessage("Vérifier votre Données.", ResponseBehavior.ERROR);
        exception.getBindingResult().getFieldErrors()
                .forEach(fieldError -> {
                    errorMessage.getValidations().add(new RequestValidation(fieldError.getField(),  fieldError.getDefaultMessage()));
                });
        return errorMessage;
    }
}