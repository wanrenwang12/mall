package io.winters.mall.service;

import org.springframework.stereotype.Component;

@Component
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
     * @param passwordMD5
     * @return
     */
    String login(String loginName, String passwordMD5);


}
