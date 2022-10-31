package cl.losheroes.lab.service;

import cl.losheroes.lab.exception.BusinessException;
import cl.losheroes.lab.exception.RequestException;
import cl.losheroes.lab.persistence.dto.CustomerDto;
import cl.losheroes.lab.persistence.entity.Customer;
import cl.losheroes.lab.persistence.repository.CustomerRepository;
import cl.losheroes.lab.shared.dto.ItemsDto;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDto addCustomer(CustomerDto customerDto) {
        Customer customer = this.validateCustomer(customerDto, false);
        customer = customerRepository.save(customer);
        log.info("[x] add customer: {}", customer);
        return modelMapper.map(customer, CustomerDto.class);
    }

    public CustomerDto editCustomer(CustomerDto customerDto) {
        Customer customer = this.validateCustomer(customerDto, true);
        customer = customerRepository.save(customer);
        log.info("[x] update customer: {}", customer);
        return modelMapper.map(customer, CustomerDto.class);
    }

    public void deleteCustomer(String documentId) {
        Optional<Customer> customer = customerRepository.findOneByDocumentId(documentId);
        if (customer.isPresent()) {
            log.info("[x] delete customer, documentId: {}", documentId);
            customerRepository.delete(customer.get());
        } else {
            throw new BusinessException(HttpStatus.NOT_FOUND, "customer not found, documentId:" + documentId);
        }
    }

    public ItemsDto<CustomerDto> getAllCustomers(Integer page, Integer itemsPerPage) {

        page = (Objects.isNull(page) || page < 0) ? 0 : page-1;
        itemsPerPage = (Objects.isNull(itemsPerPage) || itemsPerPage < 0) ? 5 : itemsPerPage;
        //log.info("[x] page: {}, itemsPerPage: {}", page, itemsPerPage);

        Pageable pageable = PageRequest.of(page, itemsPerPage, Sort.by("firstName"));
        Page<Customer> customersPage = customerRepository.findAll(pageable);

        List<CustomerDto> dtoList = customersPage.getContent().stream()
                .map( customer -> {
                    return modelMapper.map(customer, CustomerDto.class);
                }).toList();

        ItemsDto<CustomerDto> itemsDto = new ItemsDto<>();
        itemsDto.setItems(dtoList);
        itemsDto.setPage(page+1);
        itemsDto.setItemsPerPage(itemsPerPage);
        itemsDto.setTotalItems(customersPage.getTotalElements());
        itemsDto.setTotalPages(customersPage.getTotalPages());

        return itemsDto;
    }


    public CustomerDto getCustomer(String documentId) {

        Optional<Customer> customer = customerRepository.findOneByDocumentId(documentId.trim().toUpperCase());
        if (customer.isEmpty())
            throw new BusinessException(HttpStatus.NOT_FOUND, "customer not found");

        return modelMapper.map(customer.get(), CustomerDto.class);

    }


    private Customer validateCustomer(CustomerDto customerDto, boolean update) {

        Customer customer = modelMapper.map(customerDto, Customer.class);
        String documentId = customer.getDocumentId().trim().toUpperCase();
        String email = customer.getEmail().trim().toLowerCase();

        if (update) {

            if (Objects.isNull(customerDto.getId()))
                throw new RequestException("id is mandatory");
            if (customerDto.getId().isEmpty())
                throw new RequestException("id can not be blank");

            String customerId = customer.getId();
            Optional<Customer> _customer = customerRepository.findOneByDocumentId(documentId);

            if (_customer.isPresent() && !_customer.get().getId().equals(customerId)) {
                String msg = "documentId: " + customerId + " duplicate";
                log.error("[x] {}", msg);
                throw new BusinessException(HttpStatus.CONFLICT, msg);
            }

            _customer = customerRepository.findOneByEmail(email);
            if (_customer.isPresent() && !_customer.get().getId().equals(customerId)) {
                String msg = "email: " + email + " duplicate";
                log.error("[x] {}", msg);
                throw new BusinessException(HttpStatus.CONFLICT, msg);
            }

        } else {

            if (!Objects.isNull(customerDto.getId()))
                throw new RequestException("use update method (PUT) for resources with Id");

            boolean duplicateDocumentId = customerRepository.existsByDocumentId(documentId);
            if (duplicateDocumentId) {
                String msg = "documentId "+ documentId + " already exists";
                log.error("[x] {}", msg);
                throw new BusinessException(HttpStatus.CONFLICT, msg);
            }

            boolean duplicateEmail = customerRepository.existsByEmail(email);
            if (duplicateEmail) {
                String msg = "email "+ email + " already exists";
                log.error("[x] {}", msg);
                throw new BusinessException(HttpStatus.CONFLICT, msg);
            }
        }

        //verify and format documentId (rut), email and state (region)
        customer.setDocumentId(documentId);
        customer.setEmail(email);
        customer.getAddresses().forEach( address -> {
            if ( Objects.isNull(address.getState())) {
                address.setState("");
            }
        });
        return customer;

    }

    @Async
    public void saveBulkData(List<CustomerDto> customerDtoList) {

        customerDtoList.forEach( customerDto -> {

            try {
                this.addCustomer(customerDto);
            } catch (Exception ex) {
                log.error("[x] save bulk data error: {}", ex.getMessage());
            }
        });
    }


}
