package com.joy.xxfy.informationallyt.aop;

import com.joy.xxfy.informationallyt.config.JwtParamConfig;
import com.joy.xxfy.informationallyt.publish.exception.JoyException;
import com.joy.xxfy.informationallyt.publish.result.JoyResult;
import com.joy.xxfy.informationallyt.publish.result.Notice;
import com.joy.xxfy.informationallyt.publish.utils.LogUtil;
import com.joy.xxfy.informationallyt.publish.utils.jwt.JwtUtil;
import com.joy.xxfy.informationallyt.publish.utils.jwt.Token;
import io.jsonwebtoken.Claims;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登陆拦截、权限验证
 */
@Aspect
@Component
public class AuthorizeAspect {

    @Autowired
    private JwtParamConfig jwtParamConfig;

    @Pointcut("execution(public * com.joy.xxfy.informationallyt.module.*.web.*Controller.*(..))"
//            + "&&!execution(public * com.joy.xxfy.informationallyt.module.login.web.LoginController.*(..))"
//            + "&&!execution(public * com.joy.xxfy.informationallyt.module.login..web.UserController.*(..))"
//            + "&&!execution(public * com.joy.xxfy.informationallyt.module.system.web.HeartController.*(..))"
            // 开放文件下载权限
            + "&&!execution(public * com.joy.xxfy.informationallyt.module.*.web.*.download*(..))"
           )
    public void auth() {
    }

    @Before("auth()")
    public void doAuth() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        final Object authHeader = request.getHeader(Token.AUTHORIZATION.getName());
        if (authHeader == null) {
            LogUtil.info(Notice.USER_NOT_LOGIN.getMessage());
            throw new JoyException(JoyResult.buildFailedResult(Notice.USER_NOT_LOGIN));
        }
        final Claims claims = JwtUtil.parseJWT(authHeader.toString(), jwtParamConfig.getBase64Security());
        if (claims == null) {
            LogUtil.info(Notice.USER_NOT_LOGIN.getMessage());
            throw new JoyException(Notice.USER_NOT_LOGIN);
        }
        request.setAttribute(Token.CLAIMS.getName(), claims);
        response.setHeader(Token.AUTHORIZATION.getName(), JwtUtil.createJWT(claims, jwtParamConfig));
    }


}
