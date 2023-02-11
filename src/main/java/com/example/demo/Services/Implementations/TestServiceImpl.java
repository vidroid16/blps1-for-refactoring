package com.example.demo.Services.Implementations;

import com.example.demo.DataBase.ProjectsDB.Project;
import com.example.demo.DataBase.ProjectsDB.ProjectRepository;
import com.example.demo.DataBase.UsersDB.User;
import com.example.demo.DataBase.UsersDB.UsersRepository;
import com.example.demo.Mail.MailSender;
import com.example.demo.Services.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    UsersRepository usersRepository;
    @Autowired
    MailSender mailSender;
    @Override
    public String say() {
        return "I am test service. Everything is OK";
    }
    @Override
    public void use() {
        Project u =  projectRepository.findByName("Dota3");
        int sum = u.getCur_sum();
        System.out.println(sum);
        projectRepository.save(new Project("TestShit", 1337, 228));
    }

    @Override
    public void mail() {
        for (User u:usersRepository.getall()) {
            mailSender.send(mailSender.getGmailSession(),"Kickstarter: Лучие проекты за неделю для вас!!", "У нас 1 проект вы о чем вообще! Но зато есть анекдот", u.getLogin());
        }
    }
}
