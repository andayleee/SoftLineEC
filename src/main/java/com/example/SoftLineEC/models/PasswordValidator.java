package com.example.SoftLineEC.models;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;
/**
 * Класс-валидатор для проверки корректности пароля.
 * Реализует интерфейс ConstraintValidator<ValidPassword, String>.
 */
public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private static final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";

    private final Pattern pattern;
    /**
     * Конструктор класса.
     * Инициализирует регулярное выражение для проверки корректности пароля.
     */
    public PasswordValidator() {
        this.pattern = Pattern.compile(PASSWORD_PATTERN);
    }
    /**
     * Метод инициализации класса-валидатора.
     * В данном случае не используется.
     * @param constraintAnnotation аннотация, указывающая, какой тип данных будет валидироваться
     */
    @Override
    public void initialize(ValidPassword constraintAnnotation) {
    }
    /**
     * Метод проверки корректности пароля.
     * Проверяет, что в пароле есть хотя бы одна цифра, одна строчная и одна заглавная буква,
     * один специальный символ и длина пароля не менее 8 символов.
     * @param password пароль, который нужно проверить
     * @param context контекст валидации
     * @return true, если пароль корректен, false в противном случае
     */
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        if (password == null) {
            return true;
        }
        return pattern.matcher(password).matches();
    }
}

