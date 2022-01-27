package io.winters.mall.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserTokenDORepo extends CrudRepository<UserTokenDO, Long> {

    //int deleteUserTokenDOByUserId(long userId);
    //int deleteUserTokenDOByUserId(Long userId);



    UserTokenDO save(UserTokenDO userTokenDo);
    //int insert(UserTokenDO record);

    //int insertSelective(UserTokenDO record);

    UserTokenDO findUserTokenDOByUserId(Long userId);
    UserTokenDO findUserTokenDOByToken(String token);

    void deleteUserTokenDOByUserId(Long userId);


    //UserTokenDO selectByToken(String token);

    //int updateByPrimaryKeySelective(UserTokenDO record);

    //int updateByPrimaryKey(UserTokenDO record);

}
