package com.wstore.Service.impl;

import java.util.Set;

import com.wstore.Domain.User;
import com.wstore.Domain.security.PasswordResetToken;
import com.wstore.Domain.security.UserRole;

public interface UserService {
	PasswordResetToken getPasswordResetToken(final String token);
	
	void createPasswordResetTokenForUser(final User user,final String token);
	User findByUsername(String username);
	User findByEmail(String email);
	User createUser(User user,Set<UserRole> userRoles) throws  Exception;
}
