package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.AnswerOptions;
import com.example.SoftLineEC.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
/**
 * Интерфейс для работы с таблицей вариантов ответов.
 */
public interface AnswerOptionsRepository extends CrudRepository<AnswerOptions,Long>{
    /**
     * Метод для поиска варианта ответа по содержанию.
     * @param content содержание варианта ответа
     * @return вариант ответа, содержащий указанное содержание
     */
    AnswerOptions findByContent(String content);
    /**
     * Метод для поиска вариантов ответов по идентификатору вопроса.
     * @param question вопрос
     * @return список вариантов ответов на данный вопрос
     */
    List<AnswerOptions> findAnswerOptionsByQuestionID (Question question);
}
