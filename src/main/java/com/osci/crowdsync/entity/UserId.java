package com.osci.crowdsync.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


/**
 * SYS_USER, UPDATED_USER, REQ_UPDATE_USER 테이블이 공통으로 갖는 PK CLASS
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserId implements Serializable {
    private String corpCode;
    private String userId;
}
