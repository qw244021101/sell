package util;

import java.util.Random;

/**
 * 工具类
 */
public class KeyUtil {
    /**
     * 生成唯一主键
     * 防止并发，加锁
     */
    public synchronized static String getUniqueKey(){
        Random random = new Random();
        Integer number = random.nextInt(900000) + 100000;
        return System.currentTimeMillis() + "" + String.valueOf(number);
    }
}
