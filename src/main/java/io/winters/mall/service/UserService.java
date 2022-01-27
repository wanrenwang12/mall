package io.winters.mall.service;

import io.winters.mall.web.dto.UserUpdateDTO;

public interface UserService {
    /**
     *
     * @param loginName
     * @param password
     * @return
     */
    String register(String loginName, String password);

    /**
     *
     * @param loginName
     * @param password
     * @return
     */
    String login(String loginName, String password);

    /**
     *
     * @param userId
     * @return
     */
    Boolean logout(Long userId);

    Boolean updateUserInfo(UserUpdateDTO user, String userName);


}
