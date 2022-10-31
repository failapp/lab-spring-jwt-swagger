package cl.losheroes.lab.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Data
@NoArgsConstructor
@Document(collection = "customers")
public class Customer {

    @Id
    private String id;

    @Indexed(unique = true)
    private String documentId;

    @Indexed(unique = true)
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private LocalDate birthDate;

    private List<Address> addresses;

    public Customer(String documentId, String email, String firstName, String lastName, LocalDate birthDate) {
        this.documentId = documentId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = "";
        this.birthDate = birthDate;
        this.addresses = new ArrayList<>();
    }

    public Customer(String documentId, String email, String firstName, String lastName, LocalDate birthDate, List<Address> addresses) {
        this.documentId = documentId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = "";
        this.birthDate = birthDate;
        this.addresses = addresses;
    }

    public Customer(String documentId, String email, String firstName, String lastName, String phoneNumber, LocalDate birthDate, List<Address> addresses) {
        this.documentId = documentId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.addresses = addresses;
    }

    public void addAddress(Address address) {
        if (Objects.isNull(this.addresses)) this.addresses = new ArrayList<>();
        Optional<Address> _address = this.addresses.stream().filter(x -> x.equals(address)).findFirst();
        if (_address.isEmpty())
            this.addresses.add(address);
    }

    public void removeAddress(Address address) {
        if (Objects.isNull(this.addresses)) return;
        this.addresses.remove(address);
    }

    public void removeAllAddress() {
        if (Objects.isNull(this.addresses)) return;
        this.addresses.clear();
    }

}
