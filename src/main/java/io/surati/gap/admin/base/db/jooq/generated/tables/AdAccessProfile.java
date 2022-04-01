/*
 * This file is generated by jOOQ.
 */
package io.surati.gap.admin.base.db.jooq.generated.tables;


import io.surati.gap.admin.base.db.jooq.generated.Keys;
import io.surati.gap.admin.base.db.jooq.generated.Public;
import io.surati.gap.admin.base.db.jooq.generated.tables.records.AdAccessProfileRecord;

import java.util.Arrays;
import java.util.List;

import org.jooq.Field;
import org.jooq.ForeignKey;
import org.jooq.Name;
import org.jooq.Record;
import org.jooq.Row2;
import org.jooq.Schema;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableOptions;
import org.jooq.UniqueKey;
import org.jooq.impl.DSL;
import org.jooq.impl.SQLDataType;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class AdAccessProfile extends TableImpl<AdAccessProfileRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * The reference instance of <code>public.ad_access_profile</code>
     */
    public static final AdAccessProfile AD_ACCESS_PROFILE = new AdAccessProfile();

    /**
     * The class holding records for this type
     */
    @Override
    public Class<AdAccessProfileRecord> getRecordType() {
        return AdAccessProfileRecord.class;
    }

    /**
     * The column <code>public.ad_access_profile.access_id</code>.
     */
    public final TableField<AdAccessProfileRecord, String> ACCESS_ID = createField(DSL.name("access_id"), SQLDataType.VARCHAR(100).nullable(false), this, "");

    /**
     * The column <code>public.ad_access_profile.profile_id</code>.
     */
    public final TableField<AdAccessProfileRecord, Long> PROFILE_ID = createField(DSL.name("profile_id"), SQLDataType.BIGINT.nullable(false), this, "");

    private AdAccessProfile(Name alias, Table<AdAccessProfileRecord> aliased) {
        this(alias, aliased, null);
    }

    private AdAccessProfile(Name alias, Table<AdAccessProfileRecord> aliased, Field<?>[] parameters) {
        super(alias, null, aliased, parameters, DSL.comment(""), TableOptions.table());
    }

    /**
     * Create an aliased <code>public.ad_access_profile</code> table reference
     */
    public AdAccessProfile(String alias) {
        this(DSL.name(alias), AD_ACCESS_PROFILE);
    }

    /**
     * Create an aliased <code>public.ad_access_profile</code> table reference
     */
    public AdAccessProfile(Name alias) {
        this(alias, AD_ACCESS_PROFILE);
    }

    /**
     * Create a <code>public.ad_access_profile</code> table reference
     */
    public AdAccessProfile() {
        this(DSL.name("ad_access_profile"), null);
    }

    public <O extends Record> AdAccessProfile(Table<O> child, ForeignKey<O, AdAccessProfileRecord> key) {
        super(child, key, AD_ACCESS_PROFILE);
    }

    @Override
    public Schema getSchema() {
        return Public.PUBLIC;
    }

    @Override
    public UniqueKey<AdAccessProfileRecord> getPrimaryKey() {
        return Keys.AD_ACCESS_PROFILE_PKEY;
    }

    @Override
    public List<UniqueKey<AdAccessProfileRecord>> getKeys() {
        return Arrays.<UniqueKey<AdAccessProfileRecord>>asList(Keys.AD_ACCESS_PROFILE_PKEY);
    }

    @Override
    public List<ForeignKey<AdAccessProfileRecord, ?>> getReferences() {
        return Arrays.<ForeignKey<AdAccessProfileRecord, ?>>asList(Keys.AD_ACCESS_PROFILE__AD_ACCESS_PROFILE_PROFILE_ID_FKEY);
    }

    private transient AdProfile _adProfile;

    public AdProfile adProfile() {
        if (_adProfile == null)
            _adProfile = new AdProfile(this, Keys.AD_ACCESS_PROFILE__AD_ACCESS_PROFILE_PROFILE_ID_FKEY);

        return _adProfile;
    }

    @Override
    public AdAccessProfile as(String alias) {
        return new AdAccessProfile(DSL.name(alias), this);
    }

    @Override
    public AdAccessProfile as(Name alias) {
        return new AdAccessProfile(alias, this);
    }

    /**
     * Rename this table
     */
    @Override
    public AdAccessProfile rename(String name) {
        return new AdAccessProfile(DSL.name(name), null);
    }

    /**
     * Rename this table
     */
    @Override
    public AdAccessProfile rename(Name name) {
        return new AdAccessProfile(name, null);
    }

    // -------------------------------------------------------------------------
    // Row2 type methods
    // -------------------------------------------------------------------------

    @Override
    public Row2<String, Long> fieldsRow() {
        return (Row2) super.fieldsRow();
    }
}