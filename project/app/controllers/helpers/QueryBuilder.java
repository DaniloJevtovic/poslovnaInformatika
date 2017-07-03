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
	
	public void buildSimpleQuery(String name, Long value) {
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
		Date queryDate = PomocneOperacije.getEndOfDay(value);
		addParam(name + "LessThanEqualsAnd", queryDate);
	}
	
	public void buildGreaterThanEqualsQuery(String name, Date value) {
		if(value==null) {
			return;
		}
		Date queryDate = PomocneOperacije.getStartOfDay(value);
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
