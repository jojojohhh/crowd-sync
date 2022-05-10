package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.ReqUpdateUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.stream.Stream;

@Repository
public interface ReqUpdateUserRepository extends JpaRepository<ReqUpdateUser, String> {

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

    @Query(value = "UPDATE req_update_user SET updated = '1' WHERE user_id = :#{#reqUpdateUser.userId}", nativeQuery = true)
    Optional<ReqUpdateUser> updateUserByReqUpdateUser(ReqUpdateUser reqUpdateUser);

    @Override
    <S extends ReqUpdateUser> S save(S entity);
}
