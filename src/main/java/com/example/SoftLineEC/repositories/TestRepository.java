package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.models.Test;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TestRepository extends CrudRepository<Test,Long>{
    Test findByNameOfTest (String nameOfTest);
    Optional<Test> findTestByLectureID (Lecture lectureID);
    Test findTestByIdTest (Long idTest);
}
