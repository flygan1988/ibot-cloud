package com.taiping.ibot.server.system.configure;

import com.taiping.ibot.common.handler.IBotAccessDeniedHandler;
import com.taiping.ibot.common.handler.IBotAuthExceptionEntryPoint;
import com.taiping.ibot.server.system.properties.IBotServerSystemProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class IBotServerSystemResourceServerConfigure extends ResourceServerConfigurerAdapter {
    @Autowired
    private IBotAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private IBotAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private IBotServerSystemProperties properties;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), ",");

        http.csrf().disable()
                .requestMatchers().antMatchers("/**")
                .and()
                .authorizeRequests()
                .antMatchers(anonUrls).permitAll()
                .antMatchers("/**").authenticated();
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.authenticationEntryPoint(exceptionEntryPoint)
                .accessDeniedHandler(accessDeniedHandler);
    }
}
