package org.scaffold.common.security.jwt;

import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.jwt.crypto.sign.RsaVerifier;

public class Extract {

	public String extract(String token) {
//        String authHeaderValue = token.substring("Bearer".length()).trim();
		String authHeaderValue = token;
		int commaIndex = authHeaderValue.indexOf(44);
		if (commaIndex > 0) {
			authHeaderValue = authHeaderValue.substring(0, commaIndex);
		}

		String publicKey = "-----BEGIN PUBLIC KEY-----MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAx9glZ1N7qMRbxGGMgJ5BYSLC/YxpmjAn0d9Qo/q+PwZYMAFR7HEEgPkUQkPdkk7BCn6n6fIm5tTk0QTEX4cChxSWIrXUHa8jlpah5mfgn3rsGteC19/JFvJwggxMbiO3mgu8sHXaOfHWGFPqY1JUvjFrJdkRujMoEHpVYB8mAEzh4sx+rnSFcz/lHZjcwkxrFN09DVWpS80bEmaPBRFGIVICD8CI9OzWECOSqvPkTtEmDXzrsSn3L9BnVYgtSKlCxHgol4d4SkZvAOmSjVQexrSnDKdBHGg5w3+XO5FR+l+kWWyKV/E4KRaaVrIbIqzJBL/+brJK0A6X9hLevtkReQIDAQAB-----END PUBLIC KEY-----";
		try {
			RsaVerifier verifier = new RsaVerifier(publicKey);
			Jwt jwt = JwtHelper.decodeAndVerify(authHeaderValue, verifier);
			String content = jwt.getClaims();
//            Map<String,String> map = JsonUtils.serializable(content, Map.class);

//			Map<String, String> map = GsonUtils.get().readValueToMap(content, String.class, String.class);

			return content;
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return null;
	}
}
