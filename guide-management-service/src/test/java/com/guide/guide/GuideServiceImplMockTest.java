package com.guide.guide;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.guide.exception.ResourceNotFoundExceptionRequest;
import com.guide.guides.dto.GuideRequest;
import com.guide.guides.dto.GuideResponse;
import com.guide.guides.entity.Category;
import com.guide.guides.entity.Guide;
import com.guide.guides.repository.CategoryRepository;
import com.guide.guides.repository.GuideRepository;
import com.guide.guides.service.impl.GuideServiceImpl;

@DataJpaTest
public class GuideServiceImplMockTest {

    @Spy
    private GuideRepository guideRepository;

    @Spy
    private CategoryRepository categoryRepository;

    @InjectMocks
    private GuideServiceImpl guideServiceImpl;

    Guide guide;

    Guide guide2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        guide = new Guide();
        guide.setCategory(new Category());
        guide.setCoachId(1L);
        guide.setDescount(1.1);
        guide.setDescription("description");
        guide.setGameId(1L);
        guide.setId(1L);
        guide.setPoints(1L);
        guide.setTitle("title");

        guide2 = new Guide();
        guide2.setCategory(new Category());
        guide2.setCoachId(2L);
        guide2.setDescount(2.2);
        guide2.setDescription("description2");
        guide2.setGameId(2L);
        guide2.setId(2L);
        guide2.setPoints(2L);
        guide2.setTitle("title2");
    }

    @Test
    @DisplayName("When Find All Then Return List Guide")
    public void WhenFindAllThenReturnListGuide() {
        // Arrange
        ArrayList<Guide> guides = new ArrayList<>();
        guides.add(guide);
        guides.add(guide2);

        when(guideRepository.findAll()).thenReturn(guides);
        // Act
        List<GuideResponse> response = guideServiceImpl.getAll();

        // Assert
        assertEquals(2L, response.size());
    }

    @Test
    @DisplayName("When Find By Id Then Return Guide")
    public void WhenFindByIdThenReturnGuide() {
        // Arrange
        when(guideRepository.getGuideById(1L)).thenReturn(Optional.of(guide));
        // Act
        GuideResponse response = guideServiceImpl.getById(1L);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("title", response.getTitle());
        assertEquals(1L, response.getCoachId());
    }

    @Test
    @DisplayName("When Create A Guide Then Return Guide")
    public void WhenCreateAGuideThenReturnGuide() {
        // Arrange
        GuideRequest request = new GuideRequest();
        request.setCategoryId(45L);
        request.setCoachId(45L);
        request.setDescount(45.0);
        request.setDescription("description mas grande");
        request.setGameId(45L);
        request.setPoints(450L);
        request.setTitle("title mas innovador");

        when(guideRepository.getGuideById(1L)).thenReturn(Optional.of(guide));
        when(categoryRepository.getCategoryById(45L)).thenReturn(Optional.of(new Category()));
        when(guideRepository.save(Mockito.any())).thenReturn(guide);
        // Act
        GuideResponse response = guideServiceImpl.create(request);

        // Assert
        assertEquals("title mas innovador", response.getTitle());
        assertEquals(45L, response.getCoachId());
        assertEquals("description mas grande", response.getDescription());
    }

    @Test
    @DisplayName("When Create A Guide But Error Ocurred While Creating")
    public void WhenCreateAGuideButErrorOcurredWhileCreating() {
        // Arrange
        GuideRequest request = new GuideRequest();
        request.setCategoryId(45L);
        request.setCoachId(45L);
        request.setDescount(45.0);
        request.setDescription("description mas grande");
        request.setGameId(45L);
        request.setPoints(450L);
        request.setTitle("title mas innovador");

        when(guideRepository.getGuideById(1L)).thenReturn(Optional.of(guide));
        when(categoryRepository.getCategoryById(45L)).thenReturn(Optional.of(new Category()));
        when(guideRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while creating guide";
        Throwable exception = catchThrowable(() -> {
            guideServiceImpl.create(request);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Update A Guide Then Return Guide")
    public void WhenUpdateAGuideThenReturnGuide() {
        // Arrange
        GuideRequest request = new GuideRequest();
        request.setCategoryId(45L);
        request.setCoachId(45L);
        request.setDescount(45.0);
        request.setDescription("description mas pequeño");
        request.setGameId(45L);
        request.setPoints(450L);
        request.setTitle("title mas triste");

        when(guideRepository.getGuideById(1L)).thenReturn(Optional.of(guide));
        when(categoryRepository.getCategoryById(45L)).thenReturn(Optional.of(new Category()));
        when(guideRepository.save(Mockito.any())).thenReturn(guide);
        // Act
        GuideResponse response = guideServiceImpl.update(request, 1L);

        // Assert
        assertEquals("title mas triste", response.getTitle());
        assertEquals(45L, response.getCoachId());
        assertEquals("description mas pequeño", response.getDescription());
    }

    @Test
    @DisplayName("When Update A Guide But Error Ocurred While Updating")
    public void WhenUpdateAGuideButErrorOcurredWhileUpdating() {
        // Arrange
        GuideRequest request = new GuideRequest();
        request.setCategoryId(45L);
        request.setCoachId(45L);
        request.setDescount(45.0);
        request.setDescription("description mas grande");
        request.setGameId(45L);
        request.setPoints(450L);
        request.setTitle("title mas innovador");

        when(guideRepository.getGuideById(1L)).thenReturn(Optional.of(guide));
        when(categoryRepository.getCategoryById(45L)).thenReturn(Optional.of(new Category()));
        when(guideRepository.save(Mockito.any())).thenReturn(ResourceNotFoundExceptionRequest.class);
        // Act
        String message = "Error ocurred while updating guide";
        Throwable exception = catchThrowable(() -> {
            guideServiceImpl.update(request, 1L);
        });

        // Assert
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }

    @Test
    @DisplayName("When Delete A Guide Is Successful")
    public void WhenDeleteAGuideIsSuccessful() {
        // Assert
        doNothing().when(guideRepository).deleteById(1L);
        // Act
        Throwable exception = catchThrowable(() -> {
            guideServiceImpl.delete(1L);
        });

        // Arrange
        assertThat(exception).isEqualTo(null);
    }

    @Test
    @DisplayName("When Delete A Guide But Error Ocurred While Deleting")
    public void WhenDeleteAGuideButErrorOcurredWhileDeleting() {
        // Assert
        doThrow(ResourceNotFoundExceptionRequest.class).when(guideRepository).deleteById(1L);
        // Act
        String message = "Error ocurred while deleting guide";
        Throwable exception = catchThrowable(() -> {
            guideServiceImpl.delete(1L);
        });

        // Arrange
        assertThat(exception).isInstanceOf(ResourceNotFoundExceptionRequest.class).hasMessage(message);
    }
}
