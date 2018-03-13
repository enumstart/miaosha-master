package com.miaosha.common.config;

import com.miaosha.service.MiaoShaUserService;
import com.miaosha.vo.MiaoShaUser;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by enum on 2018/3/6.
 */
@Service //注入到spring容器
public class UserAgermentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    private MiaoShaUserService userService;

    /**
     * 判断是否需要进行注入
     * @param methodParameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == MiaoShaUser.class;//参数是MiaoShaUser才做注入
    }

    /**
     * 需要注入的参数
     * @param methodParameter
     * @param modelAndViewContainer
     * @param nativeWebRequest
     * @param webDataBinderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        //得到request和response对象
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);
        String tokenParam = request.getParameter(MiaoShaUserService.MIAO_SHA_USER_TOKEN);
        String cookieToken = getCookieToken(request);
        String token = tokenParam != null ? tokenParam : cookieToken;
        if (StringUtils.isBlank(token)){
            return null;
        }
        return userService.getUserByToken(response, token);
    }

    /**
     * 得到cookie并取出特定值
     * @param request
     * @return
     */
    private String getCookieToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (null == cookies || cookies.length == 0) return null;
        for (Cookie cookie : cookies) {
            if (MiaoShaUserService.MIAO_SHA_USER_TOKEN.equals(cookie.getName())){
                return cookie.getValue();
            }
        }
        return null;
    }
}
