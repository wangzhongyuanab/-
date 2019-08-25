package com.ssm.controller;

import com.ssm.domain.SysLog;
import com.ssm.service.SysLogService;
import com.ssm.utils.DateUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;

@Component
@Aspect
public class LogAop {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private SysLogService sysLogService;

    private Date startTime; //开始时间
    private Class aClass; //访问的类
    private Method method; //访问的方法

   //前置通知  主要是获取开始时间，执行的类，执行的方法
    @Before("execution(* com.ssm.controller.*.*(..))")
    public void doBefore(JoinPoint joinPoint) throws NoSuchMethodException {
        startTime=new Date();   //当前时间就是开始时间
        aClass=joinPoint.getTarget().getClass(); //具体要访问的类
        String methodName=joinPoint.getSignature().getName();  //获取访问的方法名称
        Object[] args = joinPoint.getArgs();//获取访问的方法的参数

        //获取具体执行的Method对象
        if (args==null||args.length==0) {
            method = aClass.getMethod(methodName);  //只能获取无参数的方法
        }else{
            Class[] classArgs=new Class[args.length];
            for (int i=0;i<args.length;i++){
                classArgs[i]=args[i].getClass();
            }

            method=aClass.getMethod(methodName,classArgs);
        }
    }



    //后置通知
    @After("execution(* com.ssm.controller.*.*(..))")
    public void doAfter(JoinPoint joinPoint) throws Exception{
        //获取访问的时长
        long time=new Date().getTime()-startTime.getTime();

        String url="";
        //获取url
        if (aClass!=null&&method!=null&&aClass!=LogAop.class&&aClass!=SysLogController.class){
            //获取类上的注解
            RequestMapping classAnnotation = (RequestMapping)aClass.getAnnotation(RequestMapping.class);
            if (classAnnotation!=null){
                String[] classValue = classAnnotation.value();

                //获取方法上的@RequestMappingvalue值
                RequestMapping methodAnnotation = method.getAnnotation(RequestMapping.class);
                if (methodAnnotation!=null){
                    String[] methodValue = methodAnnotation.value();

                    url=classValue[0]+methodValue[0];

                    //获取访问的IP地址
                    String ip=request.getRemoteAddr();

                    //获取当前操作的用户
                    SecurityContext context= SecurityContextHolder.getContext();  //从上下文中获取当前登录的用户
                    User user =(User) context.getAuthentication().getPrincipal();
                    String username = user.getUsername();

                    //将日志相关信息封装到SysLog对象
                    SysLog sysLog=new SysLog();
                    sysLog.setExecutionTime(time);      //执行时间
                    sysLog.setIp(ip);
                    sysLog.setMethod("类名："+aClass.getName()+"方法名："+method.getName());
                    sysLog.setVisitTime(startTime);
                    sysLog.setUrl(url);
                    sysLog.setUsername(username);
                    sysLog.setVisitTimeStr(DateUtils.date2String(startTime,"yyyy-MM-dd HH:mm:ss"));

                    //调用service
                    sysLogService.save(sysLog);
                }
            }
        }
    }
}
