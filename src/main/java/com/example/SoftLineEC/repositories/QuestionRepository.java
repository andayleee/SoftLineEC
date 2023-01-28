package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Question;
import org.springframework.data.repository.CrudRepository;

public interface QuestionRepository extends CrudRepository<Question,Long> {
    Question findByNameOfQuestion(String nameOfQuestion);
}
