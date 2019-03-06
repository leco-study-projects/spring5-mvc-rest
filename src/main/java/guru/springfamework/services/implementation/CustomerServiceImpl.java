package guru.springfamework.services.implementation;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.services.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository repository, CustomerMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public CustomerDTO getCustomerById(Integer id) {
        Optional<Customer> customer = this.repository.findById(id);
        return customer.map(mapper::domainToResponse).orElse(null);
    }

    @Override
    public List<CustomerDTO> getCustomerByName(String name) {
        return this.repository.findCustomersByFirstName(name).stream().map(mapper::domainToResponse).collect(Collectors.toList());
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return this.repository.findAll().stream().map(mapper::domainToResponse).collect(Collectors.toList());
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customer) {
        Customer saved = this.repository.save(this.mapper.responseToDomain(customer));
        return this.mapper.domainToResponse(saved);
    }
}
