package com.example.SoftLineEC.repositories;
import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Lecture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
/**
 * Интерфейс для работы с таблицей лекций.
 */
public interface LectureRepository extends CrudRepository<Lecture,Long> {
    /**
     * Метод для поиска лекции по названию.
     * @param nameOfLecture название лекции
     * @return лекция с указанным названием
     */
    Lecture findByNameOfLecture(String nameOfLecture);
    /**
     * Метод для поиска лекций по идентификатору блока курса.
     * @param blockID идентификатор блока курса
     * @return список лекций, связанных с данным блоком курса
     */
    List<Lecture> findLecturesByBlockID(Block blockID);
    /**
     * Метод для поиска лекции по идентификатору.
     * @param IdLecture идентификатор лекции
     * @return лекция с указанным идентификатором
     */
    Lecture findLectureByIdLecture(long IdLecture);
}
