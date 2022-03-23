package io.surati.gap.admin.db;

import io.surati.gap.database.utils.extensions.AbstractDataSourceExtension;

final class DataSourceExtension extends AbstractDataSourceExtension {

    /**
     * Ctor.
     */
    public DataSourceExtension() {
        super(
            "io/surati/gap/admin/liquibase/db-admin.changelog-master.xml"
        );
    }
}
