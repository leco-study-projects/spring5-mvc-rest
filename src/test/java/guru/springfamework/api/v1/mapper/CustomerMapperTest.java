package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import org.junit.Assert;
import org.junit.Test;

public class CustomerMapperTest {

    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Test
    public void mapCustomerDTO(){
        Customer customer = new Customer();
        customer.setFirstName("Gabriel");
        customer.setLastName("Costa");
        customer.setAge(28);

        CustomerDTO customerDTO = mapper.domainToResponse(customer);

        Assert.assertNotNull(customerDTO);
        Assert.assertEquals(customer.getFirstName(), customerDTO.getFirstName());
        Assert.assertEquals(customer.getLastName(), customerDTO.getLastName());
        Assert.assertEquals(customer.getAge(), customerDTO.getAge());
    }

    @Test
    public void mapCustomer(){
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName("Gabriel");
        customerDTO.setLastName("Costa");
        customerDTO.setAge(28);

        Customer customer = mapper.responseToDomain(customerDTO);

        Assert.assertNotNull(customer);
        Assert.assertEquals(customerDTO.getFirstName(), customer.getFirstName());
        Assert.assertEquals(customerDTO.getLastName(), customer.getLastName());
        Assert.assertEquals(customerDTO.getAge(), customer.getAge());
    }
}
