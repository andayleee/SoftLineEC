package com.example.SoftLineEC.repositories;


import com.example.SoftLineEC.models.Block;
import com.example.SoftLineEC.models.Lecture;
import com.example.SoftLineEC.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
/**
 * Интерфейс для работы с таблицей фотографий.
 */
public interface PhotoRepository extends CrudRepository<Photo,Long> {
    /**
     * Метод для поиска фотографии по пути к файлу.
     * @param photoPath путь к файлу с фотографией
     * @return фотография с указанным путем к файлу
     */
    Photo findByPhotoPath(String photoPath);
    /**
     * Метод для поиска фотографии по идентификатору лекции.
     * @param lectureID идентификатор лекции
     * @return фотография, связанная с данной лекцией
     */
    Photo findByLectureID(long lectureID);
    /**
     * Метод для поиска всех фотографий по идентификатору лекции.
     * @param lectureID идентификатор лекции
     * @return список фотографий, связанных с данной лекцией
     */
    List<Photo> findPhotosByLectureID(Lecture lectureID);

}
