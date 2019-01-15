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

    int deleteByPrimaryKey(Integer id);

    int insert(PpmallUser record);

    int insertSelective(PpmallUser record);

    PpmallUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PpmallUser record);

    int updateByPrimaryKey(PpmallUser record);

    int checkUsername(String username);

    PpmallUser selectUserLogin(@Param("username") String username, @Param("password") String password);

    int checkUserEmail(String email);

    String selectQuestionByUsername(String username);

    int checkAnswer(@Param("username") String username, @Param("question") String question, @Param("answer") String answer);

    int updatePasswordByUsername(@Param("username") String username, @Param("passwordNew") String passwordNew);

    int checkPasswordByUserId(@Param("password") String password, @Param("userId") Integer userId);

    int checkEmailByUserId(@Param("email") String email, @Param("userId") Integer userId);
}