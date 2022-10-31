package cl.losheroes.lab.shared.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@Builder
public class MessageDto {

    private HttpStatus status;
    private String message;

}
