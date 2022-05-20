package edu.ftv.training.ExceptionHandler;

import edu.ftv.training.Model.ResponseMessage;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler   {
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseMessage handleAllException(Exception ex, WebRequest request){
        return  new ResponseMessage(10000, false, ex.getLocalizedMessage());
    }

    @ExceptionHandler(IndexOutOfBoundsException.class)
    @ResponseStatus(value =HttpStatus.BAD_REQUEST)
    public ResponseMessage UserException(Exception ex, WebRequest request) {
        return  new ResponseMessage(10100,false, "Invalid request");
    }
}
