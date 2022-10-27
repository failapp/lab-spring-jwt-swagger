package cl.losheroes.lab.service;

import cl.losheroes.lab.persistence.dto.CustomerDto;
import cl.losheroes.lab.persistence.entity.Customer;
import cl.losheroes.lab.persistence.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<CustomerDto> addCustomer(CustomerDto customerDto) {

        Customer customer = modelMapper.map(customerDto, Customer.class);

        if ( Optional.ofNullable( customer.getId() ).isPresent() ) {
            String customerId = customer.getId();
            this.editCustomer(customerDto, customerId);
        } else {

            Optional<Customer> customerEntity = this.validateCustomer(customer, false);
            if (customerEntity.isEmpty()) return Optional.empty();

            log.info("[x] add customer: {}", customerEntity.get());
            customer = customerRepository.save(customerEntity.get());
        }

        return Optional.of(modelMapper.map(customer, CustomerDto.class));

    }

    public Optional<CustomerDto> editCustomer(CustomerDto customerDto, String customerId) {

        if (Optional.ofNullable(customerId).isEmpty()) return Optional.empty();

        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setId(customerId);
        Optional<Customer> customerEntity = this.validateCustomer(customer, true);
        if (customerEntity.isEmpty()) return Optional.empty();

        log.info("[x] update customer: {}, customerId: {}", customerEntity.get());
        customer = customerRepository.save(customerEntity.get());
        return Optional.of(modelMapper.map(customer, CustomerDto.class));
    }

    public boolean deleteCustomer(String documentId) {

        Optional<Customer> customer = customerRepository.findOneByDocumentId(documentId);
        if (customer.isPresent()) {
            log.info("[x] delete customer, documentId: {}", documentId);
            customerRepository.delete(customer.get());
            return true;
        }

        log.warn("[x] customer not found, documentId: {}", documentId);
        return false;
    }

    public List<Customer> getAllCustomers() {

        return customerRepository.findAll();
    }

    public Optional<Customer> getCustomer(String documentId) {
        if ( Optional.ofNullable(documentId).isEmpty() ) return Optional.empty();
        return customerRepository.findOneByDocumentId(documentId);
    }


    private Optional<Customer> validateCustomer(Customer customer, boolean update) {

        if (update) {

            Optional<Customer> _customer = customerRepository.findOneByDocumentId(customer.getDocumentId());
            if (_customer.isPresent() && !_customer.get().getId().equals(customer.getId()) ) {
                log.error("[x] duplicate documentId: {}", customer.getDocumentId());
                return Optional.empty();
            }

            _customer = customerRepository.findOneByEmail( customer.getEmail() );
            if (_customer.isPresent() && !_customer.get().getId().equals(customer.getId()) ) {
                log.error("[x] duplicate email: {}", customer.getEmail());
                return Optional.empty();
            }

        } else {
            boolean duplicateEmail = customerRepository.existsByEmail(customer.getEmail());
            if (duplicateEmail) {
                log.error("[x] duplicate email: {}", customer.getEmail());
                return Optional.empty();
            }

            boolean duplicateDocumentId = customerRepository.existsByDocumentId(customer.getDocumentId());
            if (duplicateDocumentId) {
                log.error("[x] duplicate documentId: {}", customer.getDocumentId());
                return Optional.empty();
            }
        }


        if (Optional.ofNullable(customer.getPhoneNumber()).isEmpty() ) {
            customer.setPhoneNumber("");
        }

        return Optional.of(customer);
    }

}
