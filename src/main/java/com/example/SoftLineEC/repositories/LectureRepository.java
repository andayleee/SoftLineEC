package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface LectureRepository extends CrudRepository<Lecture,Long> {
    Lecture findByNameOfLecture(String nameOfLecture);
    List<Lecture> findLecturesByBlockID(Block blockID);
    Lecture findLectureByIdLecture(long IdLecture);
}
