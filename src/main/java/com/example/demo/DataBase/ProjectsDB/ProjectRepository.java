package com.example.demo.DataBase.ProjectsDB;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;

/**
 * Класс репозиторий для манипуляции c {@link Project} .
 * @autor Шаля Андрей
 * @version 2.0
 */
public interface ProjectRepository extends CrudRepository<Project,Long> {

    /**
     * Функция получения всех проектов содержащих строку
     * @param name строка для поиска
     * @return возвращает массив {@link Project}
     */
    ArrayList<Project> findAllByNameContaining(String name);
    /**
     * Функция получения проекта по имени
     * @return возвращает класс {@link Project}
     */
    Project findByName(String name);

    /**
     * Функция добавления денег к проекту.
     * @param sum сумма доната в рублях
     * @param id id проекта
     */
    @Modifying
    @Query("update Project p set cur_sum = cur_sum + :sum WHERE id = :Id")
    void donate(@Param("sum") int sum, @Param("Id") Long id);
    /**
     * Функция обновления карты для сбора донатов.
     * @param cardNum номер карты
     * @param id id проекта
     */
    @Modifying
    @Query("update Project p set card = :cardNum WHERE id = :Id")
    void changeCard(@Param("cardNum") String cardNum, @Param("Id") Long id);
}
