package cl.losheroes.lab.resource;

import cl.losheroes.lab.persistence.dto.CustomerDto;
import cl.losheroes.lab.persistence.entity.Customer;
import cl.losheroes.lab.service.CustomerService;
import cl.losheroes.lab.shared.dto.ItemsDto;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@Validated
@RequestMapping("api/v1")
public class CustomerRest {

    @Autowired
    private CustomerService customerService;



    @GetMapping("customers")
    public ResponseEntity<ItemsDto<CustomerDto>> getAllCustomers(
                                @RequestParam(required = false) Integer page,
                                @RequestParam(required = false) Integer itemsPerPage) {

        ItemsDto<CustomerDto> itemsDto = customerService.getAllCustomers(page, itemsPerPage);
        return new ResponseEntity<>(itemsDto, HttpStatus.OK);
    }

    @GetMapping("customers/{documentId}")
    public ResponseEntity<?> getCustomer(@PathVariable String documentId) {
        CustomerDto customerDto = customerService.getCustomer(documentId);
        return new ResponseEntity<>(customerDto, HttpStatus.OK);
    }

    @ApiOperation("crear Cliente: formato para fecha: yyyy-MM-dd")
    @PostMapping("customers")
    public ResponseEntity<?> createCustomer(@RequestBody @Valid CustomerDto customerDto) {
        //log.info("[x] customerDto: {}", customerDto);
        CustomerDto dto = customerService.addCustomer(customerDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }


    @ApiOperation("actualizar Cliente: formato para fecha: yyyy-MM-dd")
    @PutMapping("customers")
    public ResponseEntity<?> updateCustomer(@RequestBody @Valid CustomerDto customerDto) {
        log.info("[x] customerDto: {}", customerDto);
        CustomerDto dto = customerService.editCustomer(customerDto);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }



    @DeleteMapping("customers/{documentId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable String documentId) {
        customerService.deleteCustomer(documentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation("saveBulkCustomerData: cargar array json de clientes: formato para fecha: yyyy-MM-dd")
    @PostMapping("customers/bulk/data")
    public ResponseEntity<?> saveBulkCustomerData(@RequestBody List<CustomerDto> customerDtoList) {
        customerService.saveBulkData(customerDtoList);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
