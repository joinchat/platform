package join.chat.mvp.platform.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
//    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
//    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    @Scheduled(fixedRate = 1000)
    public void scheduleTaskWithFixedRate() {
//        logger.info("Fixed Rate Task :: Execution Time - {}",
//                dateTimeFormatter.format(LocalDateTime.now()));
    }
}
