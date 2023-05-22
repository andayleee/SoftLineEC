package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.AnswerOptions;
import com.example.SoftLineEC.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
/**
 * Интерфейс для работы с таблицей вопросов.
 */
public interface QuestionRepository extends CrudRepository<Question,Long> {
    /**
     * Метод для поиска вопроса по названию.
     * @param nameOfQuestion название вопроса
     * @return вопрос с указанным названием
     */
    Question findByNameOfQuestion(String nameOfQuestion);
    /**
     * Метод для поиска вопроса по идентификатору варианта ответа.
     * @param idAnswerOptions идентификатор варианта ответа
     * @return вопрос, связанный с данным вариантом ответа
     */
    Question findByTenants(Optional<AnswerOptions> idAnswerOptions);
}
