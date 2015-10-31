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

    private String actioin ;

    private String service ;

    private List<PageView> pvs ;

    public String getActioin() {
        return actioin;
    }

    public void setActioin(String actioin) {
        this.actioin = actioin;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public List<PageView> getPvs() {
        return pvs;
    }

    public void setPvs(List<PageView> pvs) {
        this.pvs = pvs;
    }
}
