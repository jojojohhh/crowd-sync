package com.osci.crowdsync.controller;

import com.osci.crowdsync.service.impl.CrowdUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class IndexController {

    private final CrowdUserServiceImpl crowdUserService;

    @RequestMapping("/update")
    public String update() {
        crowdUserService.updateUser();
        return "index";
    }

    @RequestMapping("/create")
    public String create() {
        crowdUserService.createUser();
        return "index";
    }
}
