package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.UpdatedUser;
import com.osci.crowdsync.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UpdatedUserRepository extends JpaRepository<UpdatedUser, UserId> {
    @Override
    <S extends UpdatedUser> S save(S entity);
}
