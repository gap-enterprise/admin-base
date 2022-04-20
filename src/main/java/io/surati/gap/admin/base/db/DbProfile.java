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

/**
 * Profile from Database
 *
 * @since 0.5.4
 */
import io.surati.gap.admin.base.api.Profile;
import io.surati.gap.admin.base.api.ProfileAccesses;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdProfile;
import io.surati.gap.admin.base.db.jooq.generated.tables.records.AdProfileRecord;
import io.surati.gap.database.utils.jooq.JooqContext;
import javax.sql.DataSource;
import org.jooq.DSLContext;

public final class DbProfile implements Profile {

	/**
	 * Record.
	 */
	private final AdProfileRecord record;
	
	/**
	 * DataSource.
	 */
	private final DataSource source;
	
	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;

	/**
	 * Ctor.
	 * @param source DataSource
	 * @param id Identifier
	 */
	public DbProfile(final DataSource source, final Long id) {	
		this.source = source;
		this.ctx = new JooqContext(source);
		this.record = this.ctx.fetchOne(AdProfile.AD_PROFILE, AdProfile.AD_PROFILE.ID.eq(id));
	}
		
	/**
	 * Checks if a Profile with name and not id exists
	 * @param name
	 * @return boolean exits
	 */
	private boolean nameIsUsed(String name) {
		return this.ctx
					.fetchCount(
						AdProfile.AD_PROFILE,
						AdProfile.AD_PROFILE.NAME.eq(name),
						AdProfile.AD_PROFILE.ID.notEqual(this.id())
					) > 0;
	}
	
	@Override
	public Long id() {
		return this.record.getId();
	}

	@Override
	public String name() {
		return this.record.getName();
	}
	
	@Override
	public void update(final String name) {
		if(name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Le nom doit être renseigné !");
		if(this.nameIsUsed(name))
			throw new IllegalArgumentException(
					String.format("Le nom %s est déjà utilisé", name)
			);
		this.record.setName(name);
		this.record.store();
			
	}

	@Override
	public ProfileAccesses accesses() {
		return new DbProfileAccesses(this.source, this);
	}

	@Override
	public String toString() {
		return String.format("ID=%s, Intitulé=%s", this.id(), this.name());
	}
}
