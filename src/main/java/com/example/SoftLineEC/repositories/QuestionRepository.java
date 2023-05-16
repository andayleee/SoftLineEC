package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.AnswerOptions;
import com.example.SoftLineEC.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface QuestionRepository extends CrudRepository<Question,Long> {
    Question findByNameOfQuestion(String nameOfQuestion);
    Question findByTenants(Optional<AnswerOptions> idAnswerOptions);
}
