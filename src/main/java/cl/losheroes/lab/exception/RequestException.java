package cl.losheroes.lab.exception;

import lombok.Data;

@Data
public class RequestException extends RuntimeException {
    private String code;

    public RequestException(String message) {
        super(message);
    }

    public RequestException(String code, String message) {
        super(message);
        this.code = code;
    }
}
