package com.taiping.ibot.common.annotation;

import com.taiping.ibot.common.selector.IBotCloudApplicationSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(IBotCloudApplicationSelector.class)
public @interface IBotCloudApplication {
}
