package com.osci.crowdsync.scheduler;

import com.osci.crowdsync.dto.CrowdUserDto;
import com.osci.crowdsync.dto.SysUserDto;
import com.osci.crowdsync.service.CrowdUserService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class CrowdSyncScheduler {

    private final CrowdUserService crowdUserService;

    public void crowdSyncJob() {
        Stream<SysUserDto> users = crowdUserService.findAllUsers();

    }

}
