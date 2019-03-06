package guru.springfamework.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    @Mock
    private CustomerServiceImpl service;

    private MockMvc mockMvc;

    private final String NAME = "Customer";

    private CustomerDTO customer;

    private String json;

    private String jsonMalformed;

    @Before
    public void setUp() throws JsonProcessingException {

        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(new CustomerController(service)).build();

        this.customer = new CustomerDTO();
        CustomerDTO insufficientFields = new CustomerDTO();

        this.customer.setId(1);
        this.customer.setFirstName("Customer");
        this.customer.setLastName("Name");
        this.customer.setAge(20);

        insufficientFields.setId(1);
        insufficientFields.setFirstName("Customer");

        ObjectMapper mapper = new ObjectMapper();
        this.json = mapper.writeValueAsString(customer);
        this.jsonMalformed = mapper.writeValueAsString(insufficientFields);
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

    @Test
    public void createCustomer() throws Exception {
        when(this.service.createCustomer(this.customer)).thenReturn(this.customer);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void createCustomerWithFieldRequiredNull() throws Exception {
        when(this.service.createCustomer(this.customer)).thenReturn(this.customer);

        mockMvc.perform(post("/api/v1/customers")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(jsonMalformed))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void updateCustomer() throws Exception {
        when(this.service.getCustomerById(this.customer.getId())).thenReturn(this.customer);
        when(this.service.createCustomer(this.customer)).thenReturn(this.customer);

        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void updateCustomerWhenCustomerNotFound() throws Exception {
        when(this.service.getCustomerById(this.customer.getId())).thenReturn(null);
        when(this.service.createCustomer(this.customer)).thenReturn(this.customer);

        mockMvc.perform(put("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isCreated());
    }

    @Test
    public void patchCustomer() throws Exception {
        when(this.service.getCustomerById(this.customer.getId())).thenReturn(this.customer);
        when(this.service.createCustomer(this.customer)).thenReturn(this.customer);

        mockMvc.perform(patch("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isOk());
    }

    @Test
    public void patchCustomerWhenCustomerNotFound() throws Exception {
        when(this.service.getCustomerById(this.customer.getId())).thenReturn(null);
        when(this.service.createCustomer(this.customer)).thenReturn(this.customer);

        mockMvc.perform(patch("/api/v1/customers/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(json))
                .andExpect(status().isNotFound());
    }
}
