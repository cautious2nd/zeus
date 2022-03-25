package org.scaffold.common.security.jwt;


import org.scaffold.common.json.JsonUtils;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

import java.util.Map;

public class Extract {

    public void extract(String token) {
//        String authHeaderValue = token.substring("Bearer".length()).trim();
        String authHeaderValue =token;
        int commaIndex = authHeaderValue.indexOf(44);
        if (commaIndex > 0) {
            authHeaderValue = authHeaderValue.substring(0, commaIndex);
        }

        String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx9glZ1N7qMRbxGGMgJ5BYSLC/YxpmjAn0d9Qo/q+PwZYMAFR7HEEgPkUQkPdkk7BCn6n6fIm5tTk0QTEX4cChxSWIrXUHa8jlpah5mfgn3rsGteC19/JFvJwggxMbiO3mgu8sHXaOfHWGFPqY1JUvjFrJdkRujMoEHpVYB8mAEzh4sx+rnSFcz/lHZjcwkxrFN09DVWpS80bEmaPBRFGIVICD8CI9OzWECOSqvPkTtEmDXzrsSn3L9BnVYgtSKlCxHgol4d4SkZvAOmSjVQexrSnDKdBHGg5w3+XO5FR+l+kWWyKV/E4KRaaVrIbIqzJBL/+brJK0A6X9hLevtkReQIDAQAB-----END PUBLIC KEY-----";
    try{
            RsaVerifier verifier = new RsaVerifier(publicKey);
            Jwt jwt = JwtHelper.decodeAndVerify(authHeaderValue, verifier);
            String content = jwt.getClaims();
            Map map = JsonUtils.serializable(content, Map.class);
            String json = JsonUtils.deserializer(map.get("user_info"));
//            UserInfo userInfo = JsonUtils.serializable(json, UserInfo.class);

        } catch (Exception exception) {
            exception.printStackTrace();
        }


    }
}
