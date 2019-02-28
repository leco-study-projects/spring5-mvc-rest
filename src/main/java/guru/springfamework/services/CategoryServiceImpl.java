package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CategoryMapper;
import guru.springfamework.api.v1.model.CategoryDTO;
import guru.springfamework.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryMapper mapper;
    private final CategoryRepository repository;

    @Autowired
    public CategoryServiceImpl(CategoryMapper mapper, CategoryRepository repository) {
        this.mapper = mapper;
        this.repository = repository;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return this.repository.findAll().stream().map(mapper::categoryToCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        return this.mapper.categoryToCategoryDTO(this.repository.findByName(name));
    }
}
