/**
 * 
 */
package com.hj.interceptor;

import com.hj.annotation.record;
import com.hj.po.User;
import com.hj.po.systemLog;
import com.hj.service.UserService;
import com.hj.service.systemLogService;
import com.hj.utils.RequestUtil;
import com.hj.utils.UserCookieUtil;
import com.hj.utils.common.Const;
import com.hj.utils.common.MD5;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author zh
 */

public class CommonInterceptor extends HandlerInterceptorAdapter {
	private final Logger log = LoggerFactory.getLogger(CommonInterceptor.class);
	@Resource
	private UserService userService;
	@Resource
	private systemLogService systemLogService;
	/*
	 * 利用正则映射到需要拦截的路径    
	 
    private String mappingURL;
    
    public void setMappingURL(String mappingURL) {    
               this.mappingURL = mappingURL;    
    }   
  */
    /** 
     * 在业务处理器处理请求之前被调用 
     * 如果返回false 
     *     从当前的拦截器往回执行所有拦截器的afterCompletion(),再退出拦截器链
     *     
     * 如果返回true 
     *    执行下一个拦截器,直到所有的拦截器都执行完毕 
     *    再执行被拦截的Controller 
     *    然后进入拦截器链, 
     *    从最后一个拦截器往回执行所有的postHandle() 
     *    接着再从最后一个拦截器往回执行所有的afterCompletion() 
     */  
    @Override  
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
    	
    	if ("GET".equalsIgnoreCase(request.getMethod())) {
    		RequestUtil.saveRequest();
        }
    	
        //log.info("==============执行拦截: preHandle================");  
		String requestUri = request.getRequestURI();
		String contextPath = request.getContextPath();
		String url = requestUri.substring(contextPath.length());
		
		//这里对拉入黑名单的ip进行处理【扩展】
		log.info("来自 >>>>>>"+RequestUtil.getIpAddr(request)+" 请求访问。");	
		
        User user =  (User)request.getSession().getAttribute(Const.SESSION_USER); 
        
        // 判断用户是否登录，一般是都没登录的
        if(user == null){
        	// 取cookie值，这里还有其他网站的
        	Cookie[] cookies = request.getCookies();
        	if(cookies!=null && cookies.length>0){
	  			  String cookieValue = null;
	  			  // 下面是找到本项目的cookie
	  			  for (int i = 0; i < cookies.length; i++) {
	  				  if(Const.COOKIEDOMAINNAME.equals(cookies[i].getName())){
	  					  cookieValue = cookies[i].getValue();
	  					  break;
	  				  }
	  			  }
	  			  // 如果cookieValue为空说明用户上次没有选择“记住下次登录”执行其他
	  			  if(cookieValue==null){
	  				  if (url.contains("login")){
	  					  return true;
	  				  }
	  				  request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
		  	          return false;
	  			  }else{
	  				  // 先得到的CookieValue进行Base64解码
	  				  //String cookieValueAfterDecode = new String(base64.decode(cookieValue),"UTF-8");
	  				  String cookieValueAfterDecode = new String(Base64.decodeBase64(cookieValue),"UTF-8");
	  				  // 对解码后的值进行分拆,得到一个数组,如果数组长度不为3,就是非法登陆
	  				  String cookieValues[] = cookieValueAfterDecode.split(":");
	  				  if(cookieValues.length!=3){ 
	  					  request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	  		        	  return false;
	  				  }
	  				  // 判断是否在有效期内,过期就删除Cookie
	  				  long validTimeInCookie = new Long(cookieValues[1]);
	  				  if (validTimeInCookie < System.currentTimeMillis()) {
	  					  // 删除Cookie
	  					  UserCookieUtil.clearCookie(response); 
	  					  request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
	  		        	  return false;
	  				  }
	  				  // 取出cookie中的用户名,并到数据库中检查这个用户名,
	  				  String userName = cookieValues[0];
	  				 User temp = userService.findUserByACCOUNT(userName);
	  				  // 如果user返回不为空,就取出密码,使用用户名(userName)+密码+有效时间+ webSiteKey进行MD5加密。与前面设置的进行比较，看是否是同一个用户
	  				  if(temp!=null){
	  					  String md5ValueInCookie = cookieValues[2];
	  					  String md5ValueFromUser = MD5.md5crypt(temp.getACCOUNT() + ":" + temp.getPASSWORD() + ":" + validTimeInCookie + ":" + Const.COOKIEDOMAINNAME);
	  					  // 将结果与Cookie中的MD5码相比较,如果相同,写入Session,自动登陆成功,并继续用户请求
	  					  if (md5ValueFromUser.equals(md5ValueInCookie)) {
	  						  request.getSession().setAttribute(Const.SESSION_USER, temp);
	  						  request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
	  			        	  return false;
	  					  }
	  				  }
  			      }
  		     }else{
  		    	if (url.contains("login")){
				    return true;
			    }
  		    	request.getRequestDispatcher("/WEB-INF/jsp/login.jsp").forward(request, response);
  	            return false;
  		    }
        }
        return true;   
    }  
  
    /**
     * 在业务处理器处理请求执行完成后,生成视图之前执行的动作   
     * 可在modelAndView中加入数据，比如当前时间
     */
    @Override  
    public void postHandle(HttpServletRequest request,
                           HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
//这边通过反射获取注解内容 进行日志的写入
		HandlerMethod hm = (HandlerMethod) handler;
		record re = hm.getMethodAnnotation(record.class);
		if (re != null){
			String actionType = re.actionType();
			String businessLogic = re.businessLogic();
			User user =  (User)request.getSession().getAttribute(Const.SESSION_USER);

			systemLog systemLog = new systemLog();
			systemLog.setActionType(actionType);
			systemLog.setBusinessLogic(businessLogic);
			systemLog.setUserId(user.getUID());
			systemLog.setUserName(user.getNAME());
			systemLogService.saveLog(systemLog);
		}
		super.postHandle(request,response,handler,modelAndView);
    }  
  
    /** 
     * 在DispatcherServlet完全处理完请求后被调用,可用于清理资源等  
     *  
     * 当有拦截器抛出异常时,会从当前拦截器往回执行所有的拦截器的afterCompletion() 
     */  
    @Override  
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }  

}  
