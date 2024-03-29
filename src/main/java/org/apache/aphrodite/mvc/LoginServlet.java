package org.apache.aphrodite.mvc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * 
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015 05 07  15:33   huang.yuewen   Created.
 */
public class LoginServlet extends HttpServlet {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private static final Logger LOGGER = LogManager.getLogger(LoginServlet.class) ;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("login in") ;

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

        //�û�У��
       // resp.sendRedirect("");
        resp.setCharacterEncoding("utf-8");
//        resp.setContentType("");
//        resp.sendRedirect("layout_blank_page.html");

        ServletOutputStream sos = resp.getOutputStream() ;
        sos.write("������ӭ��".getBytes("UTF-8"));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
       this.doPost(req,resp);
    }
}
