package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
/**
 * Интерфейс для работы с таблицей блоков курсов.
 */
public interface BlockRepository extends CrudRepository<Block,Long> {
    /**
     * Метод для поиска блока курса по названию, описанию или длительности.
     * @param nameOfBlock название блока
     * @param description описание блока
     * @param duration длительность блока
     * @return блок курса, содержащий указанные данные
     */
    Block findByNameOfBlockOrDescriptionOrDuration (String nameOfBlock, String description, String duration);
    /**
     * Метод для поиска блоков курса по идентификатору курса.
     * @param courseID идентификатор курса
     * @return список блоков курса
     */
    List<Block> findBlocksByCourseID(Course courseID);
    /**
     * Метод для поиска блока курса по идентификатору блока.
     * @param IdBlock идентификатор блока
     * @return блок курса с указанным идентификатором
     */
    Block findBlockByIdBlock(long IdBlock);
}
