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
package io.surati.gap.admin.db;

import com.jcabi.jdbc.JdbcSession;
import com.jcabi.jdbc.SingleOutcome;
import io.surati.gap.admin.api.User;
import io.surati.gap.admin.api.Users;
import io.surati.gap.admin.jooq.generated.tables.AdPerson;
import io.surati.gap.admin.jooq.generated.tables.AdUser;
import io.surati.gap.admin.secure.EncryptedWordImpl;
import io.surati.gap.admin.secure.GeneratedSalt;
import io.surati.gap.admin.secure.Salt;
import io.surati.gap.database.utils.exceptions.DatabaseException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.apache.commons.lang3.StringUtils;
import org.cactoos.text.Joined;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;
import org.jooq.impl.DefaultConfiguration;

/**
 * Users in Database.
 * 
 * @since 0.1
 */
public final class DbUsers implements Users {

	/**
	 * Table of user.
	 */
	private static final AdUser USER = AdUser.AD_USER;

	/**
	 * Table of person.
	 */
	private static final AdPerson PERSON = AdPerson.AD_PERSON;
	
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
	public DbUsers(final DataSource source) {
		this.source = source;
		this.ctx = DSL.using(new DefaultConfiguration().set(this.source));
	}
	
	@Override
	public boolean authenticate(final String login, final String password) {
		if(this.has(login)) {
			final User user = this.get(login);
			final String userpwd = user.password();
			final String encryptpwd = new EncryptedWordImpl(password, user.salt()).value();
			if(userpwd.equals(encryptpwd)) {
				if(user.blocked()) {
					throw new IllegalArgumentException("Vous avez été bloqué ! Veuillez SVP contacter l'administrateur.");
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public boolean authenticatePwdEncrypted(final String login, final String password) {
		if(this.has(login)) {
			final User user = this.get(login);
			if(user.password().equals(password)) {
				if(user.blocked()) {
					throw new IllegalArgumentException("Vous avez été bloqué ! Veuillez SVP contacter l'administrateur.");
				} else {
					return true;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	@Override
	public User get(final String login) {
		if(this.ctx.fetchCount(USER, USER.LOGIN.eq(login)) == 0) {
			throw new IllegalArgumentException(
				String.format("User with login %s not found !", login)
			);
		}
		return new DbUser(
			this.source,
			this.ctx.fetchOne(USER, USER.LOGIN.eq(login)).getId()
		);
	}

	@Override
	public User get(final Long id) {
		if (this.ctx.fetchCount(USER, USER.ID.eq(id)) == 0) {
			throw new IllegalArgumentException(
				String.format("User with ID %s not found !", id)
			);
		}
		return new DbUser(source, id);
	}

	@Override
	public User register(final String name, final String login, final String password) {
		if(StringUtils.isBlank(name)) 
			throw new IllegalArgumentException("Le nom  ne peut être vide !");
		if(StringUtils.isBlank(login))
			throw new IllegalArgumentException("Le login ne peut être vide !");
		if(StringUtils.isBlank(password))
			throw new IllegalArgumentException("Le mot de passe ne peut être vide !");
		if(this.has(login))
			throw new IllegalArgumentException("Ce login est déjà utilisé !");
		Long idx = this.ctx.insertInto(PERSON)
			.set(PERSON.NAME, name)
			.returning(PERSON.ID)
			.fetchOne().getId();
		final Salt salt = new GeneratedSalt();
		this.ctx.insertInto(USER)
			.set(USER.ID, idx)
			.set(USER.LOGIN, login)
			.set(USER.PASSWORD, new EncryptedWordImpl(password, salt).value())
			.set(USER.SALT, salt.value())
			.execute();
		return new DbUser(this.source, idx);
	}

	@Override
	public Iterable<User> iterate() {
		return this.ctx
			.selectFrom(USER)
			.fetch(
				rec -> new DbUser(
					this.source, rec.getId()
				)
			);
	}

	@Override
	public boolean has(final String login) {
		return this.ctx
			.fetchCount(
				USER,
				USER.LOGIN.eq(login)
			) > 0;
	}
	
	@Override
	public void remove(final Long id) {		
		try {
			final boolean used = new JdbcSession(this.source)
	            .sql(
	                new Joined(
	                    " ",
	                    "SELECT COUNT(*) FROM AD_USER",
	                    "WHERE id=? AND (login IN (SELECT author FROM event_log)",
	                    "OR id IN (SELECT author_id FROM payment_order)",
	                    "OR id IN (SELECT author_id FROM payment_order_group)",
	                    "OR id IN (SELECT issuer_id FROM bank_note)",
	                    "OR id IN (SELECT author_id FROM reference_document))"
	                ).toString()
	            )
	            .set(id)
	            .select(new SingleOutcome<>(Long.class)) > 0;
	        if(used) {
	        	throw new IllegalArgumentException(
					"L'utilisateur ne peut pas être supprimé (déjà utilisé) !"
				);
	        }
		} catch (SQLException ex) {
			throw new DatabaseException(ex);
		}
		try (
			final Connection connection = this.source.getConnection();
			final PreparedStatement pstmt = connection.prepareStatement("DELETE FROM AD_USER WHERE id=?");
			final PreparedStatement pstmt1 = connection.prepareStatement("DELETE FROM person WHERE id=?");
		) {
			pstmt.setLong(1, id);
			pstmt1.setLong(1, id);
			pstmt.executeUpdate();
			pstmt1.executeUpdate();
		} catch (SQLException e) {
			throw new DatabaseException(e);
		}
	}
	
	@Override
	public Long count() {
		return Long.valueOf(
			this.ctx.fetchCount(USER)
		);
	}

}
