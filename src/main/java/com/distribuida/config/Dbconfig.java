package com.distribuida.config;

import com.distribuida.servicios.BookRepository;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import javax.sql.DataSource;

@ApplicationScoped
public class Dbconfig {
    @Inject
    @ConfigProperty(name = "db.user")
    private String dbUser;

    @Inject
    @ConfigProperty(name = "db.password")
    private String dbPassword;

    @Inject
    @ConfigProperty(name = "db.url")
    private String dbUrl;

    //Pool de conexion
    @Produces
    @ApplicationScoped
    public DataSource dataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setDriverClassName("org.postgresql.Driver");
        ds.setJdbcUrl(dbUrl);
        ds.setUsername(dbUser);
        ds.setPassword(dbPassword);
        //ds.setMinimumIdle(1);
        //ds.setMaximumPoolSize(5);
        return ds;
    }
    @Produces
    @ApplicationScoped
    public Jdbi jdbi(DataSource dataSource) {
        Jdbi ret = Jdbi.create(dataSource);
        ret.installPlugin(new SqlObjectPlugin());
        return ret;
    }

   @Produces
    @ApplicationScoped
    public BookRepository bookRepository(Jdbi jdbi) {
        return jdbi.onDemand(BookRepository.class);
    }
}
