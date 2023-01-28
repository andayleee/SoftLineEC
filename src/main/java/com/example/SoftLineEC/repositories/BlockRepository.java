package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Block;
import org.springframework.data.repository.CrudRepository;

public interface BlockRepository extends CrudRepository<Block,Long>{
    Block findByNameOfBlockOrDescriptionOrDuration (String nameOfBlock, String description, String duration);
}
