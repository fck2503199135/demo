package com.example.demo.aspect;

import com.example.demo.common.CacheService;
import com.example.demo.exception.AuthorizeException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by luoanan
 */
@Aspect
@Component
@Slf4j
public class IshelfAuthorizeAspect {

    @Autowired
    private CacheService cacheService;

    @Value("${iknow.service.path}")
    private String loginUrl;

    /**
     * 刷新web端的token以及校验有效性
     */
    @Pointcut("execution(public * cn.manytag.ishelf.controller.*.*(..))" +
            "&& !execution(public * cn.manytag.ishelf.controller.CgibinController.*(..))" +
            "&& !execution(public * cn.manytag.ishelf.controller.H5Controller.*(..))" +
            "&& !execution(public * cn.manytag.ishelf.controller.BaseStationController.*(..))" +
            "&& !execution(public * cn.manytag.ishelf.controller.ExternalAPIEntranceController.*(..))"
    )
    public void refreshWebToken() {
    }

    @Before("refreshWebToken()")
    public void refreshWebToken(JoinPoint joinPoint) {
        // 请求开始时间
        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
        HttpServletRequest request = sra.getRequest();
        // 获取请求头
        Enumeration<String> enumeration = request.getHeaderNames();

        Map<String, String> headers = new HashMap();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            headers.put(name, value);
        }
//        log.info("header>{}", headers.toString());

        try {
            // 来自登陆页面的请求不做token校验
            String referer = headers.get("referer");
//            log.info("referer>{}", referer);
            if (!referer.contains(loginUrl)) {
                String tokenCache = cacheService.getTokenCache(headers.get("user"));
//                log.info("缓存中的token=>{}", tokenCache);
                if (!StringUtils.isBlank(tokenCache)) {
                    // 延长token时间
                    cacheService.tokenCache(headers.get("user"), headers.get("token"));
                } else {
                    log.error("header>{}", headers.toString());
                    log.error("referer>{}", referer);
                    log.error("缓存中不一致,tokenCache=>{}", tokenCache);
                    throw new AuthorizeException("缓存中查不到token信息");
                }
            }
        } catch (Exception e) {
            throw new AuthorizeException("缓存中查不到token信息");
        }
    }

    /**
     * h5/安卓端token有效性检验
     */
    @Pointcut("execution(public * cn.manytag.ishelf.controller.H5Controller.*(..))")
    public void h5Verify() {
    }

    @Before("h5Verify()")
    public void doH5Verify(JoinPoint joinPoint) {
        log.info("目标方法名为:", joinPoint.getSignature().getName());
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        ServletRequestAttributes attributes = (ServletRequestAttributes) requestAttributes;
        if (attributes != null) {
            Map<String, String> headsMap = new HashMap<>();
            HttpServletRequest request = attributes.getRequest();
            // 获取请求头
            Enumeration<String> enumeration = request.getHeaderNames();
            while (enumeration.hasMoreElements()) {
                String name = enumeration.nextElement();
                String value = request.getHeader(name);
                headsMap.put(name, value);
            }
            log.info("headsMap>{}", headsMap.toString());
        }
    }

}
