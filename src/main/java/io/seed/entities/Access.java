/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.seed.entities;


import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Entity
@Table(name = "accesses")
@NamedQueries({
		@NamedQuery(name = "Access.token", query = "SELECT a FROM Access a where a.token = :token and a.expires >= CURRENT_TIMESTAMP")
})
public class Access extends BaseEntity {

	@JoinColumn(name = "authid", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Auth auth;
	@Size(max = 256)
	@Column(name = "token")
	private String token;
	@Column(name = "expires")
	private LocalDateTime expires;

	public Access() {
	}

	public Access(Auth auth, String token) {
		this.auth = auth;
		this.token = token;
	}

	public Access(Auth auth, String token, LocalDateTime expires) {
		this.auth = auth;
		this.token = token;
		this.expires = expires;
	}

	public Auth getAuth() {
		return auth;
	}

	public void setAuth(Auth auth) {
		this.auth = auth;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
}
