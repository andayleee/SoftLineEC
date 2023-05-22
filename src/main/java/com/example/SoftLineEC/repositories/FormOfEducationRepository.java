package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.FormOfEducation;
import org.springframework.data.repository.CrudRepository;
/**
 * Интерфейс для работы с таблицей форм обучения.
 */
public interface FormOfEducationRepository extends CrudRepository<FormOfEducation,Long> {
    /**
     * Метод для поиска формы обучения по типу обучения.
     * @param typeOfEducation тип обучения
     * @return форма обучения с указанным типом обучения
     */
    FormOfEducation findByTypeOfEducation(String typeOfEducation);
}
