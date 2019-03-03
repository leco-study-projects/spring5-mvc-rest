package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import guru.springfamework.services.contracts.CustomerService;
import guru.springfamework.services.implementation.CustomerServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class CustomerServiceImplTest {

    private final CustomerMapper mapper = CustomerMapper.INSTANCE;

    @Mock
    private CustomerRepository repository;

    private final Integer ID = 1;
    private final String NAME = "Gabriel";

    private CustomerService service;

    private Customer customer;


    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        this.service = new CustomerServiceImpl(repository, mapper);

        this.customer = new Customer();

        customer.setId(ID);
        customer.setFirstName(NAME);
    }

    @Test
    public void getCustomerById() {

        when(repository.findById(any())).thenReturn(Optional.of(customer));

        CustomerDTO result = this.service.getCustomerById(ID);
        Assert.assertNotNull(result);
        Assert.assertEquals(NAME, result.getFirstName());
    }

    @Test
    public void getCustomerByName() {

        when(repository.findByFirstName(NAME)).thenReturn(Collections.singletonList(customer));

        List<CustomerDTO> customers = this.service.getCustomerByName(NAME);
        Assert.assertNotNull(customers);
        Assert.assertTrue(!customers.isEmpty());
        Assert.assertEquals(NAME, customers.get(0).getFirstName());
    }

    @Test
    public void getAllCustomers() {

        when(repository.findAll()).thenReturn(Collections.singletonList(customer));

        List<CustomerDTO> customers = this.service.getAllCustomers();
        Assert.assertNotNull(customers);
        Assert.assertTrue(!customers.isEmpty());
    }
}
