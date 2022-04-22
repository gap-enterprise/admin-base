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

import io.surati.gap.admin.base.api.Access;
import io.surati.gap.admin.base.api.Profile;
import io.surati.gap.admin.base.api.ProfileAccesses;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdAccessProfile;
import io.surati.gap.database.utils.jooq.JooqContext;

import javax.sql.DataSource;

import org.jooq.DSLContext;

/**
 * All profile accesses from Database.
 *
 * @since 0.5
 */
public final class DbProfileAccesses implements ProfileAccesses {
	
	/**
	 * jOOQ database context.
	 */
	private final DSLContext ctx;

	/**
	 * Profile.
	 */
	private final Profile profile;
	
	/**
	 * Ctor.
	 * @param source DataSource
	 * @param profile Profile
	 */
	public DbProfileAccesses(final DataSource source, final Profile profile) {
		this.profile = profile;
		this.ctx = new JooqContext(source);
	}

	private final boolean isAdmin() {
		return this.profile.name().equals("Administrateur");
	}

	@Override
	public Iterable<Access> iterate() {
		if(this.isAdmin()) {
			return () -> Access.VALUES.stream().filter(
				a -> !a.code().equals("TRAVAILLER_DANS_SON_PROPRE_ESPACE_DE_TRAVAIL")
			).iterator();
		} else {
			return this.ctx
				       .selectFrom(AdAccessProfile.AD_ACCESS_PROFILE)
				       .where(AdAccessProfile.AD_ACCESS_PROFILE.PROFILE_ID.eq(this.profile.id()))
				       .fetch(
							rec -> Access.get(
								rec.getAccessId()
							)
					   );
		}
	}

	@Override
	public boolean has(final Access access) {
		if(this.isAdmin() && !access.code().equals("TRAVAILLER_DANS_SON_PROPRE_ESPACE_DE_TRAVAIL")) {
			return true;
		} else {
			return this.ctx.selectCount()
					   .from(AdAccessProfile.AD_ACCESS_PROFILE)
					   .where(AdAccessProfile.AD_ACCESS_PROFILE.PROFILE_ID.eq(this.profile.id()))
					   .and(AdAccessProfile.AD_ACCESS_PROFILE.ACCESS_ID.eq(access.code()))
					   .fetchOne(0, Integer.class) > 0;
		}
	}

	@Override
	public void add(final Access access) {
		if(this.isAdmin()) {
			return;
		}
		this.ctx.insertInto(AdAccessProfile.AD_ACCESS_PROFILE)
				.set(AdAccessProfile.AD_ACCESS_PROFILE.ACCESS_ID, access.code())
				.set(AdAccessProfile.AD_ACCESS_PROFILE.PROFILE_ID, this.profile.id())
				.execute();
	}

	@Override
	public void remove(final Access access) {
		if(this.isAdmin()) {
			return;
		}
		this.ctx.delete(AdAccessProfile.AD_ACCESS_PROFILE)
		.where(AdAccessProfile.AD_ACCESS_PROFILE.ACCESS_ID.eq(access.code()))
		.and(AdAccessProfile.AD_ACCESS_PROFILE.PROFILE_ID.eq(this.profile.id()))
		.execute();
	}
	
	@Override
	public void removeAll() {
		if(this.isAdmin()) {
			return;
		}
		this.ctx.delete(AdAccessProfile.AD_ACCESS_PROFILE)
		.where(AdAccessProfile.AD_ACCESS_PROFILE.PROFILE_ID.eq(this.profile.id()))
		.execute();
	}
}
