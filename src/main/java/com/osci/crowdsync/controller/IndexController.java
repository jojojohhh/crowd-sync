package com.osci.crowdsync.controller;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserIdDto;
import com.osci.crowdsync.service.impl.CrowdUserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
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
}
