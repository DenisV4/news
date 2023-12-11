package com.example.news.aspect;

import com.example.news.component.AbstractMethodAccessInspector;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessAspect {
    private final List<AbstractMethodAccessInspector<?>> inspectors;

    @Around("@annotation(CreatorOnlyAccess)")
    public Object processRequest(ProceedingJoinPoint joinPoint) throws Throwable {
        var requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            throw new RuntimeException();
        }

        var request = requestAttributes.getRequest();
        var response = requestAttributes.getResponse();
        if (response == null) {
            throw new RuntimeException();
        }

        var pathParts = request.getServletPath().split("/");
        var pathVariable = pathParts[pathParts.length -1];
        var requestParameter = request.getParameter("creatorId");

        if (requestParameter == null) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return null;
        }

        long resourceId;
        long creatorId;
        try {
            resourceId = Long.parseLong(pathVariable);
            creatorId = Long.parseLong(requestParameter);
        } catch (NumberFormatException ignore) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return null;
        }

        var controllerClass = joinPoint.getSourceLocation().getWithinType();;
        var inspector = inspectors
                .stream()
                .filter(i -> i.getControllerClass().equals(controllerClass))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        if (!inspector.inspect(resourceId, creatorId)) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return null;
        }

        return joinPoint.proceed();
    }
}
