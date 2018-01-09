package join.chat.mvp.platform.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfiguration {
    private final ApplicationConfiguration configuration;

    @Autowired
    public AsyncConfiguration(ApplicationConfiguration configuration) {
        this.configuration = configuration;
    }

    @Bean
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(configuration.getAsyncPoolSize());
        executor.setMaxPoolSize(configuration.getAsyncPoolSize());
        executor.setQueueCapacity(500);
        executor.setThreadNamePrefix("jc-async-task-pool-");
        executor.initialize();
        return executor;
    }
}
