package com.fundamentosplatzi.springboot.fundamentos;

import com.fundamentosplatzi.springboot.fundamentos.bean.MyBean;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependency;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithDependencyImplement;
import com.fundamentosplatzi.springboot.fundamentos.bean.MyBeanWithProperties;
import com.fundamentosplatzi.springboot.fundamentos.component.ComponentDependency;
import com.fundamentosplatzi.springboot.fundamentos.entity.User;
import com.fundamentosplatzi.springboot.fundamentos.pojo.UserPojo;
import com.fundamentosplatzi.springboot.fundamentos.repository.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class FundamentosApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(FundamentosApplication.class);
	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;

	private UserRepository userRepository;

	// @Autowired not needed
	public FundamentosApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
								  MyBean myBean, MyBeanWithDependency myBeanWithDependency,
								  MyBeanWithProperties myBeanWithProperties, UserPojo userPojo,
								  UserRepository userRepository){
		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
	}
	public static void main(String[] args) {
		SpringApplication.run(FundamentosApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		// ejemplosAnteriores();
		saveUsersInDataBase();
		getInformationJpqlFromUser();

	}

	private void getInformationJpqlFromUser(){
		LOGGER.info("Usuario con email user5@domain.com " +
				userRepository.findByUserEmail("user5@domain.com")
						.orElseThrow(()-> new RuntimeException("No se encontró el usuario")));

/*		LOGGER.info("Usuario con email noexiste@domain.com" +
				userRepository.findByUserEmail("noexiste@domain.com")
						.orElseThrow(()-> new RuntimeException("No se encontró el usuario")));*/

		userRepository.findAndSort("user", Sort.by("id").ascending())
				.forEach(u-> LOGGER.info("usuario con método sort" + u));

		userRepository.findByName("John")
				.forEach(u -> LOGGER.info("Usuario con query method" + u));

		LOGGER.info("Usuario con query method email and name" +
				userRepository.findByEmailAndName("daniela@domain.com", "Daniela")
				.orElseThrow(()-> new RuntimeException("No se encontró el usuario")));
	}
	private void saveUsersInDataBase(){
		User user1 = new User("John", "john@domain.com", LocalDate.of(2021,3,20));
		User user2 = new User("Julie", "julie@domain.com", LocalDate.of(2021,5,21));
		User user3 = new User("Daniela", "daniela@domain.com", LocalDate.of(2001,1,11));
		User user4 = new User("user4", "user4@domain.com", LocalDate.of(2001,12,28));
		User user5 = new User("user5", "user5@domain.com", LocalDate.of(2001,8,30));
		User user6 = new User("user6", "user6@domain.com", LocalDate.of(2001,9,3));
		User user7 = new User("user7", "user7@domain.com", LocalDate.of(2001,5,14));
		User user8 = new User("user8", "user8@domain.com", LocalDate.of(2001,10,10));
		User user9 = new User("user9", "user9@domain.com", LocalDate.of(2001,4,9));
		User user10 = new User("user10", "user10@domain.com", LocalDate.of(2001,03,8));
		List<User> list = Arrays.asList(user1, user2, user3, user4, user5, user6, user7, user8, user9, user10);

		userRepository.saveAll(list);

	}
	public void ejemplosAnteriores() {
		componentDependency.saludar();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail() + "-" + userPojo.getPassword());

		try {
			int value = 10/0;
			LOGGER.debug("Mi valor " + value);
		} catch (Exception e){
			LOGGER.error("Esto es un error al dividir por 0" + e.getMessage());
		}

	}
}
