package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import guru.springfamework.repositories.CategoryRepository;
import guru.springfamework.services.contracts.CategoryService;
import guru.springfamework.services.implementation.CategoryServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.when;

public class CategoryServiceImplTest {

    private static final String DINNER = "Dinner";
    @Mock
    private CategoryRepository repository;

    private CategoryMapper mapper = CategoryMapper.INSTANCE;

    private CategoryService service;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.service = new CategoryServiceImpl(mapper, repository);
    }

    @Test
    public void getAllCategories() {
        Category dinners = new Category();
        dinners.setId(1l);
        dinners.setName(DINNER);

        when(repository.findAll()).thenReturn(Collections.singletonList(dinners));
        List<CategoryDTO> allCategories = service.getAllCategories();
        Assert.assertEquals(1, allCategories.size());
    }

    @Test
    public void getCategoryByName() {
        Category dinners = new Category();
        dinners.setId(1l);
        dinners.setName("Dinner");

        when(repository.findByName(DINNER)).thenReturn(dinners);

        CategoryDTO categoryByName = service.getCategoryByName(DINNER);
        Assert.assertNotNull(categoryByName);
        Assert.assertEquals(dinners.getName(), categoryByName.getName());
    }
}