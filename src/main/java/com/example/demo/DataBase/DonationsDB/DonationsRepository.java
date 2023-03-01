package com.example.demo.DataBase.DonationsDB;

import com.example.demo.DataBase.ProjectsDB.Project;
import com.example.demo.DataBase.UsersDB.User;
import com.example.demo.dtos.DonationID;
import org.springframework.data.repository.CrudRepository;

/**
 * Класс репозиторий для манипуляции c {@link Donation} .
 * @autor Шаля Андрей
 * @version 2.0
 */
public interface DonationsRepository extends CrudRepository<Donation, DonationID> {
    /**
     * Функция получения доната по пользователю
     * @return возвращает класс {@link Donation}
     */
    Donation findByUser(User user);
    /**
     * Функция получения доната по проекту
     * @return возвращает класс {@link Donation}
     */
    Donation findByProject(Project project);
}
