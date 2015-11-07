package org.apache.aphrodite.dao;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.apache.aphrodite.dataset.Field;
import org.apache.aphrodite.dataset.Grid;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.dataset.Record;
import org.apache.aphrodite.dataset.SqlContext;
import org.apache.aphrodite.exception.DaoException;
import org.apache.aphrodite.service.JdbcServiceImpl;
import org.apache.aphrodite.util.Constants;
import org.apache.aphrodite.util.DateUtil;
import org.apache.aphrodite.util.ObjectTableUtil;
import org.apache.aphrodite.util.PageViewUtil;
import org.apache.aphrodite.util.SqlType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


/**
 * 类描述：
 *
 * @author: huang.yuewen
 * <p>
 * History:  2015年05月07日 15:33   huang.yuewen   Created.
 */
public class JdbcDaoImpl implements JdbcDao {
	
	private static final Logger LOGGER = LogManager.getLogger(JdbcDaoImpl.class) ; 

    private DataSource dataSource;

    private ThreadLocal<Connection> tlocals = new ThreadLocal<Connection>() ;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void begin(){
        try {
            getConnection().setAutoCommit(false);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e) ;
        }
    }

    public void rollback(){
        try {
            getConnection().rollback();
        } catch (SQLException e) {
        	throw new DaoException(e.getMessage(), e) ;
        }
    }

    public void commit(){
        try {
            getConnection().commit();
        } catch (SQLException e) {
        	throw new DaoException(e.getMessage(), e) ;
        }
    }

    public void close(){
        try {
            getConnection().close();
            tlocals.remove();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e) ;
        }
    }

    public int insert(PageView pv) {
        //tableName
        PreparedStatement pstmt = null;
        try {
            SqlContext sqlContext = PageViewUtil.getSql(SqlType.INSERT, pv);
            String sql = String.format(Constants.INSERT_FORMAT, ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getHead(), sqlContext.getTail()) ;
            LOGGER.debug("insert sql: {}",sql);
            pstmt = getConnection().prepareStatement(sql);
            setUpdateParam(sqlContext, pv, pstmt,SqlType.INSERT);
            int[] results = pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(null, pstmt);
        }

        return 0;
    }
    
    private void setDeleteParam(SqlContext sqlContext, PageView pv, PreparedStatement pstmt) throws SQLException{
    	List<Record> records = pv.getGrid().getRecords();
    	Record record = null ;
        for (int index = 0, max = records.size() - 1; index <= max; index++) {
        	record = records.get(index) ;
        	pstmt.setString(1, record.getRecordVal().get("id"));    
        	pstmt.addBatch();
        }
    }

    private void setUpdateParam(SqlContext sqlContext, PageView pv, PreparedStatement pstmt,SqlType sqlType) throws SQLException {
        List<Record> records = pv.getGrid().getRecords();
        String[] fieldNames = sqlContext.getFieldNames();
        Record record = null ;
        for (int index = 0, max = records.size() - 1; index <= max; index++) {
        	record = records.get(index) ;
        	if(sqlType.equals(SqlType.INSERT)){
        		record.getRecordVal().put("id", UUID.randomUUID().toString()) ;
        	}
        	if(sqlType.name().equals(record.getStatus())){
        		Map<String, String> values = records.get(index).getRecordVal();
        		Field field = null ;
                for (int j = 1, len = fieldNames.length; j <= len; j++) {
                	field = pv.getField(fieldNames[j - 1]) ;
                	field.setValue(values.get(fieldNames[j - 1]));
                    setParam(pstmt, j, field);
                }
                pstmt.addBatch();
        	}            
        }
    }
    
    private void setSelectParam(SqlContext sqlContext, PageView pv, PreparedStatement pstmt) throws SQLException {
        String[] fieldNames = sqlContext.getFieldNames();
        if(fieldNames != null){
        	for (int j = 1, len = fieldNames.length; j <= len; j++) {
                setParam(pstmt, j, pv.getField(fieldNames[j - 1]));
            }
        }
    }

    private void setParam(PreparedStatement pstmt, int index, Field field) throws SQLException {
    	
    	
        if ("date".equals(field.getDataType())) {
            if (Constants.DATE_FORMAT.equals(field.getFormat())) {
                pstmt.setDate(index, new Date(DateUtil.toDate(field.getValue()).getTime()));
            } else if (Constants.DATETIME_FORMAT.equals(field.getFormat())) {
                pstmt.setDate(index, new Date(DateUtil.toDateTime(field.getValue()).getTime()));
            }
        } else if ("bigdecimal".equals(field.getDataType())) {
            pstmt.setBigDecimal(index, new BigDecimal(field.getValue()));
        } else {
            pstmt.setString(index, getExpression(field,field.getValue()));
        }
    }
    
    private String getExpression(Field field, String value){
    	String expression = value ;
    	if("LIKE".equals(field.getOp())){
    		expression = "%"+value+"%" ;
    	}
    	return expression ;
    }

    //
    public int update(PageView pv) {
        PreparedStatement pstmt = null;
        SqlContext sqlContext = PageViewUtil.getSql(SqlType.UPDATE, pv);
        try {
            String sql = String.format(Constants.UPDATE_FORMAT, ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getHead(), sqlContext.getTail()) ;
            LOGGER.debug("update sql: {}",sql) ;
            pstmt = getConnection().prepareStatement(sql) ;
            setUpdateParam(sqlContext, pv, pstmt,SqlType.UPDATE);
            int[] results = pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
        	close();
            close(null, pstmt);
        }
        return 0;
    }


    public int delete(PageView pv) {
    	PreparedStatement pstmt = null;
        SqlContext sqlContext = PageViewUtil.getSql(SqlType.DELETE, pv);
        try {
        	String sql = String.format(Constants.UPDATE_FORMAT, ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getHead(),sqlContext.getTail());
        	LOGGER.debug("delete sql:"+sql);
            pstmt = getConnection().prepareStatement(sql);
            setDeleteParam(sqlContext, pv, pstmt);
            int[] results = pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(null, pstmt);
        }
        return 0;
    }

    public void select(PageView pv) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlContext sqlContext = PageViewUtil.getSql(SqlType.SELECT, pv);
        try {
        	String sql = String.format(Constants.SELECT_FORMAT, sqlContext.getHead(),ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getTail());
        	LOGGER.debug("select sql:"+sql);
            pstmt = getConnection().prepareStatement(sql);
            setSelectParam(sqlContext, pv, pstmt);
            rs = pstmt.executeQuery();
            toPageView(rs, pv);
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            close(rs, pstmt);
        }
    }

    private void toPageView(ResultSet rs, PageView pv) throws SQLException {
        List<Field> fields = pv.getFields();

        List<Record> records = new ArrayList<Record>();
        Record record = null;
        while (rs.next()) {
            record = new Record();
            for (Field field : fields) {
                if ("date".equals(field.getDataType())) {
                    Date date = rs.getDate(ObjectTableUtil.toTableFieldFormat(field.getName()));
                    record.addRecordVal(field.getName(), DateUtil.toString(field.getFormat(), date));
                } else if ("bigdecimal".equals(field.getDataType())) {
                    record.addRecordVal(field.getName(), rs.getBigDecimal(ObjectTableUtil.toTableFieldFormat(field.getName())).toString());
                } else {
                    record.addRecordVal(field.getName(), rs.getString(ObjectTableUtil.toTableFieldFormat(field.getName())));
                }
            }
            records.add(record);
        }
        if(pv.getGrid() == null){
        	pv.setGrid(new Grid());
        }
        pv.getGrid().setRecords(records);
    }

    private void close(ResultSet rs, PreparedStatement pstmt) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException e) {
                throw new DaoException(e.getMessage(), e);
            }
        }
    }

    private Connection getConnection() throws SQLException {
        Connection conn = tlocals.get();
        if (conn == null) {
            conn = dataSource.getConnection();
            tlocals.set(conn);
        }
        return conn;
    }



    public static void main(String[] args) throws NoSuchMethodException, SecurityException {
//        String format = "INSERT %1s";
//        System.out.println(String.format(format, "TABLE"));
    	Object jdbcService = new JdbcServiceImpl() ;
    	Method method = jdbcService.getClass().getMethod("update", PageView.class) ;
    	System.out.println(method.toString()) ;
    }
}
