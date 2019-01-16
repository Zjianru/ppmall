package com.ppmall.service.impl;

import com.ppmall.common.Const;
import com.ppmall.common.ServerResponse;
import com.ppmall.common.TokenCache;
import com.ppmall.dao.PpmallUserMapper;
import com.ppmall.pojo.PpmallUser;
import com.ppmall.service.IUserService;
import com.ppmall.util.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service.impl
 * 2019/1/16
 */
@Service("iUserService")
public class IUserServiceImpl implements IUserService {


    @Autowired
    private PpmallUserMapper Ppmallusermapper;

    /**
     * 用户登录
     * @param userName 账户名
     * @param password 密码
     * @return 状态信息
     */
    @Override
    public ServerResponse<PpmallUser> login(String userName, String password) {
        int resultCount = Ppmallusermapper.checkUsername(userName);
        if (resultCount == 0) {
            return ServerResponse.createByERRORMessage("用户名不存在");
        }
        String md5password = MD5Util.md5encodeutf8(password);
        PpmallUser user = Ppmallusermapper.selectUserLogin(userName, md5password);
        if(user ==null){
            return ServerResponse.createByERRORMessage("密码错误");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess("登录成功",user);
    }

    /**
     * 用户注册
     * @param user PpmallUser 用户对象
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse<String> register(PpmallUser user){
        // 校验用户名
        ServerResponse validResponse = this.checkValid(user.getUsername(), Const.USERNAME);
        if(!validResponse.isSuccess()){
            return validResponse;
        }
        // 校验 email
        validResponse = this.checkValid(user.getEmail(),Const.EMAIL);
        if(!validResponse.isSuccess()){
            return validResponse;
        }

        user.setRole(Const.Role.ROLE_CUSTOMER);
        // MD5 加密
        user.setPassword(MD5Util.md5encodeutf8(user.getPassword()));
        int resultCount = Ppmallusermapper.insert(user);
        if(resultCount == 0 ){
            return ServerResponse.createByERRORMessage("注册失败");
        }
        return ServerResponse.createBySuccessMessage("注册成功");
    }
    /**
     * 校验方法
     * @param string username or email
     * @param type type in Const class final element
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse<String> checkValid(String string, String type) {
        if (StringUtils.isNotBlank(type)){
            // 校验用户名是否存在
            if(Const.USERNAME.equals(type)){
                int resultCount = Ppmallusermapper.checkUsername(string);
                if (resultCount > 0) {
                    return ServerResponse.createByERRORMessage("用户名已存在");
                }
            }
            // 校验 email 是否存在
            if (Const.EMAIL.equals(type)){
                int resultCount = Ppmallusermapper.checkUserEmail(string);
                if (resultCount > 0) {
                    return ServerResponse.createByERRORMessage("email已存在");
                }
            }
        }else {
            return ServerResponse.createByERRORMessage("参数错误");
        }
        return ServerResponse.createBySuccessMessage("校验成功");
    }
    /**
     * 校验找回密码的问题
     * @param username 用户名
     * @return 问题 or 错误消息
     */
    @Override
    public ServerResponse selectQuestion(String username){
        ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
        if (validResponse.isSuccess()){
            // 用户不存在
            return ServerResponse.createByERRORMessage("用户不存在");
        }
        String question = Ppmallusermapper.selectQuestionByUsername(username);
        if(StringUtils.isNotBlank(question)){
            return ServerResponse.createBySuccess(question);
        }
        return ServerResponse.createByERRORMessage("找回密码问题为空！");
    }

    /**
     * 校验密保问题
     * @param username 用户名
     * @param question 问题
     * @param answer 答案
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse<String> checkAnswer(String username, String question, String answer){
        int resultCount = Ppmallusermapper.checkAnswer(username,question,answer);
        if(resultCount > 0){
            // 问题和问题答案是这个用户设置 并且回答正确
            String forgetToken = UUID.randomUUID().toString();
            TokenCache.setKey(TokenCache.TOKEN_PREFIX+username,forgetToken);
            return ServerResponse.createBySuccess(forgetToken);
        }
        return ServerResponse.createByERRORMessage("问题答案错误");
    }

    /**
     * 重置用户密码
     * @param username 用户名
     * @param passwordNew 新密码
     * @param forgetToken Token
     * @return ServerResponse Message
     */
    @Override
public ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken){
        if (StringUtils.isBlank(forgetToken)){
            return ServerResponse.createByERRORMessage("参数错误，Token 未传递");
        }
    ServerResponse validResponse = this.checkValid(username,Const.USERNAME);
    if (validResponse.isSuccess()){
        // 用户不存在
        return ServerResponse.createByERRORMessage("用户不存在");
    }
    String token = TokenCache.getKey(TokenCache.TOKEN_PREFIX+username);
    if(StringUtils.isBlank(token)) {
        return ServerResponse.createByERRORMessage("Token 已无效！");
    }
    if(StringUtils.equals(forgetToken,token)){
        String md5Password = MD5Util.md5encodeutf8(passwordNew);
        int rowCount = Ppmallusermapper.updatePasswordByUsername(username, md5Password);
        if (rowCount > 0){
            return ServerResponse.createBySuccessMessage("密码重置成功");
        }
    }else{
        return ServerResponse.createByERRORMessage("Token错误！ ，请重新开始重置密码");
    }
    return ServerResponse.createByERRORMessage("重置密码失败！");
    }

