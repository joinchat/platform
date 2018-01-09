package join.chat.mvp.platform.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "join.chat.configuration")
public class ApplicationConfiguration {
    private int asyncPoolSize;
    private int schedulerPoolSize;

    public int getAsyncPoolSize() {
        return asyncPoolSize;
    }

    public void setAsyncPoolSize(int asyncPoolSize) {
        this.asyncPoolSize = asyncPoolSize;
    }

    public int getSchedulerPoolSize() {
        return schedulerPoolSize;
    }

    public void setSchedulerPoolSize(int schedulerPoolSize) {
        this.schedulerPoolSize = schedulerPoolSize;
    }
}
