package com.karmanno.payments;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.karmanno.payments.module.MyBatisModule;
import com.karmanno.payments.module.DaoModule;
import com.karmanno.payments.service.PaymentProcessor;
import io.logz.guice.jersey.JerseyModule;
import io.logz.guice.jersey.JerseyServer;
import io.logz.guice.jersey.configuration.JerseyConfiguration;
import lombok.SneakyThrows;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

import static org.apache.ibatis.io.Resources.getResourceAsReader;

public class Application {
    private static final int PORT = 8080;
    private static final String RESOURCE_PACKAGES = "com.karmanno.payments.rest";
    private static final String EXCEPTION_PACKAGES = "com.karmanno.payments.exception";
    private static final String SCHEMA_SCRIPT = "db/database-schema.sql";
    private static final String DATA_SCRIPT = "db/database-data.sql";
    private static final String DROP_SCRIPT = "db/drop-all.sql";
    public static Injector injector;

    public static void main(String[] args) throws Exception {
        createJerseyServer(PORT, true).start();
    }

    public static JerseyServer createJerseyServer(int port, boolean withMigration) {
        JerseyConfiguration configuration = jerseyConfiguration(port);

        List<Module> modules = new ArrayList<>();
        modules.add(new JerseyModule(configuration));
        modules.add(new DaoModule());
        modules.add(new MyBatisModule());

        // Create DI container
        injector = Guice.createInjector(modules);
        // Custom migration
        if (withMigration)
            migrate(injector);
        // Start embedded jetty
        return injector.getInstance(JerseyServer.class);
    }

    private static JerseyConfiguration jerseyConfiguration(int port) {
        return  JerseyConfiguration.builder()
                .addPackage(RESOURCE_PACKAGES)
                .addPackage(EXCEPTION_PACKAGES)
                .addPort(port)
                .build();
    }

    @SneakyThrows
    private static void migrate(Injector injector) {
        Environment environment = injector.getInstance(SqlSessionFactory.class).getConfiguration().getEnvironment();
        DataSource dataSource = environment.getDataSource();
        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        runner.runScript(getResourceAsReader(SCHEMA_SCRIPT));
        runner.runScript(getResourceAsReader(DATA_SCRIPT));
        runner.closeConnection();
    }

    @SneakyThrows
    public static void dropAll(Injector injector) {
        Environment environment = injector.getInstance(SqlSessionFactory.class).getConfiguration().getEnvironment();
        DataSource dataSource = environment.getDataSource();
        ScriptRunner runner = new ScriptRunner(dataSource.getConnection());
        runner.setAutoCommit(true);
        runner.setStopOnError(true);
        runner.runScript(getResourceAsReader(DROP_SCRIPT));
        runner.closeConnection();
    }

}
