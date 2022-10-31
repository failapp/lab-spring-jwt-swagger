package cl.losheroes.lab.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    @NotNull(message = "streetName is mandatory")
    @NotBlank(message = "streetName can not be blank")
    private String streetName;

    @NotNull(message = "streetNumber is mandatory")
    @NotBlank(message = "streetNumber can not be blank")
    private String streetNumber;

    private String state;

    @NotNull(message = "city is mandatory")
    @NotBlank(message = "city can not be blank")
    private String city;

    @NotNull(message = "country is mandatory")
    @NotBlank(message = "country can not be blank")
    private String country;

}
