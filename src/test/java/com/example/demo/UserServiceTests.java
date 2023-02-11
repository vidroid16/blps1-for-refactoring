package com.example.demo;

import com.example.demo.DataBase.UsersDB.User;
import com.example.demo.DataBase.UsersDB.UsersRepository;
import com.example.demo.Services.Implementations.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.AdditionalMatchers;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceTests {

	@Autowired
	private UserServiceImpl userService;

	@MockBean
	private UsersRepository userRepository;
	@MockBean
	private AuthenticationManager authenticationManager;


	static Stream<Arguments> goodCombinations() {
		return Stream.of(
				Arguments.of("vidroid16@gmail.com", "password")

		);
	}
	static Stream<Arguments> badCombinations() {
		return Stream.of(
				Arguments.of("vidroid16@gmail.com", "vidroid16@gmail.com"),
				Arguments.of("password", "password"),
				Arguments.of("daddw", "123qwerty")

		);
	}

	@BeforeEach
	public void init() {
		String password = "password";
		User user = new User();
		user.setLogin("vidroid16@gmail.com");
		user.setPassword("password");

		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getLogin(),password);

		Mockito.doReturn(Optional.of(user)).when(userRepository).findByLogin(ArgumentMatchers.eq(user.getLogin()));
		Mockito.doReturn(Optional.empty()).when(userRepository).findByLogin(AdditionalMatchers.not(ArgumentMatchers.eq(user.getLogin())));

		when(authenticationManager.authenticate(any())).thenAnswer(invocation ->{
			UsernamePasswordAuthenticationToken tokenAsserted = (UsernamePasswordAuthenticationToken)invocation.getArguments()[0];
			if(tokenAsserted.getCredentials().equals(password) && tokenAsserted.getPrincipal().equals(user.getLogin()))
				return null;
			else
				throw new UsernameNotFoundException("smth");
		});

	}

	@ParameterizedTest
	@MethodSource("goodCombinations")
	public void testAuthOk(String username, String password){
		String a = userService.login(username,password);
		assertNotEquals("bad",a);
	}
	@ParameterizedTest
	@MethodSource("badCombinations")
	public void testAuthNotOk(String username, String password){

		String a = userService.login(username,password);
		assertEquals("bad",a);
	}

}
