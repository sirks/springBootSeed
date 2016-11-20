/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.seed.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDateTime;

import static io.seed.utils.Helper.buildID;
import static java.time.LocalDateTime.now;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	public static final int ID_LEN = 11;
	
	@Id
	@Size(max = ID_LEN)
	@Column(name = "id")
	private String id;
	@Column(name = "createdtime", updatable = false)
	private LocalDateTime createdTime;
	@Column(name = "updatedtime")
	private LocalDateTime updatedTime;

	public BaseEntity() {
	}

	public BaseEntity(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public LocalDateTime getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(LocalDateTime createdTime) {
		this.createdTime = createdTime;
	}

	public LocalDateTime getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(LocalDateTime updatedTime) {
		this.updatedTime = updatedTime;
	}

	public void onSave() {
		onCreate();
		onUpdate();
	}

	private void onCreate() {
		if (id == null) {
			id = buildID();
		}
		if (createdTime == null) {
			createdTime = now();
		}
	}

	private void onUpdate() {
		if (updatedTime == null) {
			updatedTime = now();
		}
	}

	public void calcFields() {
	}

	public boolean valid() {
		return true;
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "[" + id + "]";
	}
}
