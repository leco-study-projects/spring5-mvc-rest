package guru.springfamework.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.controllers.api.v1.CategoryController;
import guru.springfamework.services.contracts.CategoryService;
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

import java.util.Arrays;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CategoryControllerTest {

    private static final String DINNER = "Dinner";
    private static final Long ID = 1l;

    private MockMvc mockMvc;

    @Mock
    private CategoryService categoryService;

    private final CategoryDTO response = new CategoryDTO();


    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders.standaloneSetup(new CategoryController(categoryService)).build();

        response.setName(DINNER);
        response.setId(ID);
    }

    @Test
    public void getCategoryByName() throws Exception {

        when(categoryService.getCategoryByName(DINNER)).thenReturn(response);

        mockMvc.perform((get("/api/v1/categories/" + DINNER)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.name").value(DINNER))
                .andExpect(jsonPath("$.id").value(ID));
    }

    @Test
    public void getCategoryNotFound() throws Exception {
        when(categoryService.getCategoryByName(DINNER)).thenReturn(null);

        mockMvc.perform((get("/api/v1/categories")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllCategories() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(Arrays.asList(response, response));

        mockMvc.perform((get("/api/v1/categories")))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getAllCategoriesWhenServiceResponseIsNull() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(null);

        mockMvc.perform((get("/api/v1/categories")))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllCategoriesWhenServiceResponseIsEmpty() throws Exception {
        when(categoryService.getAllCategories()).thenReturn(null);

        mockMvc.perform((get("/api/v1/categories")))
                .andExpect(status().isNotFound());
    }
}