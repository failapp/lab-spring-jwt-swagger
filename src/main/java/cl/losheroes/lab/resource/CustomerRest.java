package cl.losheroes.lab.resource;

import cl.losheroes.lab.persistence.dto.CustomerDto;
import cl.losheroes.lab.persistence.entity.Customer;
import cl.losheroes.lab.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@Validated
@RequestMapping("api/v1")
public class CustomerRest {

    @Autowired
    private CustomerService customerService;

    @GetMapping("customers")
    public ResponseEntity<List<Customer>> getAllCustomer() {
        List<Customer> customerList = customerService.getAllCustomers();
        return new ResponseEntity<>(customerList, HttpStatus.OK);
    }

    @GetMapping("customers/{documentId}")
    public ResponseEntity<?> getCustomer(@PathVariable String documentId) {

        Optional<Customer> customer = customerService.getCustomer(documentId);
        if (customer.isPresent())
            return new ResponseEntity<>(customer.get(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("customers")
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerDto customerDto) {

        Optional<CustomerDto> dto = customerService.addCustomer(customerDto);
        if (dto.isPresent())
            return new ResponseEntity<>(dto.get(), HttpStatus.CREATED);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @PutMapping("customers/{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable String customerId, @RequestBody @Valid CustomerDto customerDto) {

        Optional<CustomerDto> dto = customerService.editCustomer(customerDto, customerId);
        if (dto.isPresent())
            return new ResponseEntity<>(dto.get(), HttpStatus.OK);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



    @DeleteMapping("customers/{documentId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String documentId) {

        boolean delete = customerService.deleteCustomer(documentId);
        if (delete)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}
