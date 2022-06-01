package com.osci.crowdsync.repository;

import com.osci.crowdsync.dto.*;
import com.osci.crowdsync.entity.QSysUser;
import com.osci.crowdsync.entity.QUpdatedUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.osci.crowdsync.entity.QCrowdUsernameCustom.crowdUsernameCustom;
import static com.osci.crowdsync.entity.QSysUser.sysUser;
import static com.osci.crowdsync.entity.QUpdatedUser.updatedUser;


/**
 * QueryDsl 적용 Repository
 */
@Repository
@RequiredArgsConstructor
public class UserReqUpdateRepositoryImpl implements UserReqUpdateRepository {

    private final JPAQueryFactory jpaQueryFactory;

    /**
     * Display name 업데이트 해야할 사용자를 SELECT 하는 Method
     * @return List<UserDto>
     */
    public List<UserDto> findUserRequireUpdate() {
        return jpaQueryFactory
                .select(new QUserDto(sysUser.corpCode, sysUser.userId, sysUser.name, sysUser.deptName, sysUser.posName, sysUser.email, updatedUser.displayName, crowdUsernameCustom.userCustomName, sysUser.useYn, crowdUsernameCustom.chkYn))
                .from(sysUser)
                .leftJoin(sysUser.crowdUsernameCustom, crowdUsernameCustom)
                .leftJoin(sysUser.updatedUser, updatedUser)
                .where(
                        (sysUser.useYn.eq("Y").and(sysUser.corpCode.eq("OSC"))
                                .and(
                                        (sysUser.deptName.ne(updatedUser.deptName).or(sysUser.posName.ne(updatedUser.posName)))
                                                .or
                                        (crowdUsernameCustom.userCustomName.isNotNull().and(updatedUser.displayName.ne(crowdUsernameCustom.userCustomName)))
                                                .or
                                        (updatedUser.userId.isNull())
                                )).and(crowdUsernameCustom.chkYn.isNull().or(crowdUsernameCustom.chkYn.ne("Y")).or(updatedUser.userId.isNull()))
                )
                .fetch();
    }

    /**
     * 삭제 대상 사용자들을 SELECT 하는 Method
     * @return List<InactiveUserDto>
     */
    public List<InactiveUserDto> findInactiveUser() {
        return jpaQueryFactory
                .select(new QInactiveUserDto(sysUser.corpCode, sysUser.userId, sysUser.name, sysUser.deptName, sysUser.posName, updatedUser.firstName, updatedUser.lastName, sysUser.email, updatedUser.displayName, sysUser.useYn))
                .from(sysUser)
                .join(sysUser.updatedUser, updatedUser).on(sysUser.useYn.eq("N").and(updatedUser.active.isNull().or(updatedUser.active.eq("Y"))))
                .fetch();
    }

    public List<SysUserDto> findNewUsers() {
        return jpaQueryFactory
                .select(new QSysUserDto(sysUser.corpCode, sysUser.userId, sysUser.name, sysUser.deptName, sysUser.posName, sysUser.email))
                .from(sysUser)
                .leftJoin(sysUser.updatedUser, updatedUser)
                .where(updatedUser.userId.isNull())
                .fetch();
    }

}