    /**
     * 登录状态下重置密码
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @param user 用户对象
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse<String> resetPassword(String passwordOld, String passwordNew, PpmallUser user){
        // 防止横向越权，作用户校验
        int resultCount = Ppmallusermapper.checkPasswordByUserId(MD5Util.md5encodeutf8(passwordOld),user.getId());
        if (resultCount == 0){
            return ServerResponse.createByERRORMessage("旧密码错误！");
        }
        user.setPassword(MD5Util.md5encodeutf8(passwordNew));
        int updateCount = Ppmallusermapper.updateByPrimaryKeySelective(user);
        if (updateCount > 0){
            return ServerResponse.createBySuccessMessage("密码更新成功");
        }
        return ServerResponse.createByERRORMessage("密码更新失败！");
    }
    /**
     * 更新用户信息
     * @param user 用户对象
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse<PpmallUser> updateInformation(PpmallUser user){
        // username 不能被更新
        // email 更新限制：是否存在，存在则不能是当前用户
        int resultCount = Ppmallusermapper.checkEmailByUserId(user.getEmail(),user.getId());
        if(resultCount > 0){
            return ServerResponse.createByERRORMessage("邮箱已被占用，请更换后重试");
        }
        PpmallUser updateUser = new PpmallUser();
        updateUser.setUsername(user.getUsername());
        updateUser.setId(user.getId());
        updateUser.setEmail(user.getEmail());
        updateUser.setPhone(user.getPhone());
        updateUser.setQuestion(user.getQuestion());
        updateUser.setAnswer(user.getAnswer());
        int updateCount = Ppmallusermapper.updateByPrimaryKeySelective(updateUser);
        if (updateCount > 0){
             return ServerResponse.createBySuccess("更新个人信息成功",updateUser);
        }
        return ServerResponse.createByERRORMessage("更新个人信息失败！");
    }

    /**
     * 获取用户信息
     * @param userId 用户 ID
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse<PpmallUser> getInformation(Integer userId){
        PpmallUser user  = Ppmallusermapper.selectByPrimaryKey(userId);
        if (user == null){
            return ServerResponse.createByERRORMessage("找不到当前用户");
        }
        user.setPassword(StringUtils.EMPTY);
        return ServerResponse.createBySuccess(user);
    }






    // BACKEND


    /**
     * 管理员权限校验
     * @param user 用户对象
     * @return ServerResponse Message
     */
    @Override
    public ServerResponse checkAdminRole(PpmallUser user){
        if (user != null && user.getRole() == Const.Role.ROLE_ADMIN){
            return ServerResponse.createBySuccess();
        }
        return ServerResponse.createByError();
    }
}
