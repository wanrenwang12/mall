package io.winters.mall.service.impl;

import io.winters.mall.common.MallException;
import io.winters.mall.common.ServiceResultEnum;
import io.winters.mall.domain.UserAddressDO;
import io.winters.mall.domain.UserAddressDORepo;
import io.winters.mall.service.UserAddressService;
import io.winters.mall.util.BeanUtil;
import io.winters.mall.web.vo.UserAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Component
@Service
public class UserAddressServiceImpl implements UserAddressService {


    private UserAddressDORepo userAddressDORepo;

    @Autowired
    public UserAddressServiceImpl(UserAddressDORepo userAddressDORepo) {
        this.userAddressDORepo = userAddressDORepo;
    }

    @Override
    public List<UserAddressVO> getMyAddresses(Long userId) {
     List<UserAddressDO> myAddressList = userAddressDORepo.findAllByUserId(userId);
     List<UserAddressVO> userAddressVOS = BeanUtil.copyList(myAddressList, UserAddressVO.class);
     return userAddressVOS;
     }

    @Override
    @Transactional
    public Boolean saveUserAddress(UserAddressDO userAddressDO) {
        Date now = new Date();
        if (userAddressDO.getDefaultFlag().intValue() == 1) {
            //添加默认地址，需要将原有的默认地址修改掉
            UserAddressDO defaultAddress = userAddressDORepo.findUserAddressDOByUserIdAndDefaultFlag(userAddressDO.getUserId(), (byte) 1);
            if (defaultAddress != null) {
                defaultAddress.setDefaultFlag((byte) 0);
                defaultAddress.setUpdateTime(now);
                UserAddressDO updateResult = userAddressDORepo.save(defaultAddress);
                if (updateResult == null) {
                    //未更新成功
                    MallException.fail(ServiceResultEnum.DB_ERROR.getResult());
                }
            }
        }
        return userAddressDORepo.save(userAddressDO) != null;
    }

    @Override
    public UserAddressDO getMallUserAddressById(Long addressId) {
        UserAddressDO userAddress = userAddressDORepo.findUserAddressDOByAddressId(addressId);
        if (userAddress == null) {
            MallException.fail(ServiceResultEnum.DATA_NOT_EXIST.getResult());
        }
        return userAddress;
    }

    @Override
    public UserAddressDO getMyDefaultAddressByUserId(Long userId) {
        return userAddressDORepo.findUserAddressDOByUserIdAndDefaultFlag(userId, (byte)1);
    }

    @Override
    public Boolean deleteById(Long addressId) {
        return userAddressDORepo.deleteUserAddressDOByAddressId(addressId) > 0;
    }

}
