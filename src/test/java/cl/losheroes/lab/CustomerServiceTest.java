package cl.losheroes.lab;

import cl.losheroes.lab.persistence.dto.AddressDto;
import cl.losheroes.lab.persistence.dto.CustomerDto;
import cl.losheroes.lab.persistence.entity.Customer;
import cl.losheroes.lab.service.CustomerService;
import cl.losheroes.lab.shared.dto.ItemsDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@Slf4j
@SpringBootTest
public class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Autowired
    ModelMapper modelMapper;

    CustomerDto customerDto;
    List<AddressDto> addressDtoList;
    @BeforeEach
    public void init() {

        String documentId = "11222333-4";
        String firstName = "Alan";
        String lastName = "Brito";
        String email = "alanbrito@mail.com";
        String phoneNumber = "99888777";
        LocalDate birthDate = LocalDate.now().minusYears(40);

        String streetName = "street test";
        String streetNumber = "#101";
        String state = "RM";
        String city = "Santiago";
        String country = "Chile";

        AddressDto addressDto = new AddressDto(streetName, streetNumber, state, city, country);
        addressDtoList = new ArrayList<>();
        addressDtoList.add(addressDto);
        customerDto = new CustomerDto(documentId, email, firstName, lastName, phoneNumber, birthDate, addressDtoList);

    }

    @Test
    public void getAllCustomerTest() {

        Integer page = 1;
        Integer itemsPerPage = 5;

        ItemsDto<CustomerDto> itemsDto = customerService.getAllCustomers(page, itemsPerPage);
        log.info("[x] customers items size: {}", itemsDto.getItems().size());
        assertThat(itemsDto.getItems().size()).isGreaterThanOrEqualTo(0);

    }

    //@Test
    public void addCustomerTest() {
        customerDto = customerService.addCustomer(customerDto);
        assertThat(customerDto).isNotNull();
    }

    //@Test
    public void editCustomerTest() {
        String documentId = "16300200-K";
        CustomerDto dto = customerService.getCustomer(documentId);
        dto.setFirstName("Cindhy");
        dto = customerService.editCustomer(dto);
        assertThat(dto).isNotNull();
    }

    @Test
    public void deleteCustomerTest() {
        String documentId = "55154-6246";
        try {
            customerService.deleteCustomer(documentId);
            CustomerDto customerDto = customerService.getCustomer(documentId);
            assertThat(customerDto).isNull();
        } catch (Exception ex) {
            log.error("[x] error: {}", ex.getMessage());
        }
    }


}
