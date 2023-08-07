package com.pezesha.moneytransfer;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableEncryptableProperties
@EnableAsync
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MoneyTransferApplication {

    public static void main(String[] args) {
        SpringApplication.run(MoneyTransferApplication.class, args);
    }
//	@Bean
//	public Executor asyncExecutor() {
//		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
//		executor.setCorePoolSize(2);
//		executor.setMaxPoolSize(2);
//		executor.setQueueCapacity(500);
//		executor.setThreadNamePrefix("M-TAsync-");
//		executor.initialize();
//		return executor;
//	}

}
