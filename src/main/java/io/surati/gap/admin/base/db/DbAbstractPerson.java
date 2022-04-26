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
import io.surati.gap.admin.base.api.Person;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdPerson;
import io.surati.gap.admin.base.db.jooq.generated.tables.records.AdPersonRecord;
import io.surati.gap.database.utils.jooq.JooqContext;

/**
 * Person in Database.
 * 
 * @since 0.5
 */
public abstract class DbAbstractPerson implements Person {

	/**
	 * Data source
	 */
	protected final DataSource source;

	/**
	 * Record.
	 */
	private final AdPersonRecord record;
	
	/**
	 * Id of person
	 */
	protected final Long id;
	
	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbAbstractPerson(final DataSource source, final Long id) {
		this.source = source;
		this.id = id;
		this.record = new JooqContext(source).fetchOne(AdPerson.AD_PERSON, AdPerson.AD_PERSON.ID.eq(id));
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
	public String address() {
		return this.record.getAddress();
	}

	@Override
	public String pobox() {
		return this.record.getPobox();
	}

	@Override
	public String phone() {
		return this.record.getPhone();
	}

	@Override
	public String email() {
		return this.record.getEmail();
	}

	@Override
	public void update(final String name) {
		if(StringUtils.isBlank(name))
			throw new IllegalArgumentException("Le nom doit être renseigné !");
		this.record.setName(name);
		this.record.store();
	}

	@Override
	public void contacts(
		final String address, final String pobox, final String phone, final String email
	) {
		this.record.setAddress(address);
		this.record.setPobox(pobox);
		this.record.setPhone(phone);
		this.record.setEmail(email);
		this.record.store();
	}
}
