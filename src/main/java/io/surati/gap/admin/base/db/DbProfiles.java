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

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.jooq.DSLContext;

import io.surati.gap.admin.base.api.Profile;
import io.surati.gap.admin.base.api.Profiles;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdProfile;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdUser;
import io.surati.gap.database.utils.jooq.JooqContext;

/**
 * Profiles in Database.
 * 
 * @since 0.5
 */
public final class DbProfiles implements Profiles {
	
	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;
	
	/**
	 * DataSource source.
	 */
	private final DataSource source;
	
	/**
	 * Ctor.
	 * @param source
	 */
	public DbProfiles(final DataSource source) {
		this.source = source;
		this.ctx = new JooqContext(this.source);
	}
	
	@Override
	public Profile get(Long id) {
		if(!this.has(id)) {
			throw new IllegalArgumentException(
				String.format("Le profil avec ID %s n'a pas été trouvé !", id)
			);
		}
		return new DbProfile(
			this.source,
			id
		);
	}

	private boolean has(final Long id) {
		return this.ctx
				   .fetchCount(
						AdProfile.AD_PROFILE,
						AdProfile.AD_PROFILE.ID.eq(id)
					) > 0;
	}
	
	private boolean has(final String name) {
		return this.ctx
				   .fetchCount(
						AdProfile.AD_PROFILE,
						AdProfile.AD_PROFILE.NAME.eq(name.trim())
					) > 0;
	}
	
	@Override
	public Profile add(String name) {		
		if(StringUtils.isBlank(name)) {
			throw new IllegalArgumentException("Le nom ne peut être vide !");
		}
		if(this.has(name)) {
			throw new IllegalArgumentException(
					String.format("Le nom %s est déjà utilisé !", name)
				);
		}
		
		final Long idx = this.ctx.insertInto(AdProfile.AD_PROFILE)
							 .set(AdProfile.AD_PROFILE.NAME, name)
							 .returning(AdProfile.AD_PROFILE.ID)
							 .fetchOne().getId();
	    return new DbProfile(this.source, idx);
	}

	@Override
	public Iterable<Profile> iterate() {	
		return this.ctx
				   .selectFrom(AdProfile.AD_PROFILE)
				   .fetch(
						rec -> new DbProfile(
							this.source, rec.getId()
						)
					);
	}
	
	@Override
	public void remove(final Long id) {
		final boolean used = this.ctx
								 .fetchCount(
									 AdUser.AD_USER,
									 AdUser.AD_USER.PROFILE_ID.eq(id)
							     ) > 0;
	    if(used) {
	        throw new IllegalArgumentException("Le profil ne peut pas être supprimé (déjà utilisé) !");
	    }
	    else {
	    	this.ctx.delete(AdProfile.AD_PROFILE)
			.where(AdProfile.AD_PROFILE.ID.eq(id))
			.execute();
	    }
	}
}
