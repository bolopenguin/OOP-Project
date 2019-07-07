package europeBanks.spring.util;

import java.lang.reflect.Method;

public abstract class FilterTools {
	
	public static String getFilterString (String field, String value, Object b)  throws Exception{
		
		Method m = b.getClass().getMethod("get"+field.substring(0, 1).toUpperCase()+field.substring(1),null);
		
		String tmp = null;
		if(Double.class.isInstance(m.invoke(b))) {
			tmp = Double.toString((Double)m.invoke(b));
			value = Double.toString(Double.parseDouble(value));
		}
		else if(Integer.class.isInstance(m.invoke(b)))
			tmp = Integer.toString((Integer)m.invoke(b));
		else tmp =(String) m.invoke(b);
		
		return tmp;
	}

}
