package io.seed.entities

import spock.lang.Specification

class AuthTest extends Specification {

	Auth auth = new Auth();

	def "check password generation"() {
		given:
		auth.salt = ""

		when:
		auth.setPassword("my-secret-password")

		then:
		auth.password == "a2d9679c7fff3e9cf4300a2cc3fbad79"
	}
}
