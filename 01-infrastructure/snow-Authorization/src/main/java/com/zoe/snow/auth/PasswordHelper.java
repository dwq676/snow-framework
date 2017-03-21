package com.zoe.snow.auth;

import com.zoe.snow.model.support.user.BaseUserModelSupport;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * PasswordHelper
 *
 * @author Dai Wenqing
 * @date 2016/2/28
 */
public class PasswordHelper {
    private static final int hashIterations = 2;
    //private static RandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
    private static String algorithmName = "md5";
    private static String publicSalt = "_snow";

    public static String encryptPassword(String userName, String password) {
        // user.setSalt(randomNumberGenerator.nextBytes().toHex());
        return new SimpleHash(algorithmName, password, ByteSource.Util.bytes(userName + publicSalt), hashIterations).toHex();
    }

    public static void main(String[] args) {
        System.out.println(encryptPassword("dwq", "dwq"));
        System.out.println(encryptPassword("yw", "yw"));
        System.out.println(encryptPassword("john", "john"));
        System.out.println(encryptPassword("lang", "lang"));
        System.out.println("admin："+encryptPassword("admin", "admin123"));

        System.out.println("wangyangcheng：" + encryptPassword("wangyangcheng", "wyc"));
        System.out.println("chenjia：" + encryptPassword("chenjia", "chenjia"));
        System.out.println("xuyanying：" + encryptPassword("xuyanying", "xuyanying"));
        System.out.println("wumengxin：" + encryptPassword("wumengxin", "wumengxin"));
        System.out.println("qiuxiuxiu：" + encryptPassword("qiuxiuxiu", "qiuxiuxiu"));
        System.out.println("zhoujunyi" + encryptPassword("zhoujunyi", "zhoujunyi"));
        System.out.println("zhangchunrong：" + encryptPassword("zhoujunyi", "zhoujunyi"));
        System.out.println("fangyang：" + encryptPassword("fangyang", "fangyang"));
        System.out.println("yuanwen：" + encryptPassword("yuanwen", "yuanwen"));
        System.out.println("dwq：" + encryptPassword("dwq", "dwq"));

    }
}
