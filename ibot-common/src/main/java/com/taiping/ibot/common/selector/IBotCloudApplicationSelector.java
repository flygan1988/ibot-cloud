package com.taiping.ibot.common.selector;

import com.taiping.ibot.common.configure.IBotAuthExceptionConfigure;
import com.taiping.ibot.common.configure.IBotOAuth2FeignConfigure;
import com.taiping.ibot.common.configure.IBotServerProtectConfigure;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class IBotCloudApplicationSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{
                IBotAuthExceptionConfigure.class.getName(),
                IBotOAuth2FeignConfigure.class.getName(),
                IBotServerProtectConfigure.class.getName()
        };
    }
}
