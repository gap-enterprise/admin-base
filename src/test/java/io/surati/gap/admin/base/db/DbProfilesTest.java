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

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import javax.management.InvalidApplicationException;

import org.cactoos.list.ListOf;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.llorllale.cactoos.matchers.Satisfies;

import com.lightweight.db.EmbeddedPostgreSQLDataSource;
import io.surati.gap.admin.base.api.Profile;
import io.surati.gap.admin.base.api.Profiles;
import io.surati.gap.database.utils.extensions.DatabaseSetupExtension;

/**
 * Test case for {@link DbProfiles}.
 *
 * @since 0.5
 */
final class DbProfilesTest {

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
     * Profiles.
     */
    private Profiles profiles;
    
    /**
     * Profile
     */
    private Profile profile;
    

    @BeforeEach
    void setUp() {
        this.profiles = new DbProfiles(this.src);
        this.profile = new DbProfile(this.src, 1L);
    }
    
	@Test
	public Profile getsProfileById() {
		final Profile profile = this.profiles.get(1L);
		MatcherAssert.assertThat(
			profile,
			new Satisfies<>(
				prf -> prf.id().equals(this.profile.id()) &&
				prf.name().equals(this.profile.name()) &&
				prf.accesses().equals(this.profile.accesses())
			)
		);
		return this.profiles.get(this.profile.id());
	}
	
	@Test
	public Profile addsProfile() {
		final String name = "Guest";
		final Profile profile = this.profiles.add(name);
		MatcherAssert.assertThat(
			profile,
			new Satisfies<>(
				prf -> prf.name().equals(name)
			)
		);
		return this.profiles.get(profile.id());
	}
	
	@Test
    void iterates() {
    	final String[] names = {"Administrateur", "Anonyme", "Mentor", "Guest"};
    	this.profiles.add(names[2]);
    	this.profiles.add(names[3]);
		int idx = 0;
		for (final Profile profile : this.profiles.iterate()) {
			final String name = names[idx];
			MatcherAssert.assertThat(
				"Profiles should match in ascending order.",
				profile,
				new Satisfies<>(
					prf -> prf.name().equals(name)
				)
			);
			idx ++;
		}
    }
	
	@Test
    void removesUnsedProfile() {
		final Profile newProfile =  this.profiles.add("Guest");
		this.profiles.remove(newProfile.id());
		MatcherAssert.assertThat(
            "Profile should be deleted.",
            new ListOf<>(this.profiles.iterate()).stream().noneMatch(p -> p.id().equals(newProfile.id())),
            Matchers.is(true)
        );
    }
	
	@Test
    void removesProfileAlreadyUsed() {
		Exception exception = assertThrows(IllegalArgumentException.class, () -> {
	        this.profiles.remove(this.profile.id());
	    });

	    String expectedMessage = "Le profil ne peut pas être supprimé (déjà utilisé) !";
	    String actualMessage = exception.getMessage();

	    assertTrue(actualMessage.contains(expectedMessage));
    }
}
