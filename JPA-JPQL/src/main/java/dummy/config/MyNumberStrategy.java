package dummy.config;

import java.io.IOException;
import java.util.regex.Pattern;

import com.google.gson.ToNumberStrategy;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class MyNumberStrategy implements ToNumberStrategy {
 
	@Override
	public Number readNumber(JsonReader in) throws IOException { 
		while (in.hasNext()) { 
			if (in.peek() == JsonToken.NUMBER)  
				return checkPrimitiveType(in.nextString());
			
			if (in.peek() == JsonToken.STRING && in.nextName().equals("age") || (in.peek() == JsonToken.STRING && in.nextName().equals("value")))
				return checkPrimitiveType(in.nextString());
		}
		return null; 
	}
	
	public Number checkPrimitiveType(String value) {
		
		// Integer range from -2147483648 to 2147483647 as per JLS
		if (Pattern.compile("^[+-]?[0-9]{0,9}[0-8]{0,1}$").matcher(value).find()) 
			return Integer.parseInt(value);
		
		// Long range from -9223372036854775808 to 9223372036854775807 as per JLS
		if (Pattern.compile("^[+-]?[0-9]{0,18}[0-8]{0,1}$").matcher(value).find()) 
			return Long.parseLong(value);
		
		// Byte range from -128 to 127 as per JLS
		if (Pattern.compile("^[+-]?[0-9]{0,2}[0-8]{0,1}$").matcher(value).find()) 
			return Byte.parseByte(value);
		
		// Short range from -32768 to 32767 as per JLS
		if (Pattern.compile("^[+-]?[0-9]{0,4}[0-8]{0,1}$").matcher(value).find()) 
			return Short.parseShort(value);
		
		// Float range from 1.40239846e-45f to 3.40282347e+38f as per JLS
		if (Pattern.compile("^[+-]?[0-9]{0,38}\\.[0-9]{0,52}[0-8]{1}$").matcher(value).find()) 
			return Float.parseFloat(value);
		
		// Double range from 4.94065645841246544e-324 to 1.79769313486231570e+308 as per JLS
		if (Pattern.compile("^[+-]?[0-9]{0,289}\\.[0-9]{0,341}[0-4]{1}$").matcher(value).find()) 
			return Double.parseDouble(value);
		
		return null;
	}
}
