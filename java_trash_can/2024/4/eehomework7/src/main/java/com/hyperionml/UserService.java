package com.hyperionml;

import com.hyperionml.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Transactional
    public void zhuanZhang(String name1, String name2, Integer money){
        userMapper.addMoney(name2, money);
        userMapper.deleteMoney(name1, money);
    }
}
