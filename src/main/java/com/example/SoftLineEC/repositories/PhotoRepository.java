package com.example.SoftLineEC.repositories;


import com.example.SoftLineEC.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
public interface PhotoRepository extends CrudRepository<Photo,Long> {
    Photo findByPhotoPath(String photoPath);

    Photo findByLectureID(long lectureID);

}
