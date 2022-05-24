package com.osci.crowdsync.repository;

import com.osci.crowdsync.entity.QUserDto;
import com.osci.crowdsync.dto.UserDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.osci.crowdsync.entity.QCrowdUsernameCustom.crowdUsernameCustom;
import static com.osci.crowdsync.entity.QSysUser.sysUser;
import static com.osci.crowdsync.entity.QUpdatedUser.updatedUser;


@Repository
@RequiredArgsConstructor
public class UserReqUpdateRepositoryImpl implements UserReqUpdateRepository {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<UserDto> findUserRequireUpdate() {
        return jpaQueryFactory
                .select(new QUserDto(sysUser.userId, sysUser.name, sysUser.deptName, sysUser.posName, sysUser.email, sysUser.useYn, updatedUser.deptName, updatedUser.posName, updatedUser.displayName, crowdUsernameCustom.userCustomName))
                .from(sysUser)
                .leftJoin(sysUser.crowdUsernameCustom, crowdUsernameCustom)
                .leftJoin(sysUser.updatedUser, updatedUser)
                .where(
                        sysUser.useYn.eq("Y").and(sysUser.corpCode.eq("OSC"))
                                .and(
                                        (sysUser.deptName.ne(updatedUser.deptName).or(sysUser.posName.ne(updatedUser.posName)))
                                                .or
                                        (crowdUsernameCustom.userCustomName.isNotNull().and(updatedUser.displayName.ne(crowdUsernameCustom.userCustomName)))
                                                .or
                                        (updatedUser.userId.isNull())
                                )
                )
                .fetch();
    }
}
