package com.example.demo.DataBase.ProjectsDB;

import com.example.demo.DataBase.UsersDB.User;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * Модель данных проектов.
 * @autor Шаля Андрей
 * @version 2.0
 */
@Entity
public class Project {
        /** Поле ID */
        @Id
        @GeneratedValue
        private Long id;
        /** Имя проекта */
        private String name;
        /** Требумая сумма для сбора */
        private int need_sum;
        /** Текущая сумма всех донатов*/
        private int cur_sum;
        /** Проверка привязанной карты на доступность. Проверена - 1, Не проверена - 0 */
        private int checked;
        /** Номер карты на которую поступают донаты */
        private String card;
        /** Почта автора проекта */
        private String mail;

        public Project(){

        }

        public Project(String name, int need_sum, int cur_sum) {
                this.name = name;
                this.need_sum = need_sum;
                this.cur_sum = cur_sum;
        }
        public Project(String name, int need_sum, int cur_sum, String card, String mail) {
                this.name = name;
                this.need_sum = need_sum;
                this.cur_sum = cur_sum;
                this.card = card;
                this.mail = mail;
        }
        public Project(String name, int need_sum, int cur_sum, int checked) {
                this.name = name;
                this.need_sum = need_sum;
                this.cur_sum = cur_sum;
                this.checked = checked;
        }
        public Long getId() {
                return id;
        }

        public int getChecked() {
                return checked;
        }

        public void setChecked(int checked) {
                this.checked = checked;
        }

        public String getCard() {
                return card;
        }

        public void setCard(String card) {
                this.card = card;
        }
        public void setId(Long id) {
                this.id = id;
        }

        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        public String getMail() {
                return mail;
        }

        public void setMail(String mail) {
                this.mail = mail;
        }

        public int getNeed_sum() {
                return need_sum;
        }

        public void setNeed_sum(int need_sum) {
                this.need_sum = need_sum;
        }

        public int getCur_sum() {
                return cur_sum;
        }

        public void setCur_sum(int cur_sum) {
                this.cur_sum = cur_sum;
        }


}
