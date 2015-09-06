package org.apache.aphrodite.mvc;

import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.service.BaseService;
import org.apache.aphrodite.service.Callback;
import org.apache.aphrodite.util.ApplicationContextUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class AphroditeServlet extends HttpServlet {

    private static final Logger LOGGER = LogManager.getLogger(AphroditeServlet.class) ;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    //字符集+消息
        String reqCharset = req.getCharacterEncoding() ;
        LOGGER.debug("request charset {}",reqCharset);
        ServletInputStream sis = req.getInputStream() ;
        ByteArrayOutputStream baos = new ByteArrayOutputStream() ;
        byte[] data = new byte[1024] ;
        int length = 0 ;
        while((length=sis.read(data)) >0){
            baos.write(data,0,length);
        }
        String message = new String(baos.toByteArray(),reqCharset) ;
        LOGGER.debug("receive message {}",message);

        String service = null ;

        LOGGER.debug("request service {}",service);

        Callback callback = ApplicationContextUtil.getApplicationContext().getBean(service, Callback.class) ;

        BaseService baseService = ApplicationContextUtil.getApplicationContext().getBean("baseService", BaseService.class) ;
        baseService.doService(callback,new Dataset());


    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
