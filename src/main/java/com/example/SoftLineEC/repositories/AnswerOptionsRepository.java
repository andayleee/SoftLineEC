package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.AnswerOptions;
import org.springframework.data.repository.CrudRepository;

public interface AnswerOptionsRepository extends CrudRepository<AnswerOptions,Long>{
    AnswerOptions findByContent(String content);
}
