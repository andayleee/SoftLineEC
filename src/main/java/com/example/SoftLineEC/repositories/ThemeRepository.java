package com.example.SoftLineEC.repositories;

import com.example.SoftLineEC.models.Theme;
import org.springframework.data.repository.CrudRepository;

public interface ThemeRepository extends CrudRepository<Theme,Long> {
    Theme findThemeByNameOfTheme (String nameOfTheme);
}
