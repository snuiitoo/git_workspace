package common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Base64.Encoder;

public class HelloMvcUtils {
	// 동일한 1234 비밀번호지만 패스워드는 다 달라서 디비에서 일일이 변경해줬다. . . 
	public static void main(String[] args) {
		System.out.println(encrypt("1234", "honggd"));
	}

	/**
	 * SHA256 | SHA512 (SHA1 또는 MD5는 절대 사용하지 말 것)
	 * @param password
	 * @param salt 
	 * @return
	 */
	public static String encrypt(String password, String salt) { // 솔트라는 키워드를 사용
		// 1. 암호화 Hasing
		MessageDigest md = null;
		byte[] encrypted = null;
		try {
			md = MessageDigest.getInstance("SHA-512");
			byte[] input = password.getBytes("utf-8");
			byte[] saltBytes = salt.getBytes("utf-8");
			md.update(saltBytes); // salt 값으로 MessageDigest 객체 갱신
			encrypted = md.digest(input); // MessageDigest 객체에 raw password 전달 및 hashing
			System.out.println(new String(encrypted)); // 깨져있는 텍스트 출력 : 관리할 수 없음
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		// 2. 인코딩처리 - 단순 문자 변환
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(encrypted);
	}

}
