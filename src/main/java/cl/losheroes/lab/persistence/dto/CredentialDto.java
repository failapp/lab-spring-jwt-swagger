package cl.losheroes.lab.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredentialDto {

    @NotNull(message = "username is mandatory")
    @NotBlank(message = "username can not be blank")
    private String username;

    @NotNull(message = "password is mandatory")
    @NotBlank(message = "password can not be blank")
    private String password;

}
