package utils.spring;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import utils.CalendarDay;

@Configuration
@ComponentScan({"repositories", "controller", "utils", "model", "view", "modelTest"})
public class AppConfig {
    @Bean
    public CalendarDay getCalendarDay() {
        return CalendarDay.getCurrentCalendarDay();
    }
}

