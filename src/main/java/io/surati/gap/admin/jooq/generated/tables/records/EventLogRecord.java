/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.admin.jooq.generated.tables.records;


import io.surati.gap.admin.jooq.generated.tables.EventLog;

import java.time.LocalDateTime;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record7;
import org.jooq.Row7;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class EventLogRecord extends UpdatableRecordImpl<EventLogRecord> implements Record7<Long, LocalDateTime, String, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.event_log.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.event_log.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.event_log.date</code>.
     */
    public void setDate(LocalDateTime value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.event_log.date</code>.
     */
    public LocalDateTime getDate() {
        return (LocalDateTime) get(1);
    }

    /**
     * Setter for <code>public.event_log.message</code>.
     */
    public void setMessage(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.event_log.message</code>.
     */
    public String getMessage() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.event_log.details</code>.
     */
    public void setDetails(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.event_log.details</code>.
     */
    public String getDetails() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.event_log.level_id</code>.
     */
    public void setLevelId(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.event_log.level_id</code>.
     */
    public String getLevelId() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.event_log.author</code>.
     */
    public void setAuthor(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.event_log.author</code>.
     */
    public String getAuthor() {
        return (String) get(5);
    }

    /**
     * Setter for <code>public.event_log.ip_address</code>.
     */
    public void setIpAddress(String value) {
        set(6, value);
    }

    /**
     * Getter for <code>public.event_log.ip_address</code>.
     */
    public String getIpAddress() {
        return (String) get(6);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record7 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row7<Long, LocalDateTime, String, String, String, String, String> fieldsRow() {
        return (Row7) super.fieldsRow();
    }

    @Override
    public Row7<Long, LocalDateTime, String, String, String, String, String> valuesRow() {
        return (Row7) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return EventLog.EVENT_LOG.ID;
    }

    @Override
    public Field<LocalDateTime> field2() {
        return EventLog.EVENT_LOG.DATE;
    }

    @Override
    public Field<String> field3() {
        return EventLog.EVENT_LOG.MESSAGE;
    }

    @Override
    public Field<String> field4() {
        return EventLog.EVENT_LOG.DETAILS;
    }

    @Override
    public Field<String> field5() {
        return EventLog.EVENT_LOG.LEVEL_ID;
    }

    @Override
    public Field<String> field6() {
        return EventLog.EVENT_LOG.AUTHOR;
    }

    @Override
    public Field<String> field7() {
        return EventLog.EVENT_LOG.IP_ADDRESS;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public LocalDateTime component2() {
        return getDate();
    }

    @Override
    public String component3() {
        return getMessage();
    }

    @Override
    public String component4() {
        return getDetails();
    }

    @Override
    public String component5() {
        return getLevelId();
    }

    @Override
    public String component6() {
        return getAuthor();
    }

    @Override
    public String component7() {
        return getIpAddress();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public LocalDateTime value2() {
        return getDate();
    }

    @Override
    public String value3() {
        return getMessage();
    }

    @Override
    public String value4() {
        return getDetails();
    }

    @Override
    public String value5() {
        return getLevelId();
    }

    @Override
    public String value6() {
        return getAuthor();
    }

    @Override
    public String value7() {
        return getIpAddress();
    }

    @Override
    public EventLogRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public EventLogRecord value2(LocalDateTime value) {
        setDate(value);
        return this;
    }

    @Override
    public EventLogRecord value3(String value) {
        setMessage(value);
        return this;
    }

    @Override
    public EventLogRecord value4(String value) {
        setDetails(value);
        return this;
    }

    @Override
    public EventLogRecord value5(String value) {
        setLevelId(value);
        return this;
    }

    @Override
    public EventLogRecord value6(String value) {
        setAuthor(value);
        return this;
    }

    @Override
    public EventLogRecord value7(String value) {
        setIpAddress(value);
        return this;
    }

    @Override
    public EventLogRecord values(Long value1, LocalDateTime value2, String value3, String value4, String value5, String value6, String value7) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        value7(value7);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached EventLogRecord
     */
    public EventLogRecord() {
        super(EventLog.EVENT_LOG);
    }

    /**
     * Create a detached, initialised EventLogRecord
     */
    public EventLogRecord(Long id, LocalDateTime date, String message, String details, String levelId, String author, String ipAddress) {
        super(EventLog.EVENT_LOG);

        setId(id);
        setDate(date);
        setMessage(message);
        setDetails(details);
        setLevelId(levelId);
        setAuthor(author);
        setIpAddress(ipAddress);
    }
}
