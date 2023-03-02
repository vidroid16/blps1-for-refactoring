package com.example.demo.Quartz;

import org.quartz.JobDetail;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;

/**
 * Класс конфигурация задач для планировщика Quartz
 * @autor Шаля Андрей
 * @version 2.0
 */
@Configuration
public class QuartzSubmitJobs {
    /** Строка задающая переодичность выполнения задачи раз в 5 минут. Формат cron*/
    private static final String CRON_EVERY_FIVE_MINUTES = "0 0/5 * ? * * *";

    /**
     * Функция регистрации задачи {@link MyJob} в плаанировщике
     */
    @Bean(name = "memberStats")
    public JobDetailFactoryBean jobMemberStats() {
        return QuartzConfig.createJobDetail(MyJob.class, "Testing Quartz");
    }

    /**
     * Функция регистрации триггера для задачи {@link MyJob}
     */
    @Bean(name = "memberStatsTrigger")
    public SimpleTriggerFactoryBean triggerMemberStats(@Qualifier("memberStats") JobDetail jobDetail) {
        return QuartzConfig.createTrigger(jobDetail, 3000000, "Doing Quartz Test");
    }


}