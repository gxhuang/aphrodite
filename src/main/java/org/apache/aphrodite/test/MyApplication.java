package org.apache.aphrodite.test;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class MyApplication {
    static Logger logger = LogManager.getLogger(MyApplication.class.getName());

    public boolean doIt() {
        logger.entry();   //Log entry to a method
        logger.error("Did it again!");   //Log a message object with the ERROR level
        logger.exit();    //Log exit from a method
        return false;
    }
}
