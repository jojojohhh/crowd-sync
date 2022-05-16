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
     * Display Name 을 업데이트 해야하는 사용자 리스트를 select
     * sys_user, updated_user, crowd_username_custom 세개의 테이블을 모두 left join
     * Mysql Query 로 작성되어짐
     * @return SysUser 타입의 Stream
     */
    @Query(value = "select * " +
                   "from sys_user s left join updated_user u on s.corp_code = u.corp_code and s.user_id = u.user_id " +
                                   "left join crowd_username_custom c on s.corp_code = c.corp_code and s.user_id = c.user_id " +
                   "where u.user_id is null or (c.user_custom_name is not null and u.display_name != c.user_custom_name) or (s.name != u.name or s.dept_name != u.dept_name or s.pos_name != u.pos_name)",
            nativeQuery = true)
    Stream<SysUser> getAllUsers();

}
