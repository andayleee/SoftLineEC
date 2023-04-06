package com.example.SoftLineEC.repositories;


import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PhotoRepository extends CrudRepository<Photo,Long> {
    Photo findByPhotoPath(String photoPath);

    Photo findByLectureID(long lectureID);
    List<Photo> findPhotosByLectureID(Lecture lectureID);

}
