package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.UserId;
import com.osci.crowdsync.entity.UpdatedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.stream.Stream;

public interface UpdatedUserRepository extends JpaRepository<UpdatedUser, UserId> {
}
