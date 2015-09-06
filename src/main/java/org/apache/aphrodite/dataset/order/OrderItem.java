package org.apache.aphrodite.dataset.order;

import java.math.BigDecimal;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class OrderItem {

    private String id ;

    private String orderId ;

    /**
     * 货品编号  进货单，出货单
     */
    private String itemId ;

    private BigDecimal price ;

    private Integer count ;

    public String getId() {
        return id;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getItemId() {
        return itemId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getCount() {
        return count;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
