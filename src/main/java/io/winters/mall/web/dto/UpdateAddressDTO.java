package io.winters.mall.web.dto;

import lombok.Data;

@Data
public class UpdateAddressDTO {

    private Long addressId;

    private Long userId;

    private String userName;

    private String userPhone;

    private Byte defaultFlag;

    private String provinceName;

    private String cityName;

    private String regionName;

    private String detailAddress;

}
