package guru.springfamework.controllers.api.v1;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.contracts.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

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
}
