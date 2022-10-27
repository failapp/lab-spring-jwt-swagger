package cl.losheroes.lab.persistence.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
public class CustomerDto {

    private String id;

    @NotNull
    @NotBlank
    private String documentId;
    @NotNull
    @Email
    private String email;
    @NotBlank
    @Size(max = 50)
    private String firstName;
    @NotBlank
    @Size(max = 50)
    private String lastName;
    private String phoneNumber;

    public CustomerDto(String documentId, String email, String firstName, String lastName) {
        this.documentId = documentId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = "";
    }
}
