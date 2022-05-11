package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.SysUser;
import com.osci.crowdsync.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

/**
 * SYS_USER 테이블에 대한 JPARepository
 */
@Repository
public interface SysUserRepository extends JpaRepository<SysUser, UserId> {

    /**
     * updated_user 테이블에 존재하지 않으면 select
     * Mysql Query 로 작성되어짐
     * @return SysUser 타입의 Stream
     */
    @Query(value = "SELECT * " +
            "FROM sys_user " +
            "WHERE (CORP_CODE, USER_ID) NOT IN (" +
                "SELECT CORP_CODE, USER_ID " +
                "FROM updated_user)",
            nativeQuery = true)
    Stream<SysUser> streamAllNotUpdated();

}
