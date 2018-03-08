package com.wstore.Domain;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wstore.Domain.security.Authority;
import com.wstore.Domain.security.UserRole;

@SuppressWarnings("serial")          //burayı sonradan ekledim!!!1
@Entity 
public class User implements UserDetails{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO) //Auto stratejisi seçilirse altta kullanılan veritabanı türüne göre 
	//Hibernate kullanacağı yöntemi seçer. Örneğin Oracle için sequence yöntemini kullanırken, MySQL için Identity yöntemin
	//https://yazilimcorbasi.blogspot.com.tr/2012/01/hibernate-ve-generatedvalue-kullanm.html -->burdan tekrar incele
	@Column(name="id",nullable=false,updatable=false)
	private Long id;
	
	private String name;
	private String username;
	@Column(name="email",nullable=false,updatable=false)
	private String email;
	private String adres;
	private String tel;
	private String password;
	
	private boolean enabled=true;
	
	@OneToMany(mappedBy="user", cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JsonIgnore
	private Set<UserRole> userRoles=new HashSet<>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAdres() {
		return adres;
	}
	public void setAdres(String adres) {
		this.adres = adres;
	}
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public Set<UserRole> getUserRoles() {
		return userRoles;
	}
	public void setUserRoles(Set<UserRole> userRoles) {
		this.userRoles = userRoles;
	}
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Set<GrantedAuthority> authorites=new HashSet<>();
		userRoles.forEach(ur-> authorites.add(new Authority(ur.getRole().getName())));
		return authorites;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled(){
		return enabled;
	}
}
