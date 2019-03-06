package guru.springfamework.controllers;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.controllers.api.v1.CustomerController;
import guru.springfamework.services.implementation.CustomerServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Collections;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerServiceImpl service;

    private MockMvc mockMvc;

    private final String NAME = "Customer";

    private CustomerDTO customer;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(service)).build();

        this.customer = new CustomerDTO();

        this.customer.setId(1);
        this.customer.setFirstName("Customer");
        this.customer.setLastName("Name");
    }

    @Test
    public void findCustomerById() throws Exception {
        when(this.service.getCustomerById(1)).thenReturn(this.customer);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    public void findCustomerByIdWhenResultIsNull() throws Exception {
        when(this.service.getCustomerById(1)).thenReturn(null);

        mockMvc.perform(get("/api/v1/customers/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAll() throws Exception {
        when(this.service.getAllCustomers()).thenReturn(Collections.singletonList(this.customer));

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void findAllWhenResultIsNull() throws Exception {
        when(this.service.getAllCustomers()).thenReturn(null);

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void findAllWhenResultIsEmpty() throws Exception {
        when(this.service.getAllCustomers()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/v1/customers"))
                .andExpect(status().isNotFound());
    }
}
