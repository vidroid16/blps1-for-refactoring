package com.example.demo.Services.Implementations;

import com.example.demo.DataBase.ProjectsDB.Project;
import com.example.demo.DataBase.ProjectsDB.ProjectRepository;
import com.example.demo.DataBase.UsersDB.User;
import com.example.demo.DataBase.UsersDB.UsersRepository;
import com.example.demo.Mail.MailSender;
import com.example.demo.Services.DataService;
import com.example.demo.security.SecurityUser;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Класс сервис для представления пользователя {@link User} в контексте Spring Security {@link SecurityUser}
 * @author Шаля Андрей
 * @version 2.0
 */
@Service
public class DataServiceImpl implements DataService {
    private final ProjectRepository projectRepository;
    private final UsersRepository usersRepository;
    private final PlatformTransactionManager transactionManager;
    @Autowired
    DonationServiceImpl donationService;
    @Autowired
    MailSender mailSender;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    @Value("${bankServer.port}")
    private String bankServerPort;

    public DataServiceImpl(ProjectRepository projectRepository, UsersRepository usersRepository, PlatformTransactionManager transactionManager) {
        this.projectRepository = projectRepository;
        this.usersRepository = usersRepository;
        this.transactionManager = transactionManager;
    }
    @PersistenceContext
    private EntityManager entityManager;

    /**
     * Функция сохранения нового пользователя
     */
    @Override
    public void addUser(User user) {
        usersRepository.save(user);
    }

    /**
     * Функция возвращает всех пользователей
     */
    @Override
    public ArrayList<User> listUsers() {
        return (ArrayList<User>) usersRepository.findAll();
    }

    /**
     * Функция сохранения нового проекта
     */
    @Override
    public void addProject(Project project) {
        projectRepository.save(project);
    }

    /**
     * Функция смены карты на которую поступают донаты на проект
     * @param nCardNum номер карты
     * @param projectId id проекта
     */
    @Override
    public void changeProjectCard(Long projectId, String nCardNum) {
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("Donation Transaction"); // Transaction name
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            projectRepository.changeCard(nCardNum, projectId);
        }
        catch (Exception e) {
            transactionManager.rollback(status);
        }
        transactionManager.commit(status);
    }

    /**
     * Функция проверки карты привязанной к проекту на возможность зачичлений
     * @param id - id проекта
     */
    @Override
    public void moderate(Long id) {
        HttpPost post = new HttpPost("http://localhost:" + this.bankServerPort + "/bank/moderate");
        JSONObject requestBody = new JSONObject();
        Project p = projectRepository.findById(id).get();
        requestBody.put("number", p.getCard());
        requestBody.put("money", 1);
        post.setHeader("content-type", "application/json");
        try {
            post.setEntity(new StringEntity(requestBody.toString()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        CloseableHttpClient httpClient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(post);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(response.getStatusLine().getStatusCode() == 200){
            mailSender.send(mailSender.getGmailSession(),"Kickstarter", "Ваша карта работает нормально, можете не переживать!", p.getMail());
        }else{
            mailSender.send(mailSender.getGmailSession(),"Kickstarter", "Ваша карта работает ненормально, можете переживать!", p.getMail());
        }
    }

    /**
     * Функция получения всех проектов содержащих строку
     * @param name строка для поиска
     * @return возвращает массив {@link Project}
     */
    @Override
    public ArrayList<Project> doSearch(String name) {
        ArrayList<Project> pr = projectRepository.findAllByNameContaining(name);
        return pr;
    }
}
