package cl.losheroes.lab.persistence.dto;

import cl.losheroes.lab.persistence.entity.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class CustomerDto {

    private String id;

    @NotNull(message = "documentId is mandatory")
    @NotBlank(message = "documentId can not be blank")
    @Size(max = 30, message = "documentId cannot exceed 30 characters")
    private String documentId;

    @NotNull(message = "email is mandatory")
    @Email(message = "must be a valid email")
    private String email;

    @NotNull(message = "firstName is mandatory")
    @NotBlank(message = "firstName can not be blank")
    private String firstName;

    @NotNull(message = "lastName is mandatory")
    @NotBlank(message = "lastName can not be blank")
    private String lastName;

    @NotNull(message = "phoneNumber is mandatory")
    @NotBlank(message = "phoneNumber can not be blank")
    private String phoneNumber;

    @NotNull(message = "birthDate is mandatory")
    @Past(message = "birthDate must be in the past")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @Valid
    @NotNull(message = "addresses is mandatory")
    @Size(min=1, message = "address list cannot be empty")
    private List<AddressDto> addresses;

    public CustomerDto(String documentId, String email, String firstName, String lastName, String phoneNumber, LocalDate birthDate) {
        this.documentId = documentId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
    }

    public CustomerDto(String documentId, String email, String firstName, String lastName, String phoneNumber, LocalDate birthDate, List<AddressDto> addresses) {
        this.documentId = documentId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.addresses = addresses;
    }
}
