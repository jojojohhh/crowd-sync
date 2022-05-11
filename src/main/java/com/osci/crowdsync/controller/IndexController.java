package com.osci.crowdsync.controller;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.service.CrowdUserService;
import com.osci.crowdsync.service.ReqUpdateUserService;
import com.osci.crowdsync.service.UpdatedUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
public class IndexController {

    private final CrowdUserService crowdUserService;
    private final UpdatedUserService updatedUserService;
    private final ReqUpdateUserService reqUpdateUserService;

    @GetMapping("/test")
    public String findReq() {
        reqUpdateUserService.findAllReqUpdateUsers().forEach(reqUpdateUserDto -> {
            log.info(reqUpdateUserDto.toString());
        });
        return "true";
    }

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

    @GetMapping("/all")
    public String getNotUpdatedAllUsers() {
        List<SysUserDto> users = crowdUserService.findAllNotUpdated();
        for (int i = 0; i < users.size(); i++) {
            log.warn(users.get(i).toString());
        }
        return "true";
    }
}
