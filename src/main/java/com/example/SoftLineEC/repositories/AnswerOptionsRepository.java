package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.AnswerOptions;
import com.example.SoftLineEC.models.Question;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AnswerOptionsRepository extends CrudRepository<AnswerOptions,Long>{
    AnswerOptions findByContent(String content);
    List<AnswerOptions> findAnswerOptionsByQuestionID (Question question);
}
