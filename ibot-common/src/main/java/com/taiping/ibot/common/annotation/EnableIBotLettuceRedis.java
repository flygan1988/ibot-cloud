package com.taiping.ibot.common.annotation;

import com.taiping.ibot.common.configure.IBotLettuceRedisConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IBotLettuceRedisConfigure.class)
public @interface EnableIBotLettuceRedis {
}
