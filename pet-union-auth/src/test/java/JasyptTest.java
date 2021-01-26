import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.util.text.BasicTextEncryptor;
import org.junit.Test;

/**
 * <p>
 * Jasypt 测试类
 * </p>
 *
 * @author ：Leo
 * @since ：2021-01-26 14:19
 */
public class JasyptTest {
    @Test
    public void jasyptEncode(){

        BasicTextEncryptor encryptor = new BasicTextEncryptor();
        encryptor.setPassword("bysk");
        String username = encryptor.encrypt("root");
        String password = encryptor.encrypt("123456");
        System.out.println("username---->" + username);
        System.out.println("password---->" + password);
    }

}
