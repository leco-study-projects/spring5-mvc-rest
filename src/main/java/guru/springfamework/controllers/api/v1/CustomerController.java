package guru.springfamework.controllers.api.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping(CustomerController.API_V_1_CUSTOMERS)
public class CustomerController {

    static final String API_V_1_CUSTOMERS = "/api/v1/customers";

    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/{id}")
    public ResponseEntity findByCustomerByName(@PathVariable Integer id) {
        CustomerDTO customer = this.customerService.getCustomerById(id);

        if (customer == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customer);
    }

    @GetMapping
    public ResponseEntity findAll() {
        List<CustomerDTO> customers = this.customerService.getAllCustomers();

        if (customers == null || customers.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(customers);
    }

    @PostMapping
    public ResponseEntity createCustomer(@Valid @RequestBody CustomerDTO customerDTO) {
        return new ResponseEntity(this.customerService.createCustomer(customerDTO),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateCustomer(@PathVariable Integer id,
                                         @Valid @RequestBody CustomerDTO customerDTO) throws URISyntaxException {

        CustomerDTO found = this.customerService.getCustomerById(id);

        if (found == null) {
            CustomerDTO created = this.customerService.createCustomer(customerDTO);
            return ResponseEntity.created(new URI(API_V_1_CUSTOMERS + created.getId())).build();
        }

        customerDTO.setId(id);

        return ResponseEntity.ok(this.customerService.createCustomer(customerDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity pathCustomer(@PathVariable Integer id,
                                       @Valid @RequestBody CustomerDTO customerDTO) {

        CustomerDTO found = this.customerService.getCustomerById(id);

        if (found == null) {
            return ResponseEntity.notFound().build();
        }

        found.setAge(customerDTO.getAge());
        found.setLastName(customerDTO.getLastName());
        found.setFirstName(customerDTO.getLastName());

        return ResponseEntity.ok(this.customerService.createCustomer(found));
    }
}

