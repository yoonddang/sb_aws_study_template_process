package com.template.process;

import com.template.common.configuration.annotation.EnableDatasource;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.solr.SolrAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;

//@SpringBootApplication
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, SolrAutoConfiguration.class, RabbitAutoConfiguration.class})
@EnableCaching
@EnableDatasource
public class HnateoProcessApplication {

	public static void main(String[] args) {
		SpringApplication.run(HnateoProcessApplication.class, args);
	}
}
