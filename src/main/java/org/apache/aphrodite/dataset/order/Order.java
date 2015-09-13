package org.apache.aphrodite.dataset.order;

import org.apache.aphrodite.dataset.BaseInfo;

import java.util.Date;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class Order extends BaseInfo {

    private String id;

    private Date orderDate;

    /**
     * 交货时间
     */
    private Date delDate;

    private String customerCode;

    private String totalAmount;

    /**
     * 订单状态
     */
    private String status ;
}