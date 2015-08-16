package org.apache.aphrodite.dataset.sys;

import org.apache.aphrodite.dataset.BaseInfo;

import java.util.Date;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class User extends BaseInfo{

    private String id ;

    private String name ;

    private String code ;

    private String passwd ;

    private int maxTry ;

    private Date lastLogin ;
}
