package com.wstore.Domain.security;

import org.springframework.security.core.GrantedAuthority;

public class Authority implements GrantedAuthority{
 /**
	 * alt satırı düzenleme opsiyonu seçince otomatik ayarlandı
	 */
	private static final long serialVersionUID = -6358458414180522245L; 
private final String authority;
 public Authority(String authority){
	 this.authority=authority;
 }
 @Override
public String getAuthority() {
	return authority;
}
 
}
