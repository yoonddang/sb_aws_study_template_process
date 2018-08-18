package com.template.process.configuration;


import com.template.common.mail.MailService;
import com.template.process.schedule.DataCollect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CollectConfig {

    @Autowired
    DataDAO DataDAO;
    @Autowired
    BatchLogDAO batchLogDAO;
    @Autowired
    MailService mailService;

    @Bean
    public DataCollect setJsoupCollect() {
        DataCollect dataCollect = new DataCollect(DataDAO, batchLogDAO, mailService);
        return dataCollect;
    }

}
