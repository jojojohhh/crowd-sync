package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.UpdatedUser;
import com.osci.crowdsync.entity.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * UPDATED_USER 테이블에 대한 JPARepository
 */
@Repository
public interface UpdatedUserRepository extends JpaRepository<UpdatedUser, UserId> {

    /**
     * CRUDRepository save 메소드
     * @param entity must not be {@literal null}.
     * @return UpdatedUser entity
     * @param <S> UpdatedUser
     */
    @Override
    <S extends UpdatedUser> S save(S entity);
}
