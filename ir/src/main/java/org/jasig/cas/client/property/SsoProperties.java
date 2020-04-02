package org.jasig.cas.client.property;

/**
 * 单点登陆属性配置
 */
public class SsoProperties {

    /**
     * 项目访问路径，前端项目，不是接口项目
     */
    public static String serviceName;

    /**
     * sso server 服务地址
     */
    public static String ssoServerPath;

    /**
     * cas登陆url
     */
    public static String casServerLoginUrl;

    /**
     * cas登出url
     */
    public static String casServerLogoutUrl;
    /**
     *  cas url前缀
     */
    public static String casServerUrlPrefix;




    /**
     * 初始化sso相关配置
     */
    public static void init(String serviceName, String ssoServerPath) {
        SsoProperties.serviceName = serviceName;
        SsoProperties.ssoServerPath = ssoServerPath;

        SsoProperties.casServerLoginUrl = ssoServerPath + "/cas/login";
        SsoProperties.casServerLogoutUrl = ssoServerPath + "/cas/logout";
        SsoProperties.casServerUrlPrefix = ssoServerPath + "/cas";
        
    }



}
