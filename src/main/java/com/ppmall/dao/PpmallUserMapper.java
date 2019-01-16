package com.ppmall.dao;
import com.ppmall.pojo.PpmallUser;
import org.apache.ibatis.annotations.Param;
/**
 * Created by IntelliJ IDEA
 *
 * @author Zjianru
 * @version 1.0
 * com.ppmall.dao
 * 2019/1/15
 */
public interface PpmallUserMapper {
    /**
     *根据主键删除
     * @param id id
     * @return 影响行数
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insert(PpmallUser record);

    /**
     * 选择性插入
     * @param record 待插入对象
     * @return 影响行数
     */
    int insertSelective(PpmallUser record);

    /**
     * 根据主键查找
     * @param id id
     * @return PpmallUser
     */
    PpmallUser selectByPrimaryKey(Integer id);

    /**
     * 根据主键选择性更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKeySelective(PpmallUser record);

    /**
     * 更新
     * @param record 待更新对象
     * @return 影响行数
     */
    int updateByPrimaryKey(PpmallUser record);

    /**
     * 根据用户名检查用户
     * @param username username
     * @return 影响行数
     */
    int checkUsername(String username);

    /**
     * 用户登录
     * @param username username
     * @param password password
     * @return PpmallUser
     */
    PpmallUser selectUserLogin(@Param("username") String username, @Param("password") String password);

    /**
     * 检查用户邮箱
     * @param email email
     * @return 影响行数
     */
    int checkUserEmail(String email);

    /**
     * 根据用户名查找用户问题
     * @param username username
     * @return String UserQuestion
     */
    String selectQuestionByUsername(String username);

    /**
     * 查找问题答案
     * @param username username
     * @param question question
     * @param answer answer
     * @return 影响行数
     */
    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    /**
     * 根据用户名更新密码
     * @param username username
     * @param passwordNew passwordNew
     * @return 影响行数
     */
    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    /**
     * 根据 Id 查找密码
     * @param password password
     * @param userId userId
     * @return 影响行数
     */
    int checkPasswordByUserId(@Param("password") String password, @Param("userId") Integer userId);

    /**
     * 根据 Id 检查邮箱
     * @param email email
     * @param userId userId
     * @return 影响行数
     */
    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);
}