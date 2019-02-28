package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.domain.Category;
import org.junit.Assert;
import org.junit.Test;

public class CategoryMapperTest {

    public static final String DINNER = "Dinner";
    public static final long ID = 1l;
    CategoryMapper mapper = CategoryMapper.INSTANCE;

    @Test
    public void convertCategoryToCategoryDTO() {

        Category category = new Category();
        category.setName(DINNER);
        category.setId(ID);

        CategoryDTO categoryDTO = mapper.categoryToCategoryDTO(category);
        Assert.assertNotNull(categoryDTO);
        Assert.assertEquals(category.getName(), categoryDTO.getName());
    }

    @Test
    public void convertCategoryDTOtoCategory(){

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID);
        categoryDTO.setName(DINNER);

        Category category = mapper.categoryDTOToCategory(categoryDTO);
        Assert.assertNotNull(category);
        Assert.assertEquals(categoryDTO.getName(), category.getName());
    }
}