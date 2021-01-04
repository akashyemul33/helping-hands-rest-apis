package com.ayprojects.helpinghands.util.tools;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
public class BeanForCalendarOperations {

    @Bean
    public CalendarOperations calendarOperations(){
        return new CalendarOperations();
    }

}
