package org.apache.aphrodite.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class Log4j2Test {

    private static Logger logger = LogManager.getLogger(Log4j2Test.class);
    public static void main(String[] args) {
        MyApplication myApplication =  new MyApplication();

        logger.entry();
        logger.info("Hello, World!");
        myApplication.doIt();
        logger.error("Hello, World!");
        logger.exit();
    }
}
