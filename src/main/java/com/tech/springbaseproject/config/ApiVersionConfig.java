package com.tech.springbaseproject.config;

import com.tech.springbaseproject.common.Version;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

@Configuration
public class ApiVersionConfig extends RequestMappingHandlerMapping {

    @Override
    protected org.springframework.web.servlet.mvc.method.RequestMappingInfo getMappingForMethod(
            Method method, Class<?> handlerType) {

        Version classLevel = handlerType.getAnnotation(Version.class);
        Version methodLevel = method.getAnnotation(Version.class);

        var info = super.getMappingForMethod(method, handlerType);
        if (info == null) return null;

        if (classLevel == null && methodLevel == null) {
            // no version annotation
            return info;
        }

        int version = (methodLevel != null) ? methodLevel.value() : classLevel.value();
        String prefix = "/v" + version;

        assert info.getPatternsCondition() != null;
        PatternsRequestCondition pattern = new PatternsRequestCondition(prefix)
                .combine(info.getPatternsCondition());

        return new org.springframework.web.servlet.mvc.method.RequestMappingInfo(
                pattern,
                info.getMethodsCondition(),
                info.getParamsCondition(),
                info.getHeadersCondition(),
                info.getConsumesCondition(),
                info.getProducesCondition(),
                info.getCustomCondition()
        );
    }
}
