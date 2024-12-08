package com.hyperionml.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.hyperionml.mapper.UserMapper;
import com.hyperionml.pojo.User;
import com.hyperionml.service.UserService;
import com.hyperionml.utils.BeanUtil;
import com.hyperionml.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    /*@Override
    public List<User> list() {

        //return BeanUtil.userPoToVoList(users);
        return userMapper.list();
    }*/

    @Override
    public int deleteById(Integer id) {
        return userMapper.deleteById(id);
    }

    @Override
    public void addUser(User user) {
        userMapper.addUser(user);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectById(id);
    }

    @Override
    public void updateById(User user) {
        if(user.getPassword().isEmpty()){
            user.setPassword("123456");
        }
        userMapper.updateById(user);
    }

    @Override
    public List<User> page(Integer page, Integer pageSize, String name, Integer gender) {
        PageHelper.startPage(page, pageSize);
        List<User> userList = userMapper.list(name, gender);
        Page<User> p = (Page<User>) userList;
        return p.getResult();

        /*Integer start = (page - 1) * pageSize;
        return userMapper.page(start, pageSize);*/
    }
}
