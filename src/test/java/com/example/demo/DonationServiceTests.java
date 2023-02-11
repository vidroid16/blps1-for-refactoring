package com.example.demo;

import com.example.demo.DataBase.DonationsDB.Donation;
import com.example.demo.DataBase.DonationsDB.DonationsRepository;
import com.example.demo.DataBase.ProjectsDB.Project;
import com.example.demo.DataBase.ProjectsDB.ProjectRepository;
import com.example.demo.DataBase.UsersDB.User;
import com.example.demo.DataBase.UsersDB.UsersRepository;
import com.example.demo.Services.Implementations.DonationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
class DonationServiceTests {


    @Autowired
    private DonationServiceImpl donationService;

    @MockBean
    private UsersRepository userRepository;
    @MockBean
    private ProjectRepository projectRepository;
    @MockBean
    private DonationsRepository donationsRepository;


    static Stream<Arguments> goodCombinations() {
        return Stream.of(
                Arguments.of("vidroid16@gmail.com", 3L, 200, "1812-4127-3531-5000")

        );
    }
    static Stream<Arguments> badCombinations() {
        return Stream.of(
                Arguments.of("papa", 32L, 320, "1812-4127-4356-dw dwd wd w"),
                Arguments.of("vidroid16@gmail.com", 4L, 200, "1812-4127-3531-5000"),
                Arguments.of("vidroid435646@gmail.com", 3L, 200, "1812-4127-3531-5000"),
                Arguments.of("vidroid16@gmail.com", 3L, 200, "4352-4127-4231-5000"),
                Arguments.of("vidroid16@gmail.com", 3L, 200, "4352-4127-4231-5000")

        );
    }

    @BeforeEach
    public void init() {
        User user = new User();
        user.setLogin("vidroid16@gmail.com");
        user.setPassword("password");

        Project project = new Project("TestProject",200,20000);

        Donation donation = new Donation(user,project,200);


        Mockito.doReturn(Optional.of(user)).when(userRepository).findByLogin(ArgumentMatchers.eq("vidroid16@gmail.com"));
        Mockito.doReturn(Optional.empty()).when(userRepository).findByLogin(AdditionalMatchers.not(ArgumentMatchers.eq("vidroid16@gmail.com")));

        Mockito.doReturn(Optional.empty()).when(projectRepository).findById(AdditionalMatchers.not(ArgumentMatchers.eq(3L)));
        Mockito.doReturn(Optional.of(project)).when(projectRepository).findById(ArgumentMatchers.eq(3L));

        Mockito.doReturn(donation).when(donationsRepository).save(ArgumentMatchers.eq(donation));
        Mockito.doReturn(null).when(donationsRepository).save((AdditionalMatchers.not(ArgumentMatchers.eq(donation))));

    }

    @ParameterizedTest
    @MethodSource("goodCombinations")
    public void testDonationOk(String username, Long projectId, int sum, String cardNumber){

        int code = donationService.donate(username,projectId,sum,cardNumber);

        assertEquals(200,code);
    }
    @ParameterizedTest
    @MethodSource("badCombinations")
    public void testDonationNotOk(String username, Long projectId, int sum, String cardNumber){

        int code = donationService.donate(username,projectId,sum,cardNumber);

        assertNotEquals(200,code);
    }
    @Test
    public void testDonationNotEnoughMoney(){

        int code = donationService.donate("vidroid16@gmail.com", 3L, 200, "1823-4537-2930-4583");

        assertEquals(400,code);
    }
}
