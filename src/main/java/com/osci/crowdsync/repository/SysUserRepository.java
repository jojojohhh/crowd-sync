package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.SysUser;
import com.osci.crowdsync.entity.SysUserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface SysUserRepository extends JpaRepository<SysUser, SysUserId> {

    @Query(value = "SELECT u FROM SysUser u WHERE u.corpCode = :corp_code AND u.userId = :user_id")
    Optional<SysUser> findById(@Param(value = "corp_code") String corpCode, @Param(value = "user_id") String userId);

    @Query(value = "SELECT * FROM SysUser", nativeQuery = true)
    Stream<SysUser> streamAll();


}
