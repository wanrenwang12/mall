package io.winters.mall.service;

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


    Boolean logout(Long userId);
}
