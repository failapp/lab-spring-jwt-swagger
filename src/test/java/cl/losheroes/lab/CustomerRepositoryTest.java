package cl.losheroes.lab;

import cl.losheroes.lab.persistence.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    //@Test
    public void existsByDocumentIdTest() {

        String documentId = "20333444-5";
        boolean bln = customerRepository.existsByDocumentId(documentId);
        assertThat(bln).isTrue();
    }

    //@Test
    public void existsByEmailTest() {

        String email = "alanbrito@mail.com";
        boolean bln = customerRepository.existsByEmail(email);
        assertThat(bln).isTrue();
    }

}
