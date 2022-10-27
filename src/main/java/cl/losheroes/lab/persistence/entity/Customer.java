package cl.losheroes.lab.persistence.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "lab")
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

    public Customer(String documentId, String email, String firstName, String lastName) {
        this.documentId = documentId;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = "";
    }

}
