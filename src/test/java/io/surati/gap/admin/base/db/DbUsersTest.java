/*
 * Copyright (c) 2022 Surati

 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
	
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.base.db;

import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import io.surati.gap.admin.base.api.Profile;
import io.surati.gap.admin.base.api.User;
import io.surati.gap.admin.base.api.Users;
import io.surati.gap.database.utils.extensions.DatabaseSetupExtension;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsEqual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.llorllale.cactoos.matchers.Satisfies;

/**
 * Test case for {@link DbUsers}.
 *
 * @since 0.1
 */
final class DbUsersTest {

	/**
	 * Database setup extension.
	 * @checkstyle VisibilityModifierCheck (5 lines)
	 */
	@RegisterExtension
	final DatabaseSetupExtension src = new DatabaseSetupExtension(
		new AdminDatabaseBuiltWithLiquibase(
			new EmbeddedPostgreSQLDataSource()
		)
	);

    /**
     * Users.
     */
    private Users users;
    
    /**
     * User admin.
     */
    private User admin;

	/**
	 * User profile.
	 */
	private Profile profile;

	@BeforeEach
	void setUp() {
		this.users = new DbUsers(this.src);
		this.admin = new DbUser(this.src, 1L);
		this.profile = new DbProfile(this.src, 1L);
	}
	
	@Test
	void registersUser() {
		final String name = "Marx Brou";
		final String login = "brou87";
		final User user = this.users.register(
			name, login, "broupwd", this.profile
		);
		MatcherAssert.assertThat(
			user,
			new Satisfies<>(
				usr -> usr.name().equals(name) &&
				   usr.login().equals(login)
			)
		);
	}
	
	@Test
	public void checksUserExistenceByItsLogin() {
		MatcherAssert.assertThat(
			this.users.has(this.admin.login()),
			Matchers.is(true)
		);
	}
	
	@Test
	public void getsUserById() {
		final User user = this.users.get(this.admin.id());
		MatcherAssert.assertThat(
			user,
			new Satisfies<>(
				usr -> usr.id().equals(this.admin.id()) &&
				   usr.name().equals(this.admin.name()) &&
                   usr.login().equals(this.admin.login())
			)
		);
	}
	
	@Test
	void countsTotalNumber() {
		this.users.register("Mentor", "mentor", "mentorpwd", this.profile);
		this.users.register("Guest", "guest", "guestpwd", this.profile);
		MatcherAssert.assertThat(
            this.users.count(),
            Matchers.equalTo(3L)
        );
	}
	
	@Test
	void authenticatesWithNonEncryptedPassword() {
		MatcherAssert.assertThat(
			this.users.authenticate(this.admin.login(), "admin"),
			Matchers.is(true)
		);
	}
	
	@Test
	void authenticatesWithEncryptedPassword() {
		MatcherAssert.assertThat(
			users.authenticatePwdEncrypted(
				this.admin.login(),
				this.admin.password()
			),
			Matchers.is(true)
		);
	}
	
	@Test
    void iterates() {
    	final String[] names = {"Administrateur", "Mentor", "Guest"};
		final String[] logins = {"admin", "mentor", "guest"};
		this.users.register(names[1], logins[1], "pwd1", this.profile);
		this.users.register(names[2], logins[2], "pwd2", this.profile);
		MatcherAssert.assertThat(
            "Application should have three users.",
            this.users.count(),
            new IsEqual<>(Long.valueOf(3L))
        );
		int idx = 0;
		for (final User user : this.users.iterate()) {
			final String name = names[idx];
			final String login = logins[idx];
			MatcherAssert.assertThat(
				"Users should match in ascending order.",
				user,
				new Satisfies<>(
					usr -> usr.name().equals(name) &&
						   usr.login().equals(login)
				)
			);
			idx ++;
		}
    }
}
