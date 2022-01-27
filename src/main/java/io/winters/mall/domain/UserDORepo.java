package io.winters.mall.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

@Component
public interface UserDORepo extends CrudRepository<UserDO, Long>{

    UserDO findUserDOByLoginName(String loginName);

    UserDO save(UserDO userDo);

    UserDO findUserDOByLoginNameAndPassword(String loginName, String password);

    UserDO findUserDOByUserId(Long userId);


    //UserDO findUserDOByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

    //int lockUserBatch(@Param("ids") Long[] ids, @Param("lockStatus") int lockStatus);
}
