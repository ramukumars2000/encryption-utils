import java.io.BufferedReader;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
public class EncryptionUtils {

	
	public static void main(String[] args) throws Exception{
		(new EncryptionUtils()).decrypt();
	}
	
	
	public void testModulusGenerattion() throws Exception{
		
		String filename = "C:\\Ram\\encryption\\rsa.public";
		/*
		 * // Read key from file String strKeyPEM = ""; BufferedReader br = new
		 * BufferedReader(new FileReader(filename)); String line; while ((line =
		 * br.readLine()) != null) { strKeyPEM += line + "\n"; } br.close();
		 */
	    
	    String publicKeyPEM = getKeyFromFile(filename);
	    
	    System.out.println(publicKeyPEM);
	    
	    publicKeyPEM = publicKeyPEM.replace("-----BEGIN PUBLIC KEY-----\n", "");
	    publicKeyPEM = publicKeyPEM.replace("-----END PUBLIC KEY-----", "");
	    byte[] encoded = Base64.decodeBase64(publicKeyPEM);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    RSAPublicKey pubKey = (RSAPublicKey) kf.generatePublic(new X509EncodedKeySpec(encoded));
	    System.out.println(pubKey.getModulus());
	    System.out.println(pubKey.getModulus().toString(16));
	    System.out.println(pubKey.getPublicExponent());
		
	}
	
	public String getKeyFromFile(String filename) throws Exception{
		String strKeyPEM = "";
	    BufferedReader br = new BufferedReader(new FileReader(filename));
	    String line;
	    while ((line = br.readLine()) != null) {
	        strKeyPEM += line + "\n";
	    }
	    br.close();
	    
	    return strKeyPEM;
	}
	
	public void decrypt() throws Exception{
		String cipherText = "BLj/DFY8sGTWFONAcjaVO1hFpIpN70UMrjA27MOQVECcUZM0jGK8Rh4HAI5MvWFY\r\n" + 
				"WK9aoPq/h5DVjcdO/XmexJqQbv4KDXHp1ZpAaqg2NZROHYvG9oia7QQ4W0HtL/Yb\r\n" + 
				"QbgzHyzRch8visGGY/e7IihjA3zofqRCyBa/maUp4fI=";
		
		
		String filename = "C:\\Ram\\encryption\\private_key.pem";
		
	    
	    String privateKeyPEM = getKeyFromFile(filename);
	    
	    System.out.println("-----privateKey "+privateKeyPEM);
	    privateKeyPEM = privateKeyPEM.replace("-----BEGIN PRIVATE KEY-----\n", "");
	    privateKeyPEM = privateKeyPEM.replace("-----END PRIVATE KEY-----", "");
	    byte[] encoded = Base64.decodeBase64(privateKeyPEM);
	    KeyFactory kf = KeyFactory.getInstance("RSA");
	    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
	    
	    RSAPrivateKey privateKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
	   
	    
		Cipher cipher = Cipher.getInstance("RSA");
	    cipher.init(Cipher.DECRYPT_MODE, privateKey);
	    String str =  new String(cipher.doFinal(Base64.decodeBase64(cipherText)), "UTF-8");
	    
	    System.out.println("final :"+str);
	}
}
