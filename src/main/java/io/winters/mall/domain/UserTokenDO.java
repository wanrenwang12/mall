package io.winters.mall.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Data
@Entity
public class UserTokenDO {

    @Id
    //有问题
    private Long userId;

    private String token;

    private Date updateTime;

    private Date expireTime;

}
