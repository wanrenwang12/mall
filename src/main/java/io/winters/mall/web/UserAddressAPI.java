package io.winters.mall.web;

import io.winters.mall.common.ServiceResultEnum;
import io.winters.mall.config.annotation.TokenToUser;
import io.winters.mall.domain.UserAddressDO;
import io.winters.mall.domain.UserDO;
import io.winters.mall.service.UserAddressService;
import io.winters.mall.service.UserService;
import io.winters.mall.util.BeanUtil;
import io.winters.mall.web.dto.SaveUserAddressDTO;
import io.winters.mall.web.dto.UpdateAddressDTO;
import io.winters.mall.web.vo.Result;
import io.winters.mall.web.vo.ResultGenerator;
import io.winters.mall.web.vo.UserAddressVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class UserAddressAPI {

    private UserAddressService userAddressService;
    private ResultGenerator generator;

    @Autowired
    public UserAddressAPI(UserAddressService userAddressService, ResultGenerator generator) {
        this.userAddressService = userAddressService;
        this.generator = generator;
    }

    @GetMapping("/address")
    public Result<List<UserAddressVO>> addressList(@TokenToUser UserDO loginMallUser) {
        return generator.genSuccessResult(userAddressService.getMyAddresses(loginMallUser.getUserId()));
    }

    @PostMapping("/address")
    public Result<Boolean> saveUserAddress(@RequestBody SaveUserAddressDTO saveUserAddressDTO,
                                           @TokenToUser UserDO loginMallUser) {
        UserAddressDO userAddress = new UserAddressDO();
        BeanUtil.copyProperties(saveUserAddressDTO, userAddress);
        userAddress.setUserId(loginMallUser.getUserId());
        Boolean saveResult = userAddressService.saveUserAddress(userAddress);
        //添加成功
        if (saveResult) {
            return generator.genSuccessResult();
        }
        //添加失败
        return generator.genFailResult("添加失败");
    }

    @PutMapping("/address")
    public Result<Boolean> updateMallUserAddress(@RequestBody UpdateAddressDTO updateAddressDTO,
                                                 @TokenToUser UserDO loginMallUser) {
        UserAddressDO userAddress = userAddressService.getMallUserAddressById(updateAddressDTO.getAddressId());
        if (!loginMallUser.getUserId().equals(userAddress.getUserId())) {
            return generator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        UserAddressDO userAddressDO = new UserAddressDO();
        BeanUtil.copyProperties(updateAddressDTO, userAddressDO);
        userAddressDO.setUserId(loginMallUser.getUserId());
        Boolean updateResult = userAddressService.saveUserAddress(userAddressDO);
        //修改成功
        if (updateResult) {
            return generator.genSuccessResult();
        }
        //修改失败
        return generator.genFailResult("修改失败");
    }

    @GetMapping("/address/{addressId}")
    public Result<UserAddressVO> getMallUserAddress(@PathVariable("addressId") Long addressId,
                                                              @TokenToUser UserDO loginUser) {
        UserAddressDO userAddressById = userAddressService.getMallUserAddressById(addressId);
        UserAddressVO userAddressVO = new UserAddressVO();
        BeanUtil.copyProperties(userAddressById, userAddressVO);
        if (!loginUser.getUserId().equals(userAddressById.getUserId())) {
            return generator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        return generator.genSuccessResult(userAddressVO);
    }

    @GetMapping("/address/default")
    //get the default address
    public Result getDefaultMallUserAddress(@TokenToUser UserDO loginUser) {
        UserAddressDO userAddressById = userAddressService.getMyDefaultAddressByUserId(loginUser.getUserId());
        return generator.genSuccessResult(userAddressById);
    }

    @DeleteMapping("/address/{addressId}")
    @Transactional
    public Result deleteAddress(@PathVariable("addressId") Long addressId,
                                @TokenToUser UserDO loginMallUser) {
        UserAddressDO mallUserAddressById = userAddressService.getMallUserAddressById(addressId);
        if (!loginMallUser.getUserId().equals(mallUserAddressById.getUserId())) {
            return generator.genFailResult(ServiceResultEnum.REQUEST_FORBIDEN_ERROR.getResult());
        }
        Boolean deleteResult = userAddressService.deleteById(addressId);
        //删除成功
        if (deleteResult) {
            return generator.genSuccessResult();
        }
        //删除失败
        return generator.genFailResult(ServiceResultEnum.OPERATE_ERROR.getResult());
    }


}
