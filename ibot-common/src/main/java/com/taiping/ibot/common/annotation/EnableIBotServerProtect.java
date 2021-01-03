package com.taiping.ibot.common.annotation;

import com.taiping.ibot.common.configure.IBotServerProtectConfigure;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IBotServerProtectConfigure.class)
public @interface EnableIBotServerProtect {
}
