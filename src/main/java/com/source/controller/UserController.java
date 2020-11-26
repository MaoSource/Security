package com.source.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Source
 * @Date: 2020/11/18/11:25
 * @Description:
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @RequestMapping("/add")
    public String add(){
        return "/add";
    }

    @RequestMapping("/update")
    public String update(){
        return "/update";
    }

    @RequestMapping("/delete")
    public String delete(){
        return "/delete";
    }

    @RequestMapping("/list")
    public String list(){
        return "/list";
    }
}
