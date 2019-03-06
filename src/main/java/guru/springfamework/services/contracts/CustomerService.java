package guru.springfamework.services.contracts;

import guru.springfamework.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    CustomerDTO getCustomerById(Integer id);

    List<CustomerDTO> getCustomerByName(String name);

    List<CustomerDTO> getAllCustomers();

    CustomerDTO createCustomer(CustomerDTO customer);
}
