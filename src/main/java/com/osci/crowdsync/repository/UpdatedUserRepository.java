package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.UpdatedUser;
import com.osci.crowdsync.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpdatedUserRepository extends JpaRepository<UpdatedUser, UserId> {
}
