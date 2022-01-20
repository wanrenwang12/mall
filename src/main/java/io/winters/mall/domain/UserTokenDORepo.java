package io.winters.mall.domain;

import org.springframework.data.repository.CrudRepository;

public interface UserTokenDORepo extends CrudRepository<UserTokenDO, Long> {

    int deleteByPrimaryKey(Long userId);

    int insert(UserTokenDO record);

    int insertSelective(UserTokenDO record);

    UserTokenDO selectByPrimaryKey(Long userId);
    UserTokenDO findByUserId(Long userID);

    UserTokenDO selectByToken(String token);

    int updateByPrimaryKeySelective(UserTokenDO record);

    int updateByPrimaryKey(UserTokenDO record);

}
