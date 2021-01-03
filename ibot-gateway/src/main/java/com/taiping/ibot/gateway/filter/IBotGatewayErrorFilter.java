package com.taiping.ibot.gateway.filter;

import com.netflix.zuul.context.RequestContext;
import com.taiping.ibot.common.entity.IBotResponse;
import com.taiping.ibot.common.utils.IBotUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class IBotGatewayErrorFilter extends SendErrorFilter {
    @Override
    public Object run() {
        try {
            IBotResponse iBotResponse = new IBotResponse();
            RequestContext ctx = RequestContext.getCurrentContext();
            String serviceId = (String) ctx.get(FilterConstants.SERVICE_ID_KEY);

            ExceptionHolder exception = findZuulException(ctx.getThrowable());
            String errorCause = exception.getErrorCause();
            Throwable throwable = exception.getThrowable();
            String message = throwable.getMessage();
            message = StringUtils.isBlank(message) ? errorCause : message;
            iBotResponse = resolveExceptionMessage(message, serviceId, iBotResponse);

            HttpServletResponse response = ctx.getResponse();
            IBotUtil.makeResponse(
                    response, MediaType.APPLICATION_JSON_UTF8_VALUE,
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR, iBotResponse
            );
            log.error("Zull sendError：{}", iBotResponse.getMessage());
        } catch (Exception ex) {
            log.error("Zuul sendError", ex);
            ReflectionUtils.rethrowRuntimeException(ex);
        }
        return null;
    }

    private IBotResponse resolveExceptionMessage(String message, String serviceId, IBotResponse iBotResponse) {
        if (StringUtils.containsIgnoreCase(message, "time out")) {
            return iBotResponse.message("请求" + serviceId + "服务超时");
        }
        if (StringUtils.containsIgnoreCase(message, "forwarding error")) {
            return iBotResponse.message(serviceId + "服务不可用");
        }
        return iBotResponse.message("Zuul请求" + serviceId + "服务异常");
    }
}
