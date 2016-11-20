/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.seed.entities;


import io.seed.enums.AuthProvider;
import org.hibernate.validator.constraints.NotBlank;

import javax.annotation.PostConstruct;
import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

import static io.seed.enums.AuthProvider.LOCAL;
import static io.seed.enums.AuthProvider.PHONE;
import static io.seed.utils.Helper.getRandStringSimple;
import static java.time.LocalDateTime.now;
import static org.springframework.util.DigestUtils.md5DigestAsHex;

@Entity
@Table(name = "auths")
@NamedQueries({
		@NamedQuery(name = "Auth.login", query = "SELECT a FROM Auth a" +
				" where a.username = :username and a.password = md5(concat(:password,a.salt))")
})
public class Auth extends BaseEntity {

	@Column(name = "provider")
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;
	@Size(max = 50)
	@NotBlank
	@Column(name = "username")
	private String username;
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	private User user;
	@Size(max = 20)
	@Column(name = "recovery_hash")
	private String recoveryHash;
	@Column(name = "recovery_expires")
	private LocalDateTime recoveryExpires;
	@Size(min = 1, max = 20)
	@Column(name = "username_verify_hash")
	private String usernameVerifyHash;
	@Column(name = "username_verify_expires")
	private LocalDateTime usernameVerifyExpires;
	@Column(name = "username_verified")
	private Boolean usernameVerified = false;
	@Size(max = 32)
	@Column(name = "password")
	private String password;
	@Size(max = 32)
	@Column(name = "salt", updatable = false)
	private String salt;
	@Transient
	private String token;

	public Auth() {
	}

	public Auth(String username, String password) {
		this.username = username;
		setPassword(password);
		newUsernameVerification();
	}
	
	private void setSalt() {
		if (this.salt == null) {
			this.salt = getRandStringSimple(32);
		}
	}

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void newRecoveryHash() {
		recoveryHash = getRandStringSimple(20);
		recoveryExpires = now().plusDays(3);
	}

	public void usedRecoveryHash() {
		recoveryHash = null;
		recoveryExpires = null;
	}

	public String getUsernameVerifyHash() {
		return usernameVerifyHash;
	}

	public void setUsernameVerifyHash(String usernameVerifyHash) {
	}

	public LocalDateTime getUsernameVerifyExpires() {
		return usernameVerifyExpires;
	}

	public void setUsernameVerifyExpires(LocalDateTime usernameVerifyExpires) {
	}

	public void newUsernameVerification() {
		if (provider == null || LOCAL.contains(provider)) {
			usernameVerifyHash = getRandStringSimple(provider == PHONE ? 6 : 20);
			usernameVerifyExpires = now().plusDays(3);
			usernameVerified = false;
		}
	}

	public Boolean getUsernameVerified() {
		return usernameVerified;
	}

	public void setUsernameVerified(Boolean usernameVerified) {
	}

	public void usernameVerified() {
		usernameVerified = true;
	}

	public void setPassword(String password) {
		setSalt();
		this.password = md5DigestAsHex((password + salt).getBytes());
	}

	public String getRecoveryHash() {
		return recoveryHash;
	}

	public void setRecoveryHash(String recoveryHash) {
		this.recoveryHash = recoveryHash;
	}

	public LocalDateTime getRecoveryExpires() {
		return recoveryExpires;
	}

	public void setRecoveryExpires(LocalDateTime recoveryExpires) {
		this.recoveryExpires = recoveryExpires;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
