package cl.losheroes.lab;

import cl.losheroes.lab.persistence.dto.CustomerDto;
import cl.losheroes.lab.persistence.entity.Customer;
import cl.losheroes.lab.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    ModelMapper modelMapper;

    @Test
    public void getAllCustomerTest() {
        List<Customer> customerList = customerService.getAllCustomers();
        log.info("[x] customerList size: {}", customerList.size());
        assertThat(customerList.size()).isGreaterThanOrEqualTo(0);
    }

    @Test
    public void addCustomerTest() {

        CustomerDto customerDto = new CustomerDto("11222333-4", "alanbrito@mail.com", "Alan", "Brito");
        Optional<CustomerDto> customer = customerService.addCustomer(customerDto);
        assertThat(customer.isPresent()).isTrue();
    }

    @Test
    public void editCustomerTest() {

        String documentId = "11222333-4";
        Optional<Customer> customer = customerService.getCustomer(documentId);

        if (customer.isPresent()) {

            CustomerDto customerDto = modelMapper.map(customer.get(), CustomerDto.class);

            customerDto.setFirstName("John");
            Optional<CustomerDto> dto = customerService.editCustomer(customerDto, customerDto.getId());
            log.info("[x] customerDto: {}", dto);
            assertThat(dto.isPresent()).isTrue();
        }
        assertThat(false).isTrue();

    }

    @Test
    public void deleteCustomerTest() {

        String documentId = "11222333-4411";
        boolean delete = customerService.deleteCustomer(documentId);
        assertThat(delete).isFalse();
    }

    @Test
    public void passwordEncoderTest() {

        String pass = new BCryptPasswordEncoder().encode("admin");
        log.info("[x] pass: {}", pass);

        assertThat(pass.length()).isGreaterThan(0);

    }


}
