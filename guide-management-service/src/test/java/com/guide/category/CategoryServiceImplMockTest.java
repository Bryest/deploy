package com.guide.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.guide.exception.ResourceNotFoundExceptionRequest;
import com.guide.guides.dto.CategoryRequest;
import com.guide.guides.dto.CategoryResponse;
import com.guide.guides.entity.Category;
import com.guide.guides.repository.CategoryRepository;
import com.guide.guides.service.impl.CategoryServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;

@DataJpaTest
public class CategoryServiceImplMockTest {

    @Spy
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryServiceImpl;

    Category category;

    Category category2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        category = new Category();
        category.setGameId(1L);
        category.setId(1L);
        category.setName("name");

        category2 = new Category();
        category2.setGameId(2L);
        category2.setId(2L);
        category2.setName("name2");
    }

    @Test
    @DisplayName("When Find All Then Return List Category")
    public void WhenFindAllThenReturnListCategory() {
        // Arrange
        ArrayList<Category> categories = new ArrayList<>();
        categories.add(category);
        categories.add(category2);

        when(categoryRepository.findAll()).thenReturn(categories);
        // Act
        List<CategoryResponse> response = categoryServiceImpl.getAll();

        // Assert
        assertEquals(2L, response.size());
    }

    @Test
    @DisplayName("When Find By Id Then Return Category")
    public void WhenFindByIdThenReturnCategory() {
        // Arrange
        when(categoryRepository.getCategoryById(1L)).thenReturn(Optional.of(category));

        // Act
        CategoryResponse response = categoryServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals(1L, response.getGameId());
        assertEquals("name", response.getName());
    }

    @Test
    @DisplayName("When Find By Id But Not Exist")
    public void WhenFindByIdButNotExist() {
        // Arrange
        when(categoryRepository.getCategoryById(3L)).thenReturn(Optional.empty());

        // Act
        String message = "Category not found by id";
        Throwable exception = catchThrowable(() -> {
            categoryServiceImpl.getById(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Create A Category Return Category")
    public void WhenCreateACategoryReturnCategory() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        request.setGameId(2L);
        request.setName("name2");

        when(categoryRepository.save(Mockito.any())).thenReturn(category2);
        // Act
        CategoryResponse response = categoryServiceImpl.create(request);

        // Assert
        assertEquals(2L, response.getGameId());
        assertEquals("name2", response.getName());
    }

    @Test
    @DisplayName("When Create A Category But Error Ocurred While Creating")
    public void WhenCreateACategoryButErrorOcurredWhileCreating() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        request.setGameId(2L);
        request.setName("name2");

        when(categoryRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(categoryRepository.getCategoryById(3L)).thenReturn(Optional.empty());

        // Act
        String message = "Error ocurred while creating category";
        Throwable exception = catchThrowable(() -> {
            categoryServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update A Category Return Category")
    public void WhenUpdateACategoryReturnCategory() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        request.setGameId(2L);
        request.setName("name2to2");

        when(categoryRepository.save(Mockito.any())).thenReturn(category2);
        when(categoryRepository.getCategoryById(2L)).thenReturn(Optional.of(category2));
        // Act
        CategoryResponse response = categoryServiceImpl.update(request, 2L);

        // Assert
        assertEquals(2L, response.getGameId());
        assertEquals("name2to2", response.getName());
    }

    @Test
    @DisplayName("When Update A Category But Error Ocurred While Updating")
    public void WhenUpdateACategoryButErrorOcurredWhileUpdating() {
        // Arrange
        CategoryRequest request = new CategoryRequest();
        request.setGameId(2L);
        request.setName("name2");

        when(categoryRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        when(categoryRepository.getCategoryById(2L)).thenReturn(Optional.of(category2));
        // Act
        String message = "Error ocurred while updating category";
        Throwable exception = catchThrowable(() -> {
            categoryServiceImpl.update(request, 2L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete A Category Is Successful")
    public void WhenDeleteACategoryIsSuccessful() {
        // Arrange
        doNothing().when(categoryRepository).deleteById(1L);

        // Act
        Throwable exception = catchThrowable(() -> {
            categoryServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isEqualTo(null);
    }

    @Test
    @DisplayName("When Delete A Category But Error Ocurred While Deleting")
    public void WhenDeleteACategoryButErrorOcurredWhileDeleting() {
        // Arrange
        doThrow(ResourceNotFoundExceptionRequest.class).when(categoryRepository).deleteById(1L);

        // Act
        String message = "Error ocurred while deleting category";
        Throwable exception = catchThrowable(() -> {
            categoryServiceImpl.delete(1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }
}
