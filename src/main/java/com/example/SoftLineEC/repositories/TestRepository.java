package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Test;
import org.springframework.data.repository.CrudRepository;

public interface TestRepository extends CrudRepository<Test,Long>{
    Test findByNameOfTest (String nameOfTest);
}
