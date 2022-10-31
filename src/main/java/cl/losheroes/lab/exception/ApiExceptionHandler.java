package cl.losheroes.lab.exception;

import cl.losheroes.lab.shared.Util;
import cl.losheroes.lab.shared.dto.MessageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public ResponseEntity<MessageDto> runtimeExceptionHandler(RuntimeException ex) {
        log.info("[x] runtime exception handler: {}", ex.getMessage());
        MessageDto errorDto = MessageDto.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorDto, errorDto.getStatus());
    }

    @ExceptionHandler(value = RequestException.class)
    public ResponseEntity<MessageDto> requestExceptionHandler(RequestException ex) {
        MessageDto errorDto = MessageDto.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).build();
        return new ResponseEntity<>(errorDto, errorDto.getStatus());
    }

    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<MessageDto> businessExceptionHandler(BusinessException ex) {
        MessageDto errorDto = MessageDto.builder()
                                .message(ex.getMessage())
                                .status(ex.getStatus()).build();
        return new ResponseEntity<>(errorDto, errorDto.getStatus());
    }


    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<MessageDto> validationException(MethodArgumentNotValidException ex){

        List<String> messages = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((err) -> {
            messages.add(err.getDefaultMessage());
        });

        MessageDto errorDto = MessageDto.builder()
                .message(Util.trimBrackets(messages.toString()))
                .status(HttpStatus.BAD_REQUEST)
                .build();
        return new ResponseEntity<>(errorDto, errorDto.getStatus());
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<MessageDto> generalException(Exception ex) {
        MessageDto errorDto = MessageDto.builder().message(ex.getMessage()).status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        return new ResponseEntity<>(errorDto, errorDto.getStatus());
    }



}
