package com.taiping.ibot.common.configure;

import com.taiping.ibot.common.handler.IBotAccessDeniedHandler;
import com.taiping.ibot.common.handler.IBotAuthExceptionEntryPoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

public class IBotAuthExceptionConfigure {
    @Bean
    @ConditionalOnMissingBean(name = "accessDeniedHandler")
    public IBotAccessDeniedHandler accessDeniedHandler() {
        return new IBotAccessDeniedHandler();
    }

    @Bean
    @ConditionalOnMissingBean(name = "authenticationEntryPoint")
    public IBotAuthExceptionEntryPoint authenticationEntryPoint() {
        return new IBotAuthExceptionEntryPoint();
    }
}
