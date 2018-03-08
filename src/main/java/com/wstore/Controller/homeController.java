package com.wstore.Controller;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.wstore.Domain.User;
import com.wstore.Domain.security.PasswordResetToken;
import com.wstore.Domain.security.Role;
import com.wstore.Domain.security.UserRole;
import com.wstore.Service.impl.UserSecurityService;
import com.wstore.Service.impl.UserService;
import com.wstore.Utility.MailConstructor;
import com.wstore.Utility.SecurityUtility;

@Controller
public class homeController {
	@Autowired
	private JavaMailSender mailSender;
	@Autowired
	private MailConstructor mailConstructor;
	@Autowired
	private UserService userService;
	@Autowired
	private UserSecurityService userSecurityService;
	@RequestMapping("/")
	public String home(){
		return "home";
	}
	
	@RequestMapping("/login2")
	public String login2(Model model){
		model.addAttribute("classActiveLogin",true);
		return "login";
	}
	@RequestMapping("/NewAccount")
	public String NewAccount(
			Locale locale,
			@RequestParam("token")String token,Model model){
		PasswordResetToken passToken=userService.getPasswordResetToken(token);
		
		if(passToken==null){
			String message="invalid token.";
			model.addAttribute("message",message);
			return "redirect:/badRequest";
		}
		User user =passToken.getUser();
		String username=user.getUsername();
		
		UserDetails userDetails=userSecurityService.loadUserByUsername(username);
		Authentication authentication=new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());
		SecurityContextHolder.getContext().setAuthentication(authentication);
		model.addAttribute("classActiveEdit",true);
		return "myProfile";
	}
	@RequestMapping("/ForgetPassword")
	public String ForgetPassword(
			
			Model model){
		
		model.addAttribute("classActiveForgetPassword",true);
		return "login";
	}
	
	@RequestMapping(value="/newUser",method=RequestMethod.POST)
	public String newUserPost(
			HttpServletRequest request,
			@ModelAttribute("email") String userEmail,
			@ModelAttribute("username") String username,
			Model model) throws Exception{
		
		model.addAttribute("classActiveNewAccount",true);
		model.addAttribute("email",userEmail);
		model.addAttribute("username",username);
		
		if(userService.findByUsername(username)!=null){
			model.addAttribute("usernameExists",true);
			return "login";
		}
		if(userService.findByEmail(userEmail)!=null){
			model.addAttribute("emailExists",true);
			return "login";
		}
		User user=new User();
		user.setUsername(username);
		user.setEmail(userEmail);
		
		String password=SecurityUtility.randomPassword();
		
		String encryptedPassword=SecurityUtility.passwordEncoder().encode(password);
		user.setPassword(encryptedPassword);
		Role role=new Role();
		role.setRoleId(1);
		role.setName("ROLE_USER");
		Set<UserRole> userRoles=new HashSet<>();
		userRoles.add(new UserRole(user,role));
		userService.createUser(user,userRoles);
		
		String token=UUID.randomUUID().toString();
		userService.createPasswordResetTokenForUser(user,token);
		
		String appUrl ="http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
		SimpleMailMessage email=mailConstructor.constructResetTokenEmail(appUrl,request.getLocale(),token,user,password);
		mailSender.send(email);
		model.addAttribute("emailSent","true");
		return "login";
	}
	
}
