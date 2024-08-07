package sencrypt;

import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.crypto.cipher.CryptoCipher;
import org.apache.commons.crypto.utils.Utils;
import org.openjdk.jmh.annotations.Benchmark;

import javax.crypto.SecretKey;
import java.util.Properties;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Warmup;

/**
 * Bench mark the apache commons crypt library http://commons.apache.org/proper/commons-crypto
 *
 * All messages for CBC are CBC+HMAC.
 * We use a separate key for encryption and authentication.
 */
@Fork(value = 1)
@Warmup(iterations = 3)
public class AesCommons extends AesBase{

    private static byte[] GCM_128_ENCRYPTED;
    private static byte[] CBC_128_ENCRYPTED;
    private static SecretKey ENC_DEFAULT_KEY_128;
    private static SecretKey AUTH_DEFAULT_KEY_256;


    static {
        try {
            ENC_DEFAULT_KEY_128 = AesUtil.getKey(128);

            AUTH_DEFAULT_KEY_256 = AesUtil.getKey(256);

            Properties properties = new Properties();
            CryptoCipher encipher = Utils.getCipherInstance(AesUtil.CBC_CYPHER_TRANSFORM, properties);

            CryptoCipher gcmEncipher = Utils.getCipherInstance(AesUtil.GCM_CYPHER_TRANSFORM, properties);

            GCM_128_ENCRYPTED = AesUtil.encryptGCM(gcmEncipher, ENC_DEFAULT_KEY_128, AesUtil.GCM_IV_SIZE, AesUtil.plaintext);
            CBC_128_ENCRYPTED = AesUtil.encryptCBC(encipher, HmacAlgorithms.HMAC_SHA_256, AUTH_DEFAULT_KEY_256, ENC_DEFAULT_KEY_128, AesUtil.CBC_IV_SIZE, AesUtil.plaintext);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Benchmark
    public void enc_aes128CbcHmacSha256() throws Exception {

        Properties properties = new Properties();
        CryptoCipher encipher = Utils.getCipherInstance(AesUtil.CBC_CYPHER_TRANSFORM, properties);

        AesUtil.encryptCBC(encipher, HmacAlgorithms.HMAC_SHA_256, AUTH_DEFAULT_KEY_256, ENC_DEFAULT_KEY_128, AesUtil.CBC_IV_SIZE, AesUtil.plaintext);

    }

    @Benchmark
    public void enc_aes128GCM() throws Exception {
        Properties properties = new Properties();
        CryptoCipher encipher = Utils.getCipherInstance(AesUtil.GCM_CYPHER_TRANSFORM, properties);

        AesUtil.encryptGCM(encipher, ENC_DEFAULT_KEY_128, AesUtil.GCM_IV_SIZE, AesUtil.plaintext);
    }

    @Benchmark
    public void dec_aes128CbcHmacSha256() throws Exception {

        Properties properties = new Properties();
        CryptoCipher encipher = Utils.getCipherInstance(AesUtil.CBC_CYPHER_TRANSFORM, properties);


        AesUtil.decryptCBC(encipher, HmacAlgorithms.HMAC_SHA_256, AUTH_DEFAULT_KEY_256, ENC_DEFAULT_KEY_128, CBC_128_ENCRYPTED);

    }

    @Benchmark
    public void dec_aes128GCM() throws Exception {
        Properties properties = new Properties();
        CryptoCipher encipher = Utils.getCipherInstance(AesUtil.GCM_CYPHER_TRANSFORM, properties);

        AesUtil.decryptGCM(encipher, ENC_DEFAULT_KEY_128, GCM_128_ENCRYPTED);
    }

}
