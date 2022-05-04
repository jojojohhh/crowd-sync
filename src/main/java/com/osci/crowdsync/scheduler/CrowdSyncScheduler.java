package com.osci.crowdsync.scheduler;

import com.osci.crowdsync.service.CrowdUserService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CrowdSyncScheduler {

    private final CrowdUserService crowdUserService;

    public void crowdSyncJob() {
        //crowdUserService
    }

}
