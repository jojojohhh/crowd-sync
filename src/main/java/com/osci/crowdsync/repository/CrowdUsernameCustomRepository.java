package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.CrowdUsernameCustom;
import com.osci.crowdsync.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CrowdUsernameCustomRepository extends JpaRepository<CrowdUsernameCustom, UserId> {

    @Override
    <S extends CrowdUsernameCustom> S save(S entity);
}
