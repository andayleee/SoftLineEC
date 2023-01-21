package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.FormOfEducation;
import org.springframework.data.repository.CrudRepository;

public interface FormOfEducationRepository extends CrudRepository<FormOfEducation,Long> {
    FormOfEducation findByTypeOfEducation(String typeOfEducation);
}
