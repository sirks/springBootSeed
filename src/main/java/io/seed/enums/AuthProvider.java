/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.seed.enums;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

public enum AuthProvider {
	FACEBOOK, TWITTER, PHONE, EMAIL;

	public static final List<AuthProvider> LOCAL = newArrayList(PHONE, EMAIL);
}
