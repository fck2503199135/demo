package com.example.demo.common;

import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * 缓存访问token
 *
 * @author fan
 */
@Component
@Slf4j
public class CacheService {

    @Resource
    private CacheManager cacheManager;

    /**
     * 保存web端token到缓存中
     *
     * @return
     */
    public String tokenCache(String user, String token) {
        final Cache cache = cacheManager.getCache("token_cache");
        final Element tokenElement = new Element(user, token);
        cache.put(tokenElement);

        return token;
    }

    /**
     * 从缓存中获取web端token
     *
     * @return
     */
    public String getTokenCache(String user) {
        Cache cache = cacheManager.getCache("token_cache");
        final Element tokenElement = cache.get(user);
        return (String) tokenElement.getObjectValue();
    }

    /**
     * 从缓存中移除web端token
     *
     * @return
     */
    public void removeTokenCache(String user) {
        Cache cache = cacheManager.getCache("token_cache");
        cache.remove(user);
    }

    /**
     * 保存用户信息到缓存中
     *
     * @return
     */
    public UserAccount userAccountCache(String user, UserAccount userAccount) {
        final Cache cache = cacheManager.getCache("user_account_cache");
        final Element tokenElement = new Element(user, userAccount);
        cache.put(tokenElement);

        return userAccount;
    }

    /**
     * 从缓存中获取用户信息信息
     *
     * @return
     */
    public UserAccount getUserAccountCache(String user) {
        Cache cache = cacheManager.getCache("user_account_cache");
        final Element tokenElement = cache.get(user);
        return (UserAccount) tokenElement.getObjectValue();
    }

}
