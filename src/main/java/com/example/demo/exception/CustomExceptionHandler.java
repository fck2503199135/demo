/*
package com.example.demo.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

*/
/**
 * <p>
 * 全局异常处理
 *//*

@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {

    */
/**
     * 自定义异常
     *//*

    @ExceptionHandler(value = Exception.class)
    public ModelAndView customException(Exception e) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        log.error("访问" + request.getRequestURI() + " 发生错误, 错误信息=>{}", e);
        ModelAndView mv = new ModelAndView(new MappingJackson2JsonView());
        Map<String, Object> map = new HashMap<>(16);

        if (e instanceof AuthorizeException) {
            map.put("status", 401);
            map.put("msg", "token不存在或校验失败!");
            mv.addAllObjects(map);
            return mv;
        } else if (e instanceof TemplateNotFoundException) {
            map.put("status", 500);
            map.put("msg", e.getMessage());
            mv.addAllObjects(map);
            return mv;
        } else {
            map.put("status", 500);
            map.put("msg", "操作失败！");
            map.put("reasion", e);
            mv.addAllObjects(map);
            return mv;
        }
    }

}*/
