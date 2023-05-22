package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.models.Question;
import com.example.SoftLineEC.models.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
/**
 * Интерфейс для работы с таблицей тестов.
 */
public interface TestRepository extends CrudRepository<Test,Long>{
    /**
     * Метод для поиска теста по названию.
     * @param nameOfTest название теста
     * @return тест с указанным названием
     */
    Test findByNameOfTest (String nameOfTest);
    /**
     * Метод для поиска теста по идентификатору лекции.
     * @param lectureID идентификатор лекции
     * @return тест, связанный с данной лекцией
     */
    Optional<Test> findTestByLectureID (Lecture lectureID);
    /**
     * Метод для поиска теста по идентификатору.
     * @param idTest идентификатор теста
     * @return тест с указанным идентификатором
     */
    Test findTestByIdTest (Long idTest);
    /**
     * Метод для поиска теста по идентификатору вопроса.
     * @param idQuestion идентификатор вопроса
     * @return тест, содержащий данный вопрос
     */
    Test findByTenants (Question idQuestion);
}
