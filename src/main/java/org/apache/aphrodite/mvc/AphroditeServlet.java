package org.apache.aphrodite.mvc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.aphrodite.dataset.Dataset;
import org.apache.aphrodite.util.ApplicationContextUtil;
import org.apache.aphrodite.util.GsonUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class AphroditeServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1930099318146715455L;
	
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
        sis.close();
        String request = new String(baos.toByteArray(),reqCharset) ;
        LOGGER.debug("request message {}",request);

        String output = doService(request) ;

        LOGGER.debug("response message {}",output);

        resp.setCharacterEncoding("UTF-8");
        ServletOutputStream sos = resp.getOutputStream() ;
        sos.write(output.getBytes("UTF-8"));
        sos.close();
    }

    public String doService(String message) {
    	String response = "" ;
    	Dataset dataset = GsonUtil.toObject(message, Dataset.class) ;
    	Object object = ApplicationContextUtil.getApplicationContext().getBean(dataset.getService())  ;
    	try {
			Method method = object.getClass().getMethod(dataset.getAction(), Dataset.class) ;
			method.invoke(object, dataset) ;
			response = GsonUtil.toJson(dataset) ;
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			response = e.getMessage() ;
			LOGGER.error(e.getMessage(),e);
		}
    	
    	return response ;
    } 


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
