package com.zoe.snow.context.validator;

import com.zoe.snow.auth.AuthBean;
import com.zoe.snow.bean.BeanFactory;
import com.zoe.snow.context.request.Request;
import com.zoe.snow.context.response.RedirectTo;
import com.zoe.snow.context.session.Session;
import com.zoe.snow.message.Message;
import com.zoe.snow.model.support.user.UserHelper;
import com.zoe.snow.conf.CoreConfig;
import com.zoe.snow.log.Logger;
import com.zoe.snow.util.Validator;
import com.zoe.snow.validator.Checker;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * ValidatorInterceptor
 *
 * @author Dai Wenqing
 * @date 2016/3/2
 */
public class ValidatorInterceptor extends HandlerInterceptorAdapter {
    /**
     * 在业务处理器处理请求之前被调用 如果返回false 从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     * 如果返回true 执行下一个拦截器,直到所有的拦截器都执行完毕 再执行被拦截的Controller 然后进入拦截器链,
     * 从最后一个拦截器往回执行所有的postHandle() 接着再从最后一个拦截器往回执行所有的afterCompletion()
     */
    // @Value("${commons.ctrl.dispatcher.max:512}")
    protected int max;
    // protected ThreadLocal<Long> time = new ThreadLocal<>();
    protected AtomicInteger counter = new AtomicInteger();
    protected List<String> sessionList = new ArrayList<>();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (max == 0)
            max = Integer.valueOf(CoreConfig.getContextProperty("snow.request.count.max").toString());
        return preValidate(request, response, handler);
    }

    private Boolean preValidate(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        Session session = BeanFactory.getBean(Session.class);
        validateSession();
        boolean re = true;

        if (handler instanceof HandlerMethod) {
            AuthBean authBean = BeanFactory.getBean(AuthBean.class);
            //未找到开关 默认不开启权限验证
            if (authBean != null && authBean.getAuthSwitch()) {
                /*if (authBean.getAuthSwitch()) {
                    if (!authentication()) {
                        Response r = BeanFactory.getBean(Response.class);
                        RedirectTo redirectTo = BeanFactory.getBean(RedirectTo.class);
                        r.redirectTo(redirectTo.getUrl());
                        return false;
                    }
                }*/
                SecurityUtils.getSubject().isPermitted();
            }
            if (!sessionList.contains(session.getSessionId())) {
                sessionList.add(session.getSessionId());
            }

            if (counter.incrementAndGet() > max) {
                // counter.decrementAndGet();
                write(response, Message.Busy.getType());
                Logger.warn(null, "超过最大并发处理数[{}]，系统繁忙信息。", max);
                re = false;
            }

            //，未关闭的会话数据是[{}]
            // sessionList.size(),
            if (Logger.isDebugEnable()) {
                Logger.info("当前并发量是[{}]，请求的会话ID是[{}]，请求的URL是[{}]。", counter.get(), session.getSessionId(),
                        request.getRequestURI());
            }

            HandlerMethod hm = (HandlerMethod) handler;
            Validate validate = hm.getMethodAnnotation(Validate.class);
            if (!Validator.isEmpty(validate)) {
                for (Rule rule : validate.rules()) {
                    if (Validator.isEmpty(rule.name()))
                        break;
                    if (Validator.isEmpty(rule.message()))
                        break;
                    String failure = rule.message().getType();
                    Checker checker = BeanFactory.getBean(failure);
                    if (!Validator.isEmpty(checker)) {
                        Request r = BeanFactory.getBean(Request.class);
                        if (!checker.validate(r.get(rule.name()), rule.paramter())) {
                            String path = hm.getBeanType().getName();
                            String[] paths = path.split("\\.");
                            List<String> list = Arrays.asList(paths);
                            /*
                             * list.remove(0); list.remove(list.size() - 1);
                             */
                            if (list.size() > 3)
                                path = String.join(".", list.subList(2, list.size() - 1));
                            else if (list.size() > 2)
                                path = String.join(".", list.subList(2, list.size()));
                            else if (list.size() > 1)
                                path = String.join(".", list.subList(1, list.size()));
                            else
                                path = String.join(".", list);
                            String result = checker.failure(rule.message(), path + "." + rule.name()).toString();
                            write(response, result);
                            re = false;
                        }
                    } else {
                        String result = checker.failure(Message.CheckerNotFound, rule.name()).toString();
                        write(response, result);
                        re = false;
                    }
                }
            }
        }
        return re;
    }

    private boolean authentication() {
        Request request = BeanFactory.getBean(Request.class);
        AuthBean authBean = BeanFactory.getBean(AuthBean.class);
        RedirectTo redirectTo = BeanFactory.getBean(RedirectTo.class);
        //未找到开关 默认不开启权限验证
        if (authBean == null)
            return true;
        if (authBean.getAuthSwitch()) {

            //先验证JS、CSS
            /*String[] uris = request.getUri().split("\\?");
            if (uris.length > 0) {
                String uri = uris[0];

            } else */
            if (redirectTo.getExcludeUrlList() != null) {
                for (String c : redirectTo.getExcludeUrlList()) {
                    if (c.startsWith("*") && c.endsWith("*")) {
                        if (request.getUri().contains(c.substring(1, c.length() - 2)))
                            return true;
                    } else if (c.startsWith("*")) {
                        if (request.getUri().endsWith(c.substring(1)))
                            return true;
                    } else if (c.endsWith("*")) {
                        if (request.getUri().startsWith(c.substring(0, c.length() - 2)))
                            return true;
                    }
                }
            }
        }

        if (request.getUri().endsWith("login"))
            return true;
        UserHelper userHelper = BeanFactory.getBean(UserHelper.class);
        return userHelper.getUser() != null;
    }

    private void validateSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            if (!subject.isAuthenticated() && subject.isRemembered()) {
                //subject.getPrincipal().toString()
            }
        } catch (Exception e) {

        }
    }


    private void write(HttpServletResponse response, String result) throws IOException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        out.append(result);
        out.close();
    }

    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作 可在modelAndView中加入数据，比如当前时间
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // counter.decrementAndGet();
    }

    /**
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等
     * <p>
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion()
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        if (handler instanceof HandlerMethod) {
            counter.decrementAndGet();
        }

    }
}
