package com.wstore.Service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wstore.Domain.User;
import com.wstore.Domain.security.PasswordResetToken;
import com.wstore.Domain.security.UserRole;
import com.wstore.Repository.PasswordResetTokenRepository;
import com.wstore.Repository.RoleRepository;
import com.wstore.Repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
@Override
public PasswordResetToken getPasswordResetToken(final String token){
	return passwordResetTokenRepository.findByToken(token);
}
@Override
public void createPasswordResetTokenForUser(final User user,final String token){
	final PasswordResetToken myToken=new PasswordResetToken(token,user);
	passwordResetTokenRepository.save(myToken);
}
@Override
public User findByUsername(String username){
	return userRepository.findByUsername(username);
}
@Override
public User findByEmail(String email){
	return userRepository.findByEmail(email);
}
@Override
public User createUser(User user,Set<UserRole> userRoles) throws  Exception{
	User localUser=userRepository.findByUsername(user.getUsername());
	if(localUser !=null){
		throw new Exception("user already exists");
	}else{
		for (UserRole ur:userRoles){
			roleRepository.save(ur.getRole());
		}
		
		user.getUserRoles().addAll(userRoles);
		localUser=userRepository.save(user);
	}
	return localUser;
}
}


