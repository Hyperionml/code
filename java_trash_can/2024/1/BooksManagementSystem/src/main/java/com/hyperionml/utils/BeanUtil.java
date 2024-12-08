package com.hyperionml.utils;

import com.hyperionml.pojo.User;
import com.hyperionml.vo.UserVO;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class BeanUtil {

    public static UserVO userPoToVo(User user){
        return new UserVO(user.getName(), user.getUserName(), user.getPassword(), String.valueOf(user.getGender()), String.valueOf(user.getRoot()));
    }

    public static List<UserVO> userPoToVoList(List<User> users){
        List<UserVO> list = new ArrayList<>();
        users.forEach(user -> {
            UserVO userVO = new UserVO();
            userVO.setName(user.getName());
            userVO.setUserName(user.getUserName());
            userVO.setPassword(user.getPassword());
            if(user.getGender() == 1){
                userVO.setGender("男");
            }else {
                userVO.setGender("女");
            }

            if(user.getRoot() == 1){
                userVO.setRoot("管理员");
            }else {
                userVO.setRoot("一般用户");
            }

            list.add(userVO);
        });
        return list;
    }
}
