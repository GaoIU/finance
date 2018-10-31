package com.fanteng.finance.app.properties;

import com.fanteng.util.AESUtil;
import com.fanteng.util.RSAUtil;

public class SignatureProperties {

	/** 服务端公钥 */
	public static final String SERVER_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq9COW/HaiJ+pqZIOebmc+n8YxrgWxpcvRr3jerNK5+GPWBXanoyO2x67B9TsHg1FHBgLDZ4PgWGlwdfEZhW6H+a50Ftj+ZhkweVn2YOk1Y1nkcD5PMJY2xv46yTNYTgl2jCC6KB0wxO2lMGKqot8J77kCSW7VWfQf3uDIREGxK5qQ+wdFU++b9bcDWJh8TRBBkbHRr0pmY/V6RpimVIpO1FmCdsLAc9o2ywdClX4NqlgZOr2LQIVSNHi6XI3debMz/pYpcgOafvF4LnPOzxB7YBYOQgF5pzbQ4ItgfPrWh343VcPF0n122pn1eXLiGXUdrWJx27nmOAtLkBDU5uxcQIDAQAB";

	/** 服务端私钥 */
	public static final String SERVER_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCr0I5b8dqIn6mpkg55uZz6fxjGuBbGly9GveN6s0rn4Y9YFdqejI7bHrsH1OweDUUcGAsNng+BYaXB18RmFbof5rnQW2P5mGTB5WfZg6TVjWeRwPk8wljbG/jrJM1hOCXaMILooHTDE7aUwYqqi3wnvuQJJbtVZ9B/e4MhEQbErmpD7B0VT75v1twNYmHxNEEGRsdGvSmZj9XpGmKZUik7UWYJ2wsBz2jbLB0KVfg2qWBk6vYtAhVI0eLpcjd15szP+lilyA5p+8Xguc87PEHtgFg5CAXmnNtDgi2B8+taHfjdVw8XSfXbamfV5cuIZdR2tYnHbueY4C0uQENTm7FxAgMBAAECggEBAKRpQSDY0a71YgxDGJkAtie6oYHfPOQyXeoK4XhTw3UbEelPmFEp3bXutTisVnn3Zs+YblNMkjfTsiNJDsZFOkM2rU6ldYitfJVSqcqqP3yfAKGoEJhEvXSGUu7o26ORcB9XVp22laTq40d74O6L5XW/JEVgluAGJK0pCvUEbv6a+c2C2v5579mCv2TX9DUMswZ3wQYMa1eBmbYExX0rbVIDjMySLSHIlKCJMySELoET/U6rR5M2DirYybz0Ms8iJEVQq1zGe0+F3fJ+i/8NIlhNeSGCMsjL8tZLixKcmnm9Kggejtoj8sMCG/sw04prbHVvZVQWZCrk4B2UkvKBT0ECgYEA4iOKHSVWRrezLgruYT1GelIRuLywRI9dQ6WmzRFsJZfqBcGh5gGZvsTHwejMoQGwAc1iJWEAURULISGvLSruoQsiV6R6qwnAPs33uwln3sdr+xmiQZ6urWyMkx58z6qrdOrBT2A27BDTqmyZVMjeFMmh/rTLiZq4W9aYW2MUiukCgYEAwoCgqnkzy5eWG/VNpbFTJ5Ajid480A7mfQ30Keo9jGtKvbNBc+p5QxmY35RgZbBqGERhQU4vqv7p3Vbs0Cy/+riEH1ZXS4lXrxe0HsbtaKySpkk2jVYKB0/bZxm9cCaIDrqx1n2GoZJd44EPseWI3y6lgNyNN3olC8R5LzI4TUkCgYEAwDaHGbj3uYpBIdymvD5SHnyNlaHEjbHyCyLNQbcSXm3eu9/XFeK8ZNtsE1UmQaBmk1GYn77wfvrNB+9Sr6izRm1pVmd24NSb9IYrkG4xPLny/W7PbMlNHhmK7y3V1snxjKJRh393j3XKBCzks508rxynthDEhITfOSiRPfRcrKkCgYAVDD5WtE76lSjVVbJVX98TxPFTlg0WpsYiekQV1Rdb/rBT3vYSSSleLYLxjujvmCui/KNZodBevL8qCKIP1QfhZd5GVd2UewRWGkaPng9K6IHmJL4WhV0IdYfUnGB3pnPKxEFCaMsM6P3oBKtiIOiy3iVi0Hsnqbwgjd5fURvuoQKBgATbaJOUEieeky5TP8Yw3M3rSFTMIRQm/6/lNMeF+wTuYVuNQXl3kOv+BSTi3tduS/Tv/JzVkufOH1T/mj1CZ/Z5rXibs6YmptN4pvJfnjESiIzzkuWKFsX0Y/KzADVgI7SvtCvtwagyxpoQjF8nutExlV1vUyR9d6A1qDItJquP";

	/** AES密钥 */
	public static final String AES_KEY = "wwTYh0liKdjlIniHh1DNAQ==";

	private volatile static SignatureProperties signatureProperties;

	private SignatureProperties() {

	}

	public static SignatureProperties getInstance() {
		if (signatureProperties == null) {
			synchronized (SignatureProperties.class) {
				if (signatureProperties == null) {
					signatureProperties = new SignatureProperties();
				}
			}
		}
		return signatureProperties;
	}

	public static void main(String[] args) throws Exception {
		String params = "{\"userName\": \"18779141750\", \"password\": \"123456\"}";
		String data = AESUtil.encoder(params, AES_KEY);
		System.out.println("请求参数：" + data);

		String key = RSAUtil.encoderByPublicKey(AES_KEY, SERVER_PUBLIC_KEY);
		System.out.println("密钥：" + key);

		System.out.println();
		System.out.println("---------------------------进行解密----------------------------");
		System.out.println();

		String aesKey = RSAUtil.matchesByPrivateKey(key, SERVER_PRIVATE_KEY);
		System.out.println("AES密钥：" + aesKey);
		String content = AESUtil.matches(data, aesKey);
		System.out.println("请求参数：" + content);
	}

}
