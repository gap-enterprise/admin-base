/*
 * Copyright (c) 2022 Surati
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to read
 * the Software only. Permissions is hereby NOT GRANTED to use, copy, modify,
 * merge, publish, distribute, sublicense, and/or sell copies of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package io.surati.gap.admin.base.db;

import com.lightweight.db.DataSourceWrap;
import com.lightweight.db.LiquibaseDataSource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Data source decorator that automatically build Admin module database with liquibase.
 *
 * @since 0.1
 */
public final class AdminDatabaseBuiltWithLiquibase extends DataSourceWrap {

    /**
     * Changelog master file name.
     */
    public static final String CHANGELOG_MASTER_FILENAME =
        "io/surati/gap/admin/base/liquibase/db.postgresql.changelog-master.xml";

    /**
     * Ctor.
     * @param src Data source
     */
    public AdminDatabaseBuiltWithLiquibase(final DataSource src) throws SQLException {
        super(
            new LiquibaseDataSource(
                src, AdminDatabaseBuiltWithLiquibase.CHANGELOG_MASTER_FILENAME
            )
        );
    }
}
