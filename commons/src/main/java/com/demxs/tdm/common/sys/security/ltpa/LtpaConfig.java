package com.demxs.tdm.common.sys.security.ltpa;

/**
 * LTPA配置类
 * User: wuliepeng
 * Date: 2017-12-15
 * Time: 下午2:00
 */
public class LtpaConfig {
    public String	ltpaSecret;
    public String	tokenDomain;
    public String	dominohost;
    public int	tokenExpiration;
    public LtpaConfig() {
        ltpaSecret = "1cgjI5oeU1VWrkJmdPFO+AwVRCE=";
        dominohost = "";
        tokenDomain = ".longi-silicon.com";
        tokenExpiration = 1800000;
    }
}