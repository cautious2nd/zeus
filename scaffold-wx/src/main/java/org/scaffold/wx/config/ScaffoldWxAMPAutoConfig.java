package org.scaffold.wx.config;

import org.scaffold.wx.config.yml.ScaffoldWxAMPConfig;
import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.scaffold.wx.server.token.WxServerTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(ScaffoldWxAMPConfig.class)
@ConditionalOnClass(WxServerTokenService.class)
@ConditionalOnProperty(prefix = "scaffold", value = "wx.amp", matchIfMissing = false)
public class ScaffoldWxAMPAutoConfig {

    public ScaffoldWxAMPAutoConfig() {
        System.out.println("ScaffoldWxAMPAutoConfig");
    }
}
