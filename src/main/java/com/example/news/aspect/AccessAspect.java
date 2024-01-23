package com.example.news.aspect;

import com.example.news.component.AbstractMethodAccessInspector;
import com.example.news.model.Role;
import com.example.news.security.AppUserDetails;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;

@Aspect
@Component
@RequiredArgsConstructor
public class AccessAspect {
    private final List<AbstractMethodAccessInspector<?>> inspectors;

    @Around("@annotation(com.example.news.aspect.CreatorOnlyAccess) && args(..,userDetails)")
    public Object allowCreatorOnlyAccess(ProceedingJoinPoint joinPoint, UserDetails userDetails) throws Throwable {
        return processRequest(joinPoint, (AppUserDetails) userDetails);
    }

    @Around("@annotation(com.example.news.aspect.UserRoleRestriction) && args(..,userDetails)")
    public Object restrictUserRoleAccess(ProceedingJoinPoint joinPoint, UserDetails userDetails) throws Throwable {
        var authorities = new ArrayList<>(userDetails.getAuthorities());
        var hasOnlyUserRole = authorities.size() == 1 &&
                Role.RoleType.ROLE_USER.name().equals(authorities.get(0).getAuthority());

        if (!hasOnlyUserRole) {
            return joinPoint.proceed();
        }

        return processRequest(joinPoint, (AppUserDetails) userDetails);
    }

    private Object processRequest(ProceedingJoinPoint joinPoint, AppUserDetails userDetails) throws Throwable {
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

        long resourceId;
        try {
            resourceId = Long.parseLong(pathVariable);
        } catch (NumberFormatException ignore) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return null;
        }

        var userId = userDetails.getId();

        var controllerClass = joinPoint.getSourceLocation().getWithinType();
        var inspector = inspectors
                .stream()
                .filter(i -> i.getControllerClass().equals(controllerClass))
                .findFirst()
                .orElseThrow(RuntimeException::new);

        if (!inspector.inspect(resourceId, userId)) {
            response.sendError(HttpStatus.FORBIDDEN.value());
            return null;
        }

        return joinPoint.proceed();
    }
}
