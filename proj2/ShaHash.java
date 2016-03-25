import java.security.MessageDigest;
import java.security.Security;
import java.lang.Number;
import java.math.BigInteger;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
public class ShaHash {
    public String cryptMessage(String message) {
        String hash = null;
        try {
            MessageDigest cript = MessageDigest.getInstance("SHA-1");
                          cript.reset();
                          cript.update(message.getBytes("utf8"));
            hash = new BigInteger(1, cript.digest()).toString(16);
            } catch (UnsupportedEncodingException ex)
        {
            ex.printStackTrace();
        }
        catch(NoSuchAlgorithmException ex)
        {
            ex.printStackTrace();
        }
        return hash;
    }
}
