package com.socialmedia.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthManagerException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(AuthManagerException ex){
        HttpStatus httpStatus= ex.getErrorType().getHttpStatus();
        return new ResponseEntity<>(createError(ex),httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) //5501   // @Valid ile sağlanmayan (bu kurguda @NotBlank) hataları yakalanıyor
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleValidationException(MethodArgumentNotValidException ex){
        ErrorType errorType = ErrorType.PARAMETER_NOT_VALID;
        List<String> fields = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(e-> fields.add(e.getField()+": " + e.getDefaultMessage()));
        ErrorMessage errorMessage=createError(errorType,ex);
        errorMessage.setFields(fields);
        return  new ResponseEntity<>(errorMessage,errorType.getHttpStatus());
//        String customizedMessage = ex.getBindingResult().getFieldErrors().stream().map(error -> error.getDefaultMessage()).collect(Collectors.joining(","));
//        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // 400
//        return new ResponseEntity<>(createError(ex, 5501, customizedMessage), httpStatus);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(IllegalArgumentException ex) {  // console ekranında ACTIVE ASTIVE yapıp yolladığımızda direkt böyle yakalattık, olmadan önce üstteki yakalıyordu MethodArgumentNotValidException
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

        ErrorType errorType = ErrorType.INVALID_STATUS;
        ErrorMessage errorMessage = createError(errorType,ex);
        errorMessage.setMessage(errorType.getMessage());
        return new ResponseEntity<>(errorMessage, httpStatus);
    }

    @ExceptionHandler(ConstraintViolationException.class) //5601    // Entity sınıfındaki hazır kütüphane annotationları ve custom annotationların hataları yakalanıyor.
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleValidationException(ConstraintViolationException ex){
        String customizedMessage = ex.getConstraintViolations().stream().map(error -> error.getMessage()).collect(Collectors.joining(","));
        HttpStatus httpStatus = HttpStatus.BAD_REQUEST; // 400 // 422 Unprocessable Entity dee kullanılabilirdi.
        return new ResponseEntity<>(createError(ex, 5601, customizedMessage), httpStatus);
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(ArithmeticException ex){
        HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(createError(ex, httpStatus.value()),httpStatus);
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleManagerException(Exception ex){
        HttpStatus httpStatus= HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(createError(ex, httpStatus.value()),httpStatus);
    }

    private ErrorMessage createError(ErrorType errorType, Exception e){
        return ErrorMessage.builder()
                .code(errorType.getCode())
                .message(errorType.getMessage())
                .build();
    }

    private ErrorMessage createError(AuthManagerException ex){
        return ErrorMessage.builder()
                .code(ex.getErrorType().getCode())
                .message(ex.getMessage())
                .build();
    }

    private ErrorMessage createError(Exception ex, int value){
        return ErrorMessage.builder()
                .code(value)
                .message(ex.getMessage())
                .build();
    }

    private ErrorMessage createError(Exception ex, int value, String customizedMessage){
        return ErrorMessage.builder()
                .code(value)
                .message(customizedMessage)
                .build();
    }

}
