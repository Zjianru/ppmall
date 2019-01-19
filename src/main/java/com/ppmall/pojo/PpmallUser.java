package com.ppmall.pojo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.pojo
 * 2019/1/15
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PpmallUser implements Serializable {

    /** 用户表id*/
    private Integer id;

    /** 用户名*/
    private String username;

    /** 用户密码，MD5加密*/
    private String password;

    /** 用户邮箱 */
    private String email;

    /** 用户手机 */
    private String phone;

    /** 找回密码问题*/
    private String question;

    /** 找回密码答案*/
    private String answer;

    /** 角色0-管理员,1-普通用户*/
    private Integer role;

    /** 创建时间*/
    private Date createTime;

    /** 最后一次更新时间*/
    private Date updateTime;

}