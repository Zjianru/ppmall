package com.ppmall.service;

import com.ppmall.common.ServerResponse;
import com.ppmall.pojo.PpmallUser;
import org.apache.ibatis.annotations.Param;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.service
 * 2019/1/16
 */
public interface IUserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return ServerResponse Message
     */
    ServerResponse<PpmallUser> login(@Param("username") String username, @Param("password") String password);

    /**
     * 用户注册
     *
     * @param user PpmallUser 用户对象
     * @return ServerResponse
     */
    ServerResponse register(@Param("user") PpmallUser user);

    /**
     * 校验方法
     *
     * @param string username or email
     * @param type   type in Const class final element
     * @return ServerResponse Message
     */
    ServerResponse<String> checkValid(String string, String type);

    /**
     * 校验找回密码的问题
     *
     * @param username 用户名
     * @return 问题 or 错误消息
     */
    ServerResponse selectQuestion(String username);

    /**
     * 校验密保问题
     *
     * @param username 用户名
     * @param question 问题
     * @param answer   答案
     * @return ServerResponse Message
     */
    ServerResponse<String> checkAnswer(String username, String question, String answer);

    /**
     * 重置用户密码
     *
     * @param username    用户名
     * @param passwordNew 新密码
     * @param forgetToken Token
     * @return ServerResponse Message
     */
    ServerResponse<String> forgetResetPassword(String username, String passwordNew, String forgetToken);

    /**
     * 登录状态下重置密码
     *
     * @param passwordOld 旧密码
     * @param passwordNew 新密码
     * @param user        用户对象
     * @return ServerResponse Message
     */
    ServerResponse<String> resetPassword(String passwordOld, String passwordNew, PpmallUser user);

    /**
     * 更新用户信息
     *
     * @param user 用户对象
     * @return ServerResponse Message
     */
    ServerResponse<PpmallUser> updateInformation(PpmallUser user);

    /**
     * 获取用户信息
     * @param userId 用户 ID
     * @return ServerResponse Message
     */
    ServerResponse<PpmallUser> getInformation(Integer userId);

    /**
     * 管理员权限校验
     * @param user 用户对象
     * @return ServerResponse Message
     */
    ServerResponse checkAdminRole(PpmallUser user);
}