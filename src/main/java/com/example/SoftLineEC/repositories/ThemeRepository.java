package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Theme;
import org.springframework.data.repository.CrudRepository;
/**
 * Интерфейс для работы с таблицей тем.
 */
public interface ThemeRepository extends CrudRepository<Theme,Long> {
    /**
     * Метод для поиска темы по названию.
     * @param nameOfTheme название темы
     * @return тема с указанным названием
     */
    Theme findThemeByNameOfTheme (String nameOfTheme);
}
