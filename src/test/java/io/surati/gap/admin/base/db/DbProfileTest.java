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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import com.lightweight.db.EmbeddedPostgreSQLDataSource;

import io.surati.gap.admin.base.api.Profile;
import io.surati.gap.database.utils.extensions.DatabaseSetupExtension;

/**
 * Test case for {@link DbProfile}.
 *
 * @since 0.5.4
 */
final class DbProfileTest {

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
     * Profile to test.
     */
    private Profile profile;
    
    @BeforeEach
    void setUp() {
        this.profile = new DbProfile(this.src, 1L);
    }
    
    @Test
	public void updatesProfile() {
    	MatcherAssert.assertThat(
    		this.profile.name(),
            Matchers.equalTo("Administrateur")
        );
    	this.profile.update("Marx");
    	MatcherAssert.assertThat(
    			this.profile.name(),
                Matchers.equalTo("Marx")
        );
    }
}
