package io.surati.gap.admin.base.db;

import com.lightweight.db.LiquibaseDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.hamcrest.MatcherAssert;
import org.hamcrest.core.IsEqual;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.testcontainers.containers.OracleContainer;

/**
 * IT test for Oracle compatibility.
 *
 * @since 0.4
 */
public final class OracleCompatibilityITCase {

    /**
     * Oracle container.
     */
    @ClassRule
    public static OracleContainer oracle = new OracleContainer("gvenzl/oracle-xe:11.2.0.2-slim")
        .withEnv("ORACLE_PWD", "Str0ngPassw0rd")
        .withStartupTimeoutSeconds(900)
        .withConnectTimeoutSeconds(900)
        .withPassword("Str0ngPassw0rd");

    /**
     * Datat source.
     */
    private DataSource src;

    @BeforeClass
    public static void startup() {
        System.setProperty("oracle.jdbc.fanEnabled", "false");
        OracleCompatibilityITCase.oracle.start();
    }

    @Before
    public void setUp() {
        final HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(OracleCompatibilityITCase.oracle.getJdbcUrl());
        hikariConfig.setUsername(OracleCompatibilityITCase.oracle.getUsername());
        hikariConfig.setPassword(OracleCompatibilityITCase.oracle.getPassword());
        hikariConfig.setDriverClassName(OracleCompatibilityITCase.oracle.getDriverClassName());
        this.src = new HikariDataSource(hikariConfig);
    }

    @Test
    public void buildsDatabase() throws SQLException {
        MatcherAssert.assertThat(
            new DbUser(
                new LiquibaseDataSource(
                    this.src,
                    "io/surati/gap/admin/base/liquibase/db.oracle.changelog-master.xml"
                ),
                1L
            ).login(),
            new IsEqual<>("admin")
        );
    }
}
