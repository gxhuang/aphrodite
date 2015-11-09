package org.apache.aphrodite.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.aphrodite.dataset.Field;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author huang.yuewen 2015年11月6日下午3:17:49
 *
 */
public abstract class PageUtil {

	private static final String ENTER = "\r\n";

	private static final String TAB = "    ";

	public static <T> String html(String table) throws SQLException {
		StringBuilder html = new StringBuilder();
		List<Field> fields = getFields(table);
		// for(Field field : fields){
		// System.out.println("field name:" + field.getName() + " field type:" +
		// field.getType().getName()) ;
		// }
		int i = 1;
		html.append(formhtml(fields.toArray(new Field[0]), i)).append(gridhtml(fields.toArray(new Field[0]), i));
		return html.toString();
	}

	public static List<Field> getFields(String table) throws SQLException {
		List<Field> fields = new ArrayList<Field>();

		ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
		DataSource ds = cxt.getBean("dataSource", DataSource.class);
		Connection conn = ds.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM " + table + " limit 1");
		ResultSet rs = pstmt.executeQuery();
		ResultSetMetaData rsmd = rs.getMetaData();
		int count = rsmd.getColumnCount();
		for (int i = 1; i <= count; i++) {
			Field field = new Field();
			field.setDataType(rsmd.getColumnTypeName(i));
			field.setName(rsmd.getColumnName(i));

			fields.add(field);
		}
		rs.close();
		pstmt.close();
		conn.close();

		return fields;
	}

	public static String getSpace(int level) {
		StringBuilder space = new StringBuilder();
		for (int i = 0; i < level; i++) {
			space.append(TAB);
		}
		return space.toString();
	}

	public static <T> String formhtml(Field[] fields, int level) {

		StringBuilder formhtml = new StringBuilder();
		formhtml.append(getSpace(1))
				.append("<form id=\"orderForm\" class=\"container-fluid form-horizontal\" dataset=\"form\" role=\"form\">")
				.append(ENTER);
		for (int i = 0, length = fields.length; i < length; i = i + 2) {
			Field field = null;
			formhtml.append(getSpace(level + 1)).append("<div class=\"form-group\">").append(ENTER);
			for (int j = i; j < i + 2 && j < length; j++) {

				field = fields[j];
				formhtml.append(getFormControlHtml(field,level)) ;
			}
			formhtml.append(getSpace(level + 1)).append("</div>").append(ENTER);
		}
		formhtml.append(getSpace(level)).append("</form>").append(ENTER);
		return formhtml.toString();
	}

	private static String getFormControlHtml(Field field, int level) {
		StringBuilder formcontrolhtml = new StringBuilder();
		if(field.getType() == "date"){
			formcontrolhtml.append(getDatetimeHtml(field, level)) ;
		}else if(field.getType() == "search"){
			formcontrolhtml.append(getSearchHtml(field, level)) ;
		}else{
			formcontrolhtml.append(getCommonHtml(field, level)) ;
		}
		return formcontrolhtml.toString();
	}

	private static String getCommonHtml(Field field, int level) {
		StringBuilder controlHtml = new StringBuilder();
		controlHtml.append(getSpace(level + 2)).append("<label class=\"col-sm-1 control-label\">")
				.append(field.getName()).append("</label>").append(ENTER).append(getSpace(level + 2))
				.append("<div class=\"col-sm-5\">").append(ENTER).append(getSpace(level + 3)).append("<input ")
				.append(getId(field)).append(" ").append(getType(field)).append(" ").append("class=\"form-control\"")
				.append(" ").append(getPlaceholder(field)).append(">").append(ENTER).append(getSpace(level + 2))
				.append("</div>").append(ENTER);
		return controlHtml.toString();

	}

	private static String getDatetimeHtml(Field field, int level) {
		StringBuilder datetimeControlHtml = new StringBuilder();
		return datetimeControlHtml.toString();

	}

	private static String getSearchHtml(Field field, int level) {
		StringBuilder searchControlHtml = new StringBuilder();
		return searchControlHtml.toString();
	}

	private static String getId(Field field) {
		String id = "id=\"" + field.getName() + "\"";
		return id;
	}

	private static String getName(Field field) {
		String name = "name=\"" + field.getName() + "\"";
		return name;
	}

	private static String getPlaceholder(Field field) {
		String palceholder = "palceholder=\"" + field.getName() + "\"";
		return palceholder;
	}

	private static String getType(Field field) {
		String type = "type=\"" + field.getDataType() + "\"";
		return type;
	}

	private static String getDataType(Field field) {
		String datatype = "datatype=\"" + field.getDataType().toLowerCase() + "\"";
		return datatype;
	}

