package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Lecture;
import org.springframework.data.repository.CrudRepository;

public interface LectureRepository extends CrudRepository<Lecture,Long>{
    Lecture findByNameOfLecture(String nameOfLecture);
}
