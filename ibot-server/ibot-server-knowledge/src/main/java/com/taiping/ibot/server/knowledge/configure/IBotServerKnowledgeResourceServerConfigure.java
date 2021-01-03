package com.taiping.ibot.server.knowledge.configure;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.taiping.ibot.common.handler.IBotAccessDeniedHandler;
import com.taiping.ibot.common.handler.IBotAuthExceptionEntryPoint;
import com.taiping.ibot.server.knowledge.properties.IBotServerKnowledgeProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
@EnableResourceServer
public class IBotServerKnowledgeResourceServerConfigure extends ResourceServerConfigurerAdapter {
    @Autowired
    private IBotAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private IBotAuthExceptionEntryPoint exceptionEntryPoint;

    @Autowired
    private IBotServerKnowledgeProperties properties;
    @Override
    public void configure(HttpSecurity http) throws Exception {
        String[] anonUrls = StringUtils.splitByWholeSeparatorPreserveAllTokens(properties.getAnonUrl(), StringPool.COMMA);
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
