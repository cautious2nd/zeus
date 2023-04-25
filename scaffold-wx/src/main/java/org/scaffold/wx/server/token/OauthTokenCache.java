package org.scaffold.wx.server.token;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class OauthTokenCache {


    /**
     * key:openId
     * value:token
     */
    private static final Map<String,Object> OAUTH_TOKEN_CACHE = new ConcurrentHashMap<>();



    public Object put(String openid,Object value){
        return OAUTH_TOKEN_CACHE.put(openid,value);
    }

    public Object get(String openid){
       return OAUTH_TOKEN_CACHE.get(openid);
    }

}
