package com.hj.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.hj.po.Sysrole;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hj.po.SysMenu;
import com.hj.po.User;
import com.hj.po.easyui.Tree;
import com.hj.service.UserService;
import com.hj.utils.RequestUtil;
import com.hj.utils.UserCookieUtil;
import com.hj.utils.common.Const;
import org.springframework.web.servlet.ModelAndView;
import com.hj.utils.common.JsonTools;
/**
 * Created by hongjin on 2017/11/19.
 */
@Controller
public class SystemController extends BaseController {
    private final Logger log = LoggerFactory.getLogger(SystemController.class);
    @Resource
    private UserService userService;
    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String home(){
        log.info("返回首页");
        return "index";
    }
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String login(HttpServletRequest request,HttpServletResponse response,
                        @RequestParam(required = false) String loginname,@RequestParam(required = false) String password,
                        @RequestParam(required = false) String code,@RequestParam(required = false) String autologinch) throws Exception{
//        ModelAndView modelAndView = new ModelAndView();
        String returnStr = "login";
        if(loginname == null){
//            modelAndView.setViewName("login");
//            modelAndView.addObject("errInfo","");
//            return "login";
            return returnStr;
        }
        if (code.toLowerCase().equals(request.getSession().getAttribute("RANDOMCODE").toString().toLowerCase())){
            User user = userService.findUserByACCOUNT(loginname);
            if (user == null) {
                log.info("登陆用户名不存在");
//                request.getSession().setAttribute("message", "用户名不存在，请重新登录");
//                modelAndView.setViewName("login");
//                modelAndView.addObject("errInfo","登陆用户名不存在");
                request.getSession().setAttribute("message", "用户名不存在，请重新登录");
            }else{
                if (user.getPASSWORD().equals(password)){
                    if (autologinch != null && autologinch.equals("Y")){
                        //保存信息到cookie
                        UserCookieUtil.saveCookie(user,response);

                    }
                    request.getSession().setAttribute(Const.SESSION_USER,user);
//                    modelAndView.setViewName("redirect:/index");
//               returnStr = "redirect:index";
                    returnStr ="redirect:" + RequestUtil.retrieveSavedRequest();//跳转至访问页面
                }else {
//                    modelAndView.setViewName("login");
//                    modelAndView.addObject("errInfo","密码错误");
                    log.info("登陆密码错误");
                    request.getSession().setAttribute("message", "用户名密码错误，请重新登录");
                }
            }
        }else {
//            modelAndView.setViewName("login");
//            modelAndView.addObject("errInfo","验证码错误");
            request.getSession().setAttribute("message", "验证码错误，请重新输入");
        }
        return returnStr;
    }
    @RequestMapping(value = "/toLogin")
    public ModelAndView toLogin(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("errInfo","");
        return modelAndView;
    }
    @RequestMapping(value = "/logout")
    public String logout(HttpSession session,HttpServletResponse response){
        session.removeAttribute(Const.SESSION_USER);
        UserCookieUtil.clearCookie(response);
        return "redirect:/";
    }
    @ResponseBody
    @RequestMapping(value = "/getMenuByRole")
    public List getMenuByRole(Sysrole sysrole,HttpServletResponse response){
        List<Tree> treeList = new ArrayList<Tree>();
        List<SysMenu> menuList = userService.getMenuByRoleId(sysrole);
        if(sysrole.getId() == null || sysrole.getId() == 0){
            for (SysMenu sysMenu:menuList
                 ) {
                Tree node = new Tree();
                node.setId(sysMenu.getId());
                node.setText(sysMenu.getName());
//                node.setIconCls(sysMenu.getIconCls());
                if (sysMenu.getParentid() != 0){
                    node.setPid(sysMenu.getParentid());
                }
                if (sysMenu.getCountChildrens() > 0){
                    node.setState("closed");
                }

                treeList.add(node);
            }
        }else{
            List<SysMenu> allMenulist = userService.getMenuByRoleId(new Sysrole());
            for (SysMenu menu:allMenulist
                 ) {
                Tree node = new Tree();
                node.setId(menu.getId());
                node.setText(menu.getName());
//                node.setIconCls(menu.getIconCls());
                if (menu.getParentid() != 0){
                    node.setPid(menu.getParentid());

                    for (SysMenu menu2:menuList
                            ) {
                        if(menu.getId().equals(menu2.getId())){

                            node.setChecked(true);
//                        if(menu.getParentid().equals(0)) {
//                           node.setState("open");
//                        }
                            break;
                        }
                    }

                }
//                if (menu.getCountChildrens() > 0){
//                    node.setState("closed");
//                }



                treeList.add(node);
            }
        }
        return treeList;
//        this.write(response,JSONArray.toJSONString(treeList));
    }

    @ResponseBody
    @RequestMapping(value = "/getMenu")
    public List<Tree> getMenu(HttpSession session,HttpServletResponse response){
        response.setContentType("text/html;charset=UTF-8");

        User user = (User) session.getAttribute(Const.SESSION_USER);
        List<SysMenu> menuList = userService.getMenuByUserId(user.getUID());
        List<Tree> treeList = new ArrayList<Tree>();
        for (SysMenu menu:menuList
             ) {
            Tree node = new Tree();
            node.setId(menu.getId());

            node.setText(menu.getName());
            node.setIconCls(menu.getIconCls());
            if (menu.getParentid() != 0){
                node.setPid(menu.getParentid());
            }
            if (menu.getCountChildrens() > 0){
                node.setState("closed");
            }
            Map<String,Object> attr = new HashMap<String, Object>();
            attr.put("url",menu.getUrl());
            node.setAttributes(attr);
            treeList.add(node);
        }
//        String jsonStr = JsonTools.createJsonString(treeList);
        return treeList;
    }
}
