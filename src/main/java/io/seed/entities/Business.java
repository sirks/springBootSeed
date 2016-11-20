/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.seed.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "businesses")
public class Business extends BaseEntity {

    @Column(name = "description")
    private String description;
    @Size(max = 50)
    @Column(name = "phone")
    private String phone;
    @Size(max = 200)
    @Column(name = "email")
    private String email;
    @Size(max = 200)
    @Column(name = "www")
    private String www;
    @Size(max = 200)
    @Column(name = "twitter")
    private String twitter;
    @Size(max = 200)
    @Column(name = "facebook")
    private String facebook;
    @Lob
    @Size(max = 65535)
    @Column(name = "reg_notes")
    private String regNotes;
    @Size(min = 1, max = 200)
    @Column(name = "title")
    private String title;
    @Size(min = 1, max = 200)
    @Column(name = "brand")
    private String brand;
    @Size(min = 1, max = 20)
    @Column(name = "regnr")
    private String regnr;
    @Size(max = 20)
    @Column(name = "vatnr")
    private String vatnr;
	@Size(max = ID_LEN)
    @OneToMany(cascade = CascadeType.MERGE, mappedBy = "business")
    private List<User> users;
	@Size(max = ID_LEN)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Address address;
	@Size(max = ID_LEN)
	@JoinColumn(name = "legal_address_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private Address legalAddress;

    public Business() {
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getWww() {
		return www;
	}

	public void setWww(String www) {
		this.www = www;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getRegNotes() {
		return regNotes;
	}

	public void setRegNotes(String regNotes) {
		this.regNotes = regNotes;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getRegnr() {
		return regnr;
	}

	public void setRegnr(String regnr) {
		this.regnr = regnr;
	}

	public String getVatnr() {
		return vatnr;
	}

	public void setVatnr(String vatnr) {
		this.vatnr = vatnr;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Address getLegalAddress() {
		return legalAddress;
	}

	public void setLegalAddress(Address legalAddress) {
		this.legalAddress = legalAddress;
	}
}
