package com.hyperionml.controller;

import com.hyperionml.pojo.Result;
import com.hyperionml.pojo.User;
import com.hyperionml.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*",maxAge = 3600,allowedHeaders="*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*@GetMapping
    public Result list(){
        return Result.success(userService.list());
    }*/

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,
                       @RequestParam(defaultValue = "5") Integer pageSize,
                       String name, Integer gender){
        return Result.success(userService.page(page, pageSize, name, gender));
    }

    @GetMapping("/{id}")
    public Result selectById(@PathVariable Integer id){
        User user = userService.selectById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Integer id){
        int res = userService.deleteById(id);
        if(res != 0){
            return Result.success();
        }else {
            return Result.error("删除失败");
        }
    }

    @PostMapping("/save")
    public Result addUser(@RequestBody User user){
        userService.addUser(user);
        return Result.success();
    }

    @PutMapping
    public Result updateUserById(@RequestBody User user){
        userService.updateById(user);
        return Result.success();
    }
}
