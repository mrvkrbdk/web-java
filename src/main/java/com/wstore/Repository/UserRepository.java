package com.wstore.Repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import com.wstore.Domain.User;
import com.wstore.Domain.security.UserRole;

public interface UserRepository extends CrudRepository<User,Long>{
	User findByUsername(String username);

	User findByEmail(String email);
	
	User createUser(User user,Set<UserRole> userRoles);
}
