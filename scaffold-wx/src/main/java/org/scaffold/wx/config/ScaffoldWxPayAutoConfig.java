package org.scaffold.wx.config;

import com.scaffold.file.io.FileIOUtil;
import com.wechat.pay.java.core.Config;
import com.wechat.pay.java.core.RSAAutoCertificateConfig;
import com.wechat.pay.java.service.payments.nativepay.NativePayService;
import org.scaffold.logger.log.ScaffoldLogger;
import org.scaffold.wx.config.yml.ScaffoldWxConfig;
import org.scaffold.wx.server.pay.WxNativePayService;
import org.scaffold.wx.server.token.WxServerTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;


@Configuration
@EnableConfigurationProperties(ScaffoldWxConfig.class)
@ConditionalOnClass(WxNativePayService.class)
@ConditionalOnProperty(prefix = "scaffold", value = "wx.pay", matchIfMissing = false)
public class ScaffoldWxPayAutoConfig {

    private static final ScaffoldLogger SCAFFOLD_LOGGER = new ScaffoldLogger(ScaffoldWxPayAutoConfig.class);
    @Autowired
    private ScaffoldWxConfig wxConfig;
    @Autowired
    private ResourceLoader resourceLoader;

    @Bean
    public RSAAutoCertificateConfig wxPayConfig() {
        Resource resource2 = resourceLoader.getResource("classpath:key/apiclient_key.pem");
        try {
            String fileValue = FileIOUtil.readFileFromSteam(resource2.getInputStream());
            RSAAutoCertificateConfig config =
                    new RSAAutoCertificateConfig.Builder()
                            .merchantId(wxConfig.getMerchantId())
                            .privateKey(fileValue)
//                            .privateKeyFromPath(resource2.getFile().getPath())
                            .merchantSerialNumber(wxConfig.getMerchantSerialNumber())
                            .apiV3Key(wxConfig.getApiV3key())
                            .build();
            return config;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("微信秘钥文件未找到1.");
        }
    }

    @Bean
    public NativePayService nativePayService(Config wxPayConfig) {
        NativePayService nativePayService = new NativePayService.Builder().config(wxPayConfig).build();
        return nativePayService;
    }
}
