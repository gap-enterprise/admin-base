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

import io.surati.gap.admin.base.api.EventLog;
import io.surati.gap.admin.base.db.jooq.generated.tables.AdEventLog;
import io.surati.gap.admin.base.db.jooq.generated.tables.records.AdEventLogRecord;
import io.surati.gap.database.utils.jooq.JooqContext;
import java.time.LocalDateTime;
import java.util.logging.Level;
import javax.sql.DataSource;

/**
 * Log event from database.
 *
 * @since 0.1
 */
public final class DbEventLog implements EventLog {

	/**
	 * Table of log events.
	 */
	private static final AdEventLog EVENT_LOG = AdEventLog.AD_EVENT_LOG;

	/**
	 * Record.
	 */
	private final AdEventLogRecord record;

	/**
	 * Ctor.
	 * @param source Data source
	 * @param id Identifier
	 */
	public DbEventLog(final DataSource source, final Long id) {
		this.record = new JooqContext(source)
			.fetchOne(EVENT_LOG, EVENT_LOG.ID.eq(id));
	}
	
	@Override
	public Long id() {
		return this.record.getId();
	}

	@Override
	public LocalDateTime date() {
		return this.record.getDate();
	}

	@Override
	public Level level() {
		return Level.parse(this.record.getLevelId());
	}

	@Override
	public String message() {
		return this.record.getMessage();
	}

	@Override
	public String details() {
		return this.record.getDetails();
	}

	@Override
	public String author() {
		return this.record.getAuthor();
	}

	@Override
	public String ipAddress() {
		return this.record.getIpAddress();
	}

}
