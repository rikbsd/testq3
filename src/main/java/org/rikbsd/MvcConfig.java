package org.rikbsd;

import org.hibernate.SessionFactory;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.sql.DataSource;
import java.sql.Driver;
import java.util.Properties;

@Configuration
@ComponentScan({"org.rikbsd.*"})
@EnableTransactionManagement
@EnableWebMvc
public class MvcConfig extends WebMvcConfigurerAdapter
{
	@Override
	public void addViewControllers(ViewControllerRegistry registry)
	{
		registry.addViewController("/").setViewName("hello");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
	}

	@Bean
	public SessionFactory sessionFactory() {
		LocalSessionFactoryBuilder builder =
				new LocalSessionFactoryBuilder(dataSource());
		builder.scanPackages("org.rikbsd.model")
				.addProperties(getHibernateProperties());

		return builder.buildSessionFactory();
	}

	private Properties getHibernateProperties() {
		Properties prop = new Properties();
		prop.put("hibernate.format_sql", "true");
		prop.put("hibernate.show_sql", "true");
		prop.put("hibernate.dialect",
				"com.enigmabridge.hibernate.dialect.SQLiteDialect");
		return prop;
	}

	@Bean(name = "dataSource")
	public DataSource dataSource()
	{
		SimpleDriverDataSource ds = new SimpleDriverDataSource();
		try {
			ds.setDriverClass((Class<Driver>) Class.forName("org.sqlite.JDBC"));
		} catch(ClassNotFoundException ex) {
			return null;
		}
		ds.setUrl("jdbc:sqlite:test_db.sqlite");
		ds.setUsername("");
		return ds;
	}

	//Create a transaction manager
	@Bean
	public HibernateTransactionManager txManager() {
		return new HibernateTransactionManager(sessionFactory());
	}
}