package com.ppmall.controller.portal;
import com.ppmall.common.Const;
import com.ppmall.common.ResponseCode;
import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallUser;
import com.ppmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.controller.protal
 * 2019/1/16
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService iUserService;


    /**
     * 用户登录
     * @param username 用户帐号
     * @param password 用户密码
     * @param session Session 信息
     * @return User Object 对象
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PpmallUser> login(String username, String password, HttpSession session){
        ServerResponse<PpmallUser> response = iUserService.login(username, password);
        if(response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
    return response;
    }

    /**
     * 用户登出
     * @param session 接收 session
     * @return 登出成功提示信息
     */
    @RequestMapping(value = "logout.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> logout(HttpSession session){
        session.removeAttribute(Const.CURRENT_USER);
        return ServerResponse.createBySuccess();
    }

    /**
     * 用户注册
     * @param user PpmallUser对象
     * @return ServerResponse<String>
     */
    @RequestMapping(value = "register.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> register(PpmallUser user){
    return iUserService.register(user);
    }

    /**
     * 校验方法
     * @param string username or email
     * @param type type in Const class final element
     * @return ServerResponse Message
     */
    @RequestMapping(value = "check_valid.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> checkValid(String string,String type){
        return iUserService.checkValid(string,type);
    }

    /**
     * 获取用户信息
     * @param session session
     * @return ServerResponse<PpmallUser>
     */
    @RequestMapping(value = "get_user_info.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PpmallUser> getUserInfo(HttpSession session){
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if(user != null){
            return ServerResponse.createBySuccess(user);
        }
        return ServerResponse.createByERRORMessage("用户未登录，无法获取当前用户信息");
    }

    /**
     * 忘记密码 —— 密保问题查找
     * @param username 用户名
     * @return 问题 or 错误消息
     */
    @RequestMapping(value = "forget_get_question.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetGetQuestion(String username){
        return iUserService.selectQuestion(username);
    }

    /**
     * 校验密保问题
     * @param username 用户名
     * @param question 问题
     * @param answer 答案
     * @return ServerResponse Message
     */
    @RequestMapping(value = "forget_check_answer.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> forgetCheckAnswer(String username,String question,String answer){
        return iUserService.checkAnswer(username,question,answer);
    }


    /**
     * 重置用户密码
     * @param username 用户名
     * @param passwordNew 新密码
     * @param forgetToken Token
     * @return ServerResponse Message
     */
    @RequestMapping(value = "forget_reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public  ServerResponse<String> forgetResetPassword(String username,String passwordNew,String forgetToken){
       return iUserService.forgetResetPassword(username, passwordNew, forgetToken);
    }

    /**
     * 登录状态下重置密码
     * @param session session 信息
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @return ServerResponse Message
     */
    @RequestMapping(value = "reset_password.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<String> resetPassword(HttpSession session,String passwordOld,String passwordNew){
        PpmallUser user = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (user == null){
            return ServerResponse.createByERRORMessage("用户未登录!");
        }
        return iUserService.resetPassword(passwordOld,passwordNew,user);
    }

    /**
     * 更新用户信息
     * @param session session 信息
     * @param user 用户对象
     * @return ServerResponse Message
     */
    @RequestMapping(value = "update_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PpmallUser> updateInformation(HttpSession session,PpmallUser user){
        PpmallUser currentUser = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByERRORMessage("用户未登录!");
        }
        user.setId(currentUser.getId());
        user.setUsername(currentUser.getUsername());
        ServerResponse<PpmallUser> response = iUserService.updateInformation(user);
        if (response.isSuccess()){
            session.setAttribute(Const.CURRENT_USER,response.getData());
        }
        return response;
    }

    /**
     * 获取用户信息
     * @param session session 信息
     * @return ServerResponse Message
     */
    @RequestMapping(value = "get_information.do",method = RequestMethod.POST)
    @ResponseBody
    public ServerResponse<PpmallUser> getInformation(HttpSession session){
        PpmallUser currentUser = (PpmallUser) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null){
            return ServerResponse.createByERRORCodeMessage(ResponseCode.NEED_LOGIN.getCode(),"未登录！需要强制登录！");
        }
        return iUserService.getInformation(currentUser.getId());
    }


}