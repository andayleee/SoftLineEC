package com.example.SoftLineEC.controllers;

import com.example.SoftLineEC.models.CourseType;
import com.example.SoftLineEC.repositories.CourseTypeRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BindingResult;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;
/**
 * Юнит-тесты для класса CourseTypeController
 */
public class CourseTypeControllerTest {
    @InjectMocks
    private CourseTypeController courseTypeController;

    @Mock
    private CourseTypeRepository courseTypeRepository;

    @Mock
    private BindingResult bindingResult;
    /**
     * Подготовка перед выполнением каждого теста.
     */
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    /**
     * Тестовый сценарий для метода CourseTypeAddAdd
     * при передаче допустимого объекта CourseType. Он должен выполнять перенаправление на "/CourseType"
     * и сохранять объект CourseType.
     */
    @Test
    public void testCourseTypeAddAdd_WithValidCourseType_RedirectToCourseType() {
        CourseType courseType = new CourseType();

        when(bindingResult.hasErrors()).thenReturn(false);

        String result = courseTypeController.CourseTypeAddAdd(courseType, bindingResult);
        assertEquals("redirect:/CourseType", result);
        verify(courseTypeRepository, times(1)).save(courseType);
    }
    /**
     * Тестовый сценарий для метода CourseTypeAddAdd
     * при передаче недопустимого объекта CourseType. Он должен возвращать "CourseTypeAdd".
     */
    @Test
    public void testCourseTypeAddAdd_WithInvalidCourseType_ReturnCourseTypeAddView() {

        CourseType courseType = new CourseType();

        when(bindingResult.hasErrors()).thenReturn(true);

        String result = courseTypeController.CourseTypeAddAdd(courseType, bindingResult);
        assertEquals("CourseTypeAdd", result);
        verify(courseTypeRepository, never()).save(courseType);
    }
}
