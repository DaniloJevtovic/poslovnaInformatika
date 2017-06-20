package controllers.helpers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QueryBuilder {
	private StringBuilder queryStringBuilder;
	private ArrayList<Object> paramsList;
	
	public QueryBuilder() {
		queryStringBuilder = new StringBuilder("by");
		paramsList = new ArrayList<Object>();
	}
	
	private void addParam(String name, Object param) {
		queryStringBuilder.append(name);
		paramsList.add(param);
	}
	
	public void buildSimpleQuery(String name, boolean value) {
		addParam(name + "And", value);
	}
	
	public void buildLikeQuery(String name, String value) {
		if(value==null) {
			return;
		}
		String param = value.trim();
		if(param.equals("")) {
			return;
		}
		addParam(name + "LikeAnd", "%" + param.toLowerCase() + "%");
	}
	
	public void buildLessThanEqualsQuery(String name, Number value) {
		if(value==null) {
			return;
		}
		addParam(name + "LessThanEqualsAnd", value);
	}
	
	public void buildGreaterThanEqualsQuery(String name, Number value) {
		if(value==null) {
			return;
		}		
		addParam(name + "GreaterThanEqualsAnd", value);
	}
	
	public void buildLessThanEqualsQuery(String name, Date value) {
		if(value==null) {
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(value);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE,      cal.getMaximum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND,      cal.getMaximum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		Date queryDate = cal.getTime();
		addParam(name + "LessThanEqualsAnd", queryDate);
	}
	
	public void buildGreaterThanEqualsQuery(String name, Date value) {
		if(value==null) {
			return;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(value);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
	    cal.set(Calendar.MINUTE,      cal.getMinimum(Calendar.MINUTE));
	    cal.set(Calendar.SECOND,      cal.getMinimum(Calendar.SECOND));
	    cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		Date queryDate = cal.getTime();
		addParam(name + "GreaterThanEqualsAnd", queryDate);
	}
	
	public String getQuery() {
		//uklanja zadnje And
		if(queryStringBuilder.length() - 3 < 0) {
			return "";
		}
		return queryStringBuilder.toString().substring(0, queryStringBuilder.length() - 3);
	}
	
	public Object[] getParams() {
		return paramsList.toArray();
	}
}
