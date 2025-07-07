package VRS.Video.Rental.System.utils;

import javax.crypto.KeyGenerator;

import java.security.NoSuchAlgorithmException;
import java.util.Base64;


public class  SecretKeyGenerator {
    public static String generateSecretKey() throws NoSuchAlgorithmException {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance("HmacSHA256");
            return Base64.getEncoder().encodeToString(keyGen.generateKey().getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
