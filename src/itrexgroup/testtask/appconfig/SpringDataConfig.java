package itrexgroup.testtask.appconfig;

import java.sql.SQLException;
import java.util.Properties;
import javax.annotation.Resource;
import javax.sql.DataSource;
import org.h2.tools.Server;
import org.hibernate.ejb.HibernatePersistence;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "itrexgroup.testtask.repositories" })
public class SpringDataConfig {

	@Resource
	private Environment env;

	// @Bean
	// public DataSource dataSource() {
	// String args[] = { "-tcpAllowOthers", "-tcpPort", "9092", "-baseDir",
	// "./test" };
	//
	// try {
	// Server server = Server.createTcpServer(args).start();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// DriverManagerDataSource dataSource = new DriverManagerDataSource();
	// dataSource.setDriverClassName("org.h2.Driver");
	// dataSource.setUrl("jdbc:h2:tcp://localhost:9092/test");
	//
	// dataSource.setUsername("sa");
	// dataSource.setPassword("sa");
	// return dataSource;
	//
	// }

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		dataSource
				.setUrl("jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8");

		dataSource.setUsername("root");
		dataSource.setPassword("azazsxa");
		return dataSource;
	}

	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
		LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
		entityManagerFactoryBean.setDataSource(dataSource());
		entityManagerFactoryBean
				.setPersistenceProviderClass(HibernatePersistence.class);
		entityManagerFactoryBean
				.setPackagesToScan("itrexgroup.testtask.entities");
		entityManagerFactoryBean
				.setJpaProperties(getMysqlHibernateProperties());
		return entityManagerFactoryBean;
	}

	@Bean
	public PlatformTransactionManager transactionManager() {

		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
		return txManager;
	}

	// Две БД на выбор (параметры - вставить нужный метод выше)

	private Properties getH2HibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
		properties.put("show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");
		// "create-drop" или "update" для сохранения между рестартами сервера);
		return properties;
	}

	private Properties getMysqlHibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect",
				"org.hibernate.dialect.MySQL5Dialect");
		properties.put("show_sql", "true");
		properties.put("hibernate.hbm2ddl.auto", "create-drop");

		// "create-drop" или "update" для сохранения между рестартами сервера);
		return properties;
	}
}
