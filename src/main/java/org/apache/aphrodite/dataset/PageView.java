package org.apache.aphrodite.dataset;

/**
 * 类描述：每个TAB页对应一个PageView,子TAB的查询条件来自你TAB页选中的record，可以通过外键来做关联，也可以通过使用record的查询条件。
 * 每个pageview每条记录被更改后，保回原来的Grid里的对应的记录，这样便可以记录每个修改的数据
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class PageView {

    //对就package+class
    private String id ;

    //是否是查询
    private String action ;

    private String service ;

    private Form form ;

    private Grid grid ;
}
