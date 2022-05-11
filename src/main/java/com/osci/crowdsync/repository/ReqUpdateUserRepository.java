package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.ReqUpdateUser;
import com.osci.crowdsync.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

/**
 * REQ_UPDATE_USER 테이블에 대한 JPARepository
 */
@Repository
public interface ReqUpdateUserRepository extends JpaRepository<ReqUpdateUser, UserId> {

    /**
     * updated_user 테이블의 display_name 과 req_update_user 테이블의 display_name 을 비교하여 같지 않고,
     * req_update_user 테이블의 updated 값이 0 인 열을 모두 select
     * Mysql Query 로 작성되어짐
     * @return ReqUpdateUser 타입의 Stream
     */
    @Query(value = "SELECT * " +
                   "FROM req_update_user r " +
                   "WHERE r.updated = '0' and " +
                         "r.user_id = (" +
                            "SELECT u.user_id " +
                            "FROM updated_user u " +
                            "WHERE u.user_id = r.user_id and " +
                                  "u.display_name != r.display_name)",
            nativeQuery = true)
    Stream<ReqUpdateUser> findAllReqUpdateUsers();

    /**
     * CRUDRepository save 메소드
     * @param entity must not be {@literal null}.
     * @return ReqUpdateUser entity
     * @param <S> ReqUpdateUser
     */
    @Override
    <S extends ReqUpdateUser> S save(S entity);
}
