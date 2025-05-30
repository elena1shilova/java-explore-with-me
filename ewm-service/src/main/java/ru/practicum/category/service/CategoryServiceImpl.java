package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.IllegalArgumentException;
import ru.practicum.exception.NotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {

        log.info("Получение запроса на добавление новой категории");

        if (categoryRepository.existsByName(newCategoryDto.getName())) {
            throw new IllegalArgumentException("Категория с таким именем уже существует");
        } else {
            Category toSaveCategory = categoryMapper.toCategory(newCategoryDto);
            Category savedCategory = categoryRepository.save(toSaveCategory);

            log.info("Категория добавлена");
            return categoryMapper.toCategoryDto(savedCategory);
        }

    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return categoryRepository
                .findAll(PageRequest.of(from, size))
                .getContent().stream()
                .map(categoryMapper::toCategoryDto).toList();
    }

    @Override
    public CategoryDto getCategory(Long catId) {
        return categoryMapper.toCategoryDto(
                categoryRepository.findById(catId)
                        .orElseThrow(() -> new NotFoundException("Category not found"))
        );
    }

    @Override
    public void deleteCategory(Long catId) {
        if (categoryRepository.existsById(catId)) {
            categoryRepository.deleteById(catId);
        } else {
            throw new NotFoundException("Category not found");
        }
    }

    @Override
    public CategoryDto updateCategory(Long catId, CategoryDto categoryDto) {

        Category existCategory = categoryRepository.findById(catId).orElseThrow(
                () -> new NotFoundException("Category not found"));

        if (existCategory.getName().equals(categoryDto.getName()) && categoryRepository.existsByName(categoryDto.getName())) {
            throw new IllegalArgumentException("Категория с таким именем уже существует");
        }

        categoryDto.setId(catId);

        Category savedCategory = categoryRepository.save(categoryMapper.toCategory(categoryDto));
        return categoryMapper.toCategoryDto(savedCategory);
    }

}
