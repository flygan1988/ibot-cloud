package com.taiping.ibot.common.annotation;

import com.taiping.ibot.common.configure.IBotOAuth2FeignConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IBotOAuth2FeignConfigure.class)
public @interface EnableIBotOauth2FeignClient {
}
