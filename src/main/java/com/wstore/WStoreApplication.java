package com.wstore;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.wstore.Domain.User;
import com.wstore.Domain.security.Role;
import com.wstore.Domain.security.UserRole;
import com.wstore.Service.impl.UserService;
import com.wstore.Utility.SecurityUtility;

@SpringBootApplication
public class WStoreApplication implements CommandLineRunner{
@Autowired
private UserService userService;
	public static void main(String[] args) {
		SpringApplication.run(WStoreApplication.class, args);
	}
	@Override
	public void run(String...args) throws Exception{
		User user1=new User();
		user1.setName("merve");
		user1.setUsername("m");
		user1.setPassword(SecurityUtility.passwordEncoder().encode("p"));
		user1.setEmail("m.karabudak28@gmail.com");
		Set<UserRole> userRoles=new HashSet<>();
		Role role1=new Role();
		role1.setRoleId(1);
		role1.setName("ROLE_USER");
		userRoles.add(new UserRole(user1,role1));
		
		userService.createUser(user1,userRoles);
	}
}
