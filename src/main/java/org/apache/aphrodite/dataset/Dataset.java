package org.apache.aphrodite.dataset;

import java.util.List;

/**
 * ��������
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015��05��07�� 15:33   huang.yuewen   Created.
 */
public class Dataset {

    private String action ;

    private String service ;

    private List<PageView> pageViews ;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

	public List<PageView> getPageViews() {
		return pageViews;
	}

	public void setPageViews(List<PageView> pageViews) {
		this.pageViews = pageViews;
	}

    
}
