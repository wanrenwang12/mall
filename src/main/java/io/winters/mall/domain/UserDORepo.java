package io.winters.mall.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserDORepo extends CrudRepository<UserDO, Long>{

    UserDO findUserDOByLoginName(String loginName);

    UserDO save(UserDO userDo);

    UserDO findUserDOByLoginNameAndPassword(@Param("loginName") String loginName, @Param("password") String password);

    int lockUserBatch(@Param("ids") Long[] ids, @Param("lockStatus") int lockStatus);
}