	public static <T> String gridhtml(Field[] fields, int level) {
		StringBuilder gridhtml = new StringBuilder();
		gridhtml.append(getSpace(1)).append("<div name=\"grid\">").append(ENTER).append(toolbarhtml(level + 1))
				.append(ENTER).append(getSpace(level + 1)).append(ENTER).append(tablehtml(fields, level + 1))
				.append(ENTER).append(pagenationhtml(level + 1)).append(ENTER).append(getSpace(level)).append("</div>");
		return gridhtml.toString();
	}

	private static String toolbarhtml(int level) {
		StringBuilder toolbarhtml = new StringBuilder();
		toolbarhtml.append(getSpace(level)).append("<div class=\"row\" name=\"toolbar\">").append(ENTER)
				.append(getSpace(level + 1)).append("<div class=\"col-sm-9\">").append(ENTER)
				.append(getSpace(level + 2)).append("<div class=\"btn-group\">").append(ENTER)
				.append(getSpace(level + 3))
				.append("<button type=\"button\" class=\"btn btn-success\" name=\"new\">新增</button>").append(ENTER)
				.append(getSpace(level + 3))
				.append("<button type=\"button\" class=\"btn btn-primary\" name=\"edit\">编辑</button>").append(ENTER)
				.append(getSpace(level + 3))
				.append("<button type=\"button\" class=\"btn btn-warning\" name=\"delete\">查询</button>").append(ENTER)
				.append(getSpace(level + 3))
				.append("<button type=\"button\" class=\"btn btn-info\" name=\"search\">删除</button>").append(ENTER)
				.append(getSpace(level + 3))
				.append("<button type=\"button\" class=\"btn btn-warning\">warning</button>").append(ENTER)
				.append(getSpace(level + 2)).append("</div>").append(ENTER).append(getSpace(level + 1)).append("</div>")
				.append(ENTER).append(getSpace(level)).append("</div>").append(ENTER);
		return toolbarhtml.toString();
	}

	private static String tablehtml(Field[] fields, int level) {
		StringBuilder tablehtml = new StringBuilder();
		tablehtml.append(getSpace(level)).append("<div class=\"panel panel-default\">").append(ENTER)
				.append(getSpace(level + 1)).append("<div class=\"panel-body\">").append(ENTER)
				.append(getSpace(level + 2)).append("<table class=\"table table-hover\">").append(ENTER)
				.append(theadhtml(fields, level + 3)).append(ENTER).append(tbodyhtml(level + 3)).append(ENTER)
				.append(getSpace(level + 2)).append("</table>").append(ENTER).append(getSpace(level + 1))
				.append("</div>").append(ENTER).append(getSpace(level)).append("</div>").append(ENTER);
		return tablehtml.toString();
	}

	private static String theadhtml(Field[] fields, int level) {
		StringBuilder theadhtml = new StringBuilder();
		theadhtml.append(getSpace(level)).append("<thead>").append(ENTER).append(getSpace(level + 1)).append("<tr>")
				.append(ENTER);

		for (Field field : fields) {
			theadhtml.append(getSpace(level + 2)).append("<th ").append(getName(field)).append(" ")
					.append(getDataType(field)).append(">").append(field.getName()).append("</th>").append(ENTER);
		}
		theadhtml.append(getSpace(level + 1)).append("</tr>").append(ENTER).append(getSpace(level)).append("</thead>")
				.append(ENTER);
		return theadhtml.toString();
	}

	private static String tbodyhtml(int level) {
		StringBuilder tbodyhtml = new StringBuilder();
		tbodyhtml.append(getSpace(level)).append("<tbody>").append(ENTER).append(getSpace(level)).append("</tbody>")
				.append(ENTER);
		return tbodyhtml.toString();
	}

	private static String pagenationhtml(int level) {
		StringBuilder pagenationhtml = new StringBuilder();
		pagenationhtml.append(getSpace(level)).append("<ul class=\"pager\">").append(ENTER).append(getSpace(level + 1))
				.append("<li class=\"previous\">").append(ENTER).append(getSpace(level + 2))
				.append("<a href=\"#\">&larr;</a>").append(ENTER).append(getSpace(level + 1)).append("</li>")
				.append(ENTER).append(getSpace(level + 1)).append("<span class=\"info\"></span>").append(ENTER)
				.append(getSpace(level + 1)).append("<li class=\"next\">").append(ENTER).append(getSpace(level + 2))
				.append("<a href=\"#\">&rarr;</a>").append(ENTER).append(getSpace(level + 1)).append("</li>")
				.append(ENTER).append(getSpace(level)).append("</ul>").append(ENTER);
		return pagenationhtml.toString();
	}

	public static void main(String[] args) throws Exception {
		String html = PageUtil.html("DICTIONARY_DETAIL");
		File file = new File("D:\\workspace-luna\\aphrodite\\src\\main\\webapp\\dictDet.html");
		if (file.exists()) {
			throw new RuntimeException("file exists............");
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] data = html.getBytes(Charset.forName("utf-8"));
			fos.write(data);
			fos.flush();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.print(html);

		PageUtil.getFields("DICTIONARY");
	}
}
