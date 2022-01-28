package io.winters.mall.service;

import io.winters.mall.domain.UserAddressDO;
import io.winters.mall.web.vo.UserAddressVO;
import org.springframework.stereotype.Component;

import java.util.List;


public interface UserAddressService {

    /**
     * 获取我的收货地址
     *
     * @param userId
     * @return
     */
    List<UserAddressVO> getMyAddresses(Long userId);

    Boolean saveUserAddress(UserAddressDO userAddressDO);

    UserAddressDO getMallUserAddressById(Long addressId);

    UserAddressDO getMyDefaultAddressByUserId(Long userID);

    Boolean deleteById(Long addressId);
}
