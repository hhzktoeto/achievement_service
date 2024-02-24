package faang.school.achievement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class AsyncEventHandlerConfig {

    @Bean
    public ExecutorService commentEventThreadPool(){
        return Executors.newCachedThreadPool();
    }
}