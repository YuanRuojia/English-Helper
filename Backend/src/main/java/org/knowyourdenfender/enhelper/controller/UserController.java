package org.knowyourdenfender.enhelper.controller;

import org.knowyourdenfender.enhelper.pojo.*;
import org.knowyourdenfender.enhelper.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.PlatformLoggingMXBean;
import java.util.ArrayList;
import java.util.List;

/**
 * Contains entrypoint for user operation request.
 *
 * @author Miris,xxx...
 */
@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/login")
    public ServerResult login(UserDTO userDTO){
        UserVO userVO = userService.login(userDTO);

        if(userVO != null){
            ServerResult serverResult = new ServerResult(0, "登录成功", userVO);
            return serverResult;
        }
        else {
            ServerResult serverResult = new ServerResult(101, "登录失败", null);
            return serverResult;
        }
    }
    @RequestMapping("/user/UserInfo")
    public ServerResult UserInfo(String name){
        UserVO userVO = userService.UserInfo(name);
        return new ServerResult(0,"查询成功",userVO);
    }
    @RequestMapping("/user/changePassword")
    public ServerResult changePassword(UserPasswordDTO userPasswordDTO){
        boolean isSuccess = userService.changePassword(userPasswordDTO);
        if(isSuccess){
            return new ServerResult(0,"修改密码成功",null);
        }
        else{
            return new ServerResult(103,"修改密码失败",null);
        }
    }
    @RequestMapping("/user/register")
    public ServerResult register(UserInfoDTO userInfoDTO){
        Integer userId = userService.register(userInfoDTO);
        return new ServerResult(0,"注册成功",userId);
    }
    @RequestMapping("/user/collectInfo")
    public ServerResult collectInfo(Integer id){
        List<CollectVO> voList = userService.collectInfo(id);
        return new ServerResult(0,"查询成功",voList);
    }
    @RequestMapping("user/mistakeInfo")
    public ServerResult mistakeInfo(Integer id){
        List<MistakeVO> voList = userService.mistakeInfo(id);
        return new ServerResult(0,"查询成功",voList);
    }
    @RequestMapping("/user/wordCount")
    public ServerResult wordCount(){
        Integer count = userService.wordCount();
        if(count!=0){
            return new ServerResult(0,"查询总单词数成功",count);
        }
        else {
            return new ServerResult(104,"查询总单词数失败",null);
        }
    }
    @RequestMapping("/user/collectCount")
    public ServerResult collectCount(Integer id){
        Integer collectCount = userService.collectCount(id);
        if(collectCount!=0){
            return new ServerResult(0,"查询已背单词数成功",collectCount);
        }
        else{
            return new ServerResult(105,"查询已背单词数失败",null);
        }
    }
    @RequestMapping("/user/weeklyCollect")
    public ServerResult weeklyCollect(Integer id){
        List<Integer> weekly=userService.weeklyCollect(id);
        return new ServerResult(0,"查询本周已背单词数成功",weekly);
    }
    @RequestMapping("/user/weeklyMistake")
    public ServerResult weeklyMistake(Integer id){
        List<Integer> weekly=userService.weeklyMistake(id);
        return new ServerResult(0,"查询本周错误单词数成功",weekly);
    }
}
