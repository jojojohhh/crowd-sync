package com.osci.crowdsync.controller;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserIdDto;
import com.osci.crowdsync.entity.SysUser;
import com.osci.crowdsync.service.impl.CrowdUserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class IndexController {

    private final CrowdUserServiceImpl crowdUserService;

    @RequestMapping("/update")
    public String update() {
        CrowdUserDto userDto = CrowdUserDto.builder()
                .name("test1234")
                .password(new CrowdUserDto.Password())
                .firstName("sangje")
                .lastName("jo")
                .displayName("test1234")
                .email("sjjo@osci.kr")
                .build();
        crowdUserService.updateCrowdUser(userDto);
        return "index";
    }

    @RequestMapping("/create")
    public String create() {
        CrowdUserDto userDto = CrowdUserDto.builder()
                .name("test1234")
                .password(new CrowdUserDto.Password())
                .firstName("sangje")
                .lastName("jo")
                .displayName("test-sangjejo")
                .email("sjjo@osci.kr")
                .build();
        crowdUserService.createCrowdUser(userDto);
        return "index";
    }

    @GetMapping("/{corpCode}/{userId}")
    public String get(@PathVariable String corpCode, @PathVariable String userId) {
        return crowdUserService.findUserById(
                SysUserIdDto.builder()
                        .corpCode(corpCode)
                        .userId(userId)
                        .build()
        ).toString();
    }

    @GetMapping("/all")
    public String getNotUpdatedAllUsers() {
        List<SysUser> users = crowdUserService.findAllNotUpdated();
        for (int i = 0; i < users.size(); i++) {
            log.warn(users.get(i).toString());
        }
        return "true";
    }
}
