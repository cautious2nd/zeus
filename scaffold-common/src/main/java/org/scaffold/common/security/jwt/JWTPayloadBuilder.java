/**
 * Author : chiziyue
 * Date : 2021年11月11日 上午10:18:10
 * Title : org.scaffold.common.security.jwt.JWTPayloadBuilder.java
 *
**/
package org.scaffold.common.security.jwt;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.scaffold.common.json.GsonUtils;

public class JWTPayloadBuilder {

	/**
	 * 令牌默认有效时间，30分钟
	 */
	private Long validTime = 1000 * 60 * 30L;

	/**
	 * 附加信息
	 */
	private Map<String, String> additional;

	/**
	 * jwt签发者
	 */
	private String iss;
	/**
	 * jwt所面向的用户
	 */
	private String sub;
	/**
	 * 接收jwt的一方
	 */
	private String aud;
	/**
	 * jwt的过期时间，这个过期时间必须要大于签发时间
	 */
	private Long exp;
	/**
	 * 定义在什么时间之前，该jwt都是不可用的.
	 */
	private Long nbf;
	/**
	 * jwt的签发时间
	 */
	private Long iat = System.currentTimeMillis();
	/**
	 * jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
	 */
	private String jti = UUID.randomUUID().toString();

	/**
	 * 角色集合
	 */
	private Set<String> roles = new HashSet<>();

	/**
	 * jwt签发者
	 */
	public JWTPayloadBuilder iss(String iss) {
		this.iss = iss;
		return this;
	}

	/**
	 * jwt所面向的用户
	 */
	public JWTPayloadBuilder sub(String sub) {
		this.sub = sub;
		return this;
	}

	/**
	 * 接收jwt的一方
	 */
	public JWTPayloadBuilder aud(String aud) {
		this.aud = aud;
		return this;
	}

	/**
	 * jwt的过期时间，这个过期时间必须要大于签发时间
	 */
	public JWTPayloadBuilder exp(Long exp) {
		this.exp = exp == null ? this.iat + validTime : this.exp;
		return this;
	}

	/**
	 * 定义在什么时间之前，该jwt都是不可用的.
	 */
	public JWTPayloadBuilder nbf(Long nbf) {
		this.nbf = nbf;
		return this;
	}

	/**
	 * 附加信息
	 */
	public JWTPayloadBuilder additional(Map<String, String> additional) {
		this.additional = additional;
		return this;
	}

	/**
	 * 角色集合
	 */
	public JWTPayloadBuilder roles(Set<String> roles) {
		this.roles = roles;
		return this;
	}

	public String builder() {

		JWTPayload payload = new JWTPayload();
		payload.setIss(this.iss);
		payload.setSub(this.sub);
		payload.setAud(this.aud);
		payload.setIat(this.iat);
		payload.setExp(this.exp);
		payload.setNbf(this.nbf);
		payload.setJti(this.jti);
		payload.setAdditional(this.additional);
		payload.setRoles(this.roles);

		return GsonUtils.get().toJson(payload);

	}

	/**
	 * payload信息体
	 * 
	 * @author czy
	 *
	 */
	class JWTPayload {
		private String iss;// iss: jwt签发者
		private String sub;// sub: jwt所面向的用户
		private String aud;// aud: 接收jwt的一方
		private Long exp;// exp: jwt的过期时间，这个过期时间必须要大于签发时间
		private Long nbf;// nbf: 定义在什么时间之前，该jwt都是不可用的.
		private Long iat;// iat: jwt的签发时间
		private String jti;// jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。
		private Map<String, String> additional;
		private Set<String> roles;

		public String getIss() {
			return iss;
		}

		public void setIss(String iss) {
			this.iss = iss;
		}

		public String getSub() {
			return sub;
		}

		public void setSub(String sub) {
			this.sub = sub;
		}

		public String getAud() {
			return aud;
		}

		public void setAud(String aud) {
			this.aud = aud;
		}

		public Long getExp() {
			return exp;
		}

		public void setExp(Long exp) {
			this.exp = exp;
		}

		public Long getNbf() {
			return nbf;
		}

		public void setNbf(Long nbf) {
			this.nbf = nbf;
		}

		public Long getIat() {
			return iat;
		}

		public void setIat(Long iat) {
			this.iat = iat;
		}

		public String getJti() {
			return jti;
		}

		public void setJti(String jti) {
			this.jti = jti;
		}

		public Map<String, String> getAdditional() {
			return additional;
		}

		public void setAdditional(Map<String, String> additional) {
			this.additional = additional;
		}

		public Set<String> getRoles() {
			return roles;
		}

		public void setRoles(Set<String> roles) {
			this.roles = roles;
		}

	}
}
