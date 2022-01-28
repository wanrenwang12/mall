package io.winters.mall.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserAddressDORepo extends CrudRepository<UserAddressDO, Long> {

    List<UserAddressDO> findAllByUserId(Long userId);

    UserAddressDO findUserAddressDOByUserIdAndDefaultFlag(Long userId, Byte faultFlag);

    UserAddressDO save(UserAddressDO record);

    UserAddressDO findUserAddressDOByAddressId(Long addressID);

    int deleteUserAddressDOByAddressId(Long addressID);
}
