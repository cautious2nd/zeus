package org.scaffold.wx.config;

import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.scaffold.wx.server.token.WxServerTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Configuration
@EnableConfigurationProperties(ScaffoldWxConfig.class)
@ConditionalOnClass(WxServerTokenService.class)
@ConditionalOnProperty(prefix = "scaffold", value = "wx", matchIfMissing = false)
public class ScaffoldWxAutoConfig {

    public ScaffoldWxAutoConfig() {
        System.out.println("ScaffoldWxAutoConfig");
    }








}
