package com.example.demo.DataBase.DonationsDB;

import com.example.demo.DataBase.ProjectsDB.Project;
import com.example.demo.DataBase.UsersDB.User;
import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Модель данных донатов.
 * @autor Шаля Андрей
 * @version 2.0
 */
@Entity
@Table(name = "donations")
public class Donation {

    /** Поле ID */
    @Id
    @GeneratedValue
    private Long id;

    /** Поле внешнея ссылка на пользователя*/
    @ManyToOne
    @JoinColumn(name="userId")
    private User user;

    /** Поле внешнея ссылка на проект*/
    @ManyToOne
    @JoinColumn(name = "projectId")
    private Project project;

    /** Сумма доната в рубялх*/
    private int sum;
    /** Время доната*/
    private Timestamp donation_time;

    /**
     * Конструктор - создание нового доната с определенными значениями
     * @param user - пользователь
     * @param project - проект
     * @param sum - сумма доната в рублях
     * @see Donation#Donation()
     */
    public Donation(User user, Project project, int sum) {
        this.user = user;
        this.project = project;
        this.sum = sum;
        this.donation_time = new Timestamp(System.currentTimeMillis());
    }

    /**
     * Конструктор по умаолчанию - создание пустого доната с предустановленной датой
     */
    public Donation(){
       this.donation_time = new Timestamp(System.currentTimeMillis());
    }

}