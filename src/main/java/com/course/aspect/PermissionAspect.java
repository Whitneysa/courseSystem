package com.course.aspect;

import cn.hutool.core.collection.CollUtil;
import com.course.common.annotate.Permission;
import com.course.service.UserService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * @author YangHaixiong
 * @date 2023/12/23 14:34
 */

@Aspect
@Component
public class PermissionAspect {

    @Autowired
    private UserService userService;

    @Pointcut("@annotation(com.course.common.annotate.Permission)")
    public void permissionCheck() {}


    @Around("permissionCheck()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        Method method = methodSignature.getMethod();

        Permission permission = method.getAnnotation(Permission.class);
        List<String> roles = Arrays.asList(permission.value());

        // 注册接口直接通过
        if (CollUtil.isEmpty(roles)) {
            return pjp.proceed();
        }

        // 通过代码获取
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (Objects.isNull(servletRequestAttributes)) {
            throw new RuntimeException("系统异常");
        }

        HttpServletRequest request = servletRequestAttributes.getRequest();
        String token = request.getHeader("token");

        if (Objects.isNull(token)) {
            throw new RuntimeException("请在swagger的token中输入用户的id");
        }

        // 检查用户是否有操作权限
        if (userService.hasPermission(roles, token)) {
            return pjp.proceed();
        } else {
            throw new RuntimeException("用户无权限访问该接口");
        }
    }
}