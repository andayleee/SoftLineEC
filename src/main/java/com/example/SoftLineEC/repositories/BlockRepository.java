package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface BlockRepository extends CrudRepository<Block,Long> {
    Block findByNameOfBlockOrDescriptionOrDuration (String nameOfBlock, String description, String duration);
    //    Optional<Block> findBlocksByCourseID(Long courseID);
    List<Block> findBlocksByCourseID(Course courseID);
    Block findBlockByIdBlock(long IdBlock);
}
