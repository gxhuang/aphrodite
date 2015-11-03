package org.apache.aphrodite.dataset.sys;

import org.apache.aphrodite.dataset.BaseInfo;

/**
 * 类描述：菜单 总共有两级目录
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class Menu extends BaseInfo{

    private String id ;

    private String url ;

    private String name ;
    
    /**
     * 菜单类型
     */
    private String menuType ;

    private String parentId ;
    
    public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
