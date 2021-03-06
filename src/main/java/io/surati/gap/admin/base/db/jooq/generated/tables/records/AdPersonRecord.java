/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.admin.base.db.jooq.generated.tables.records;


import io.surati.gap.admin.base.db.jooq.generated.tables.AdPerson;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record6;
import org.jooq.Row6;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AdPersonRecord extends UpdatableRecordImpl<AdPersonRecord> implements Record6<Long, String, String, String, String, String> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.ad_person.id</code>.
     */
    public void setId(Long value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.ad_person.id</code>.
     */
    public Long getId() {
        return (Long) get(0);
    }

    /**
     * Setter for <code>public.ad_person.name</code>.
     */
    public void setName(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.ad_person.name</code>.
     */
    public String getName() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.ad_person.address</code>.
     */
    public void setAddress(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.ad_person.address</code>.
     */
    public String getAddress() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.ad_person.pobox</code>.
     */
    public void setPobox(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.ad_person.pobox</code>.
     */
    public String getPobox() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.ad_person.phone</code>.
     */
    public void setPhone(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.ad_person.phone</code>.
     */
    public String getPhone() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.ad_person.email</code>.
     */
    public void setEmail(String value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.ad_person.email</code>.
     */
    public String getEmail() {
        return (String) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Long> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Record6 type implementation
    // -------------------------------------------------------------------------

    @Override
    public Row6<Long, String, String, String, String, String> fieldsRow() {
        return (Row6) super.fieldsRow();
    }

    @Override
    public Row6<Long, String, String, String, String, String> valuesRow() {
        return (Row6) super.valuesRow();
    }

    @Override
    public Field<Long> field1() {
        return AdPerson.AD_PERSON.ID;
    }

    @Override
    public Field<String> field2() {
        return AdPerson.AD_PERSON.NAME;
    }

    @Override
    public Field<String> field3() {
        return AdPerson.AD_PERSON.ADDRESS;
    }

    @Override
    public Field<String> field4() {
        return AdPerson.AD_PERSON.POBOX;
    }

    @Override
    public Field<String> field5() {
        return AdPerson.AD_PERSON.PHONE;
    }

    @Override
    public Field<String> field6() {
        return AdPerson.AD_PERSON.EMAIL;
    }

    @Override
    public Long component1() {
        return getId();
    }

    @Override
    public String component2() {
        return getName();
    }

    @Override
    public String component3() {
        return getAddress();
    }

    @Override
    public String component4() {
        return getPobox();
    }

    @Override
    public String component5() {
        return getPhone();
    }

    @Override
    public String component6() {
        return getEmail();
    }

    @Override
    public Long value1() {
        return getId();
    }

    @Override
    public String value2() {
        return getName();
    }

    @Override
    public String value3() {
        return getAddress();
    }

    @Override
    public String value4() {
        return getPobox();
    }

    @Override
    public String value5() {
        return getPhone();
    }

    @Override
    public String value6() {
        return getEmail();
    }

    @Override
    public AdPersonRecord value1(Long value) {
        setId(value);
        return this;
    }

    @Override
    public AdPersonRecord value2(String value) {
        setName(value);
        return this;
    }

    @Override
    public AdPersonRecord value3(String value) {
        setAddress(value);
        return this;
    }

    @Override
    public AdPersonRecord value4(String value) {
        setPobox(value);
        return this;
    }

    @Override
    public AdPersonRecord value5(String value) {
        setPhone(value);
        return this;
    }

    @Override
    public AdPersonRecord value6(String value) {
        setEmail(value);
        return this;
    }

    @Override
    public AdPersonRecord values(Long value1, String value2, String value3, String value4, String value5, String value6) {
        value1(value1);
        value2(value2);
        value3(value3);
        value4(value4);
        value5(value5);
        value6(value6);
        return this;
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AdPersonRecord
     */
    public AdPersonRecord() {
        super(AdPerson.AD_PERSON);
    }

    /**
     * Create a detached, initialised AdPersonRecord
     */
    public AdPersonRecord(Long id, String name, String address, String pobox, String phone, String email) {
        super(AdPerson.AD_PERSON);

        setId(id);
        setName(name);
        setAddress(address);
        setPobox(pobox);
        setPhone(phone);
        setEmail(email);
    }
}
