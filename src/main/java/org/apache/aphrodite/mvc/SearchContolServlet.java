package org.apache.aphrodite.mvc;

import org.apache.aphrodite.dataset.Search;
import org.apache.aphrodite.service.SearchControlService;
import org.apache.aphrodite.util.ApplicationContextUtil;
import org.apache.aphrodite.util.GsonUtil;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 *          <p>
 *          History: 2015年05月07日 15:33 huang.yuewen Created.
 */
public class SearchContolServlet extends AphroditeServlet {

	@Override
	public String doService(String message) {

		
		Search search = GsonUtil.toObject(message, Search.class);

		SearchControlService searchControlService = ApplicationContextUtil.getApplicationContext()
				.getBean("searchControlService", SearchControlService.class);
		searchControlService.get(search);

		String json = GsonUtil.toJson(search);

		return json;
	}
}
