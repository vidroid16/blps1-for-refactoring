package com.example.demo.DataBase.UsersDB;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;
import java.util.Optional;

/**
 * Класс репозиторий для манипуляции c {@link User} .
 * @autor Шаля Андрей
 * @version 2.0
 */
public interface UsersRepository extends CrudRepository<User, Long>{
    /**
     * Функция получения пользователя по логину
     * @param login логин пользователя
     * @return возвращает {@link User} или null
     */
    Optional<User> findByLogin(String login);

    /**
     * Функция получения пользователя по логину и паролю
     * @param login логин пользователя
     * @param password хэш пароля пользователя
     * @return возвращает {@link User} или null
     */
    Optional<User> findByLoginAndPassword(String login, String password);

    /**
     * Функция получения всех пользователей
     * @return возвращает массив {@link User}
     */
    @Query(
            value = "SELECT * FROM blps_users",
            nativeQuery = true)
    ArrayList<User> getall();
}