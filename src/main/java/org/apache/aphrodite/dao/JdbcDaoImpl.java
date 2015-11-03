package org.apache.aphrodite.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.aphrodite.dataset.Field;
import org.apache.aphrodite.dataset.PageView;
import org.apache.aphrodite.dataset.Record;
import org.apache.aphrodite.dataset.SqlContext;
import org.apache.aphrodite.exception.DaoException;
import org.apache.aphrodite.util.Constants;
import org.apache.aphrodite.util.DateUtil;
import org.apache.aphrodite.util.ObjectTableUtil;
import org.apache.aphrodite.util.PageViewUtil;
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
            e.printStackTrace();
        }
    }

    public void rollback(){
        try {
            getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void commit(){
        try {
            getConnection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close(){
        try {
            getConnection().close();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e) ;
        }
    }

    public int insert(PageView pv) {
        //tableName
        PreparedStatement pstmt = null;
        try {
            SqlContext sqlContext = PageViewUtil.getSql(PageViewUtil.SqlType.INSERT, pv);
            pstmt = getConnection().prepareStatement(String.format(Constants.INSERT_FORMAT, ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getHead(), sqlContext.getTail()));
            setInsertParam(sqlContext, pv, pstmt);
            int[] results = pstmt.executeBatch();
        } catch (SQLException e) {
            throw new DaoException(e.getMessage(), e);
        } finally {
        	close();
            close(null, pstmt);
        }

        return 0;
    }

    private void setInsertParam(SqlContext sqlContext, PageView pv, PreparedStatement pstmt) throws SQLException {
        List<Record> records = pv.getGrid().getRecords();
        String[] fieldNames = sqlContext.getFieldNames();
        for (int index = 0, max = records.size() - 1; index <= max; index++) {
            Map<String, String> values = records.get(index).getRecordVal();
            for (int j = 1, len = fieldNames.length; j <= len; j++) {
                setParam(pstmt, j, pv.getField(fieldNames[j - 1]), values.get(fieldNames[j - 1]));
            }
            pstmt.addBatch();
        }
    }
    
    private void setSelectParam(SqlContext sqlContext, PageView pv, PreparedStatement pstmt) throws SQLException {
        String[] fieldNames = sqlContext.getFieldNames();
        Map<String, String> values = pv.getForm().getValues(); 
        LOGGER.debug("input params:"+values.toString());
        for (int j = 1, len = fieldNames.length; j <= len; j++) {
            setParam(pstmt, j, pv.getField(fieldNames[j - 1]), values.get(fieldNames[j - 1]));
        }
        pstmt.addBatch();
    }

    private void setParam(PreparedStatement pstmt, int index, Field field, String value) throws SQLException {
        if ("date".equals(field.getDataType())) {
            if (Constants.DATE_FORMAT.equals(field.getFormat())) {
                pstmt.setDate(index, new Date(DateUtil.toDate(value).getTime()));
            } else if (Constants.DATETIME_FORMAT.equals(field.getFormat())) {
                pstmt.setDate(index, new Date(DateUtil.toDateTime(value).getTime()));
            }
        } else if ("bigdecimal".equals(field.getDataType())) {
            pstmt.setBigDecimal(index, new BigDecimal(value));
        } else {
            pstmt.setString(index, getExpression(field,value));
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
        SqlContext sqlContext = PageViewUtil.getSql(PageViewUtil.SqlType.UPDATE, pv);
        try {
            pstmt = getConnection().prepareStatement(String.format(Constants.UPDATE_FORMAT, ObjectTableUtil.toTableFieldFormat(pv.getName()), sqlContext.getHead(), sqlContext.getTail()));
            setInsertParam(sqlContext, pv, pstmt);
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

        //
        return 0;
    }

    public void select(PageView pv) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        SqlContext sqlContext = PageViewUtil.getSql(PageViewUtil.SqlType.SELECT, pv);
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
        	close();
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



    public static void main(String[] args) {
        String format = "INSERT %1s";
        System.out.println(String.format(format, "TABLE"));
    }
}
