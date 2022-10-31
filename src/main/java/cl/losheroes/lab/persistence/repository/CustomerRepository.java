package cl.losheroes.lab.persistence.repository;

import cl.losheroes.lab.persistence.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends MongoRepository<Customer, String>,
                                            PagingAndSortingRepository<Customer, String> {

    Optional<Customer> findOneByDocumentId(String documentId);

    Optional<Customer> findOneByEmail(String email);

    boolean existsByDocumentId(String documentId);

    boolean existsByEmail(String email);

    Page<Customer> findAll(Pageable pageable);

}
