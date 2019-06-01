package cityofrevature;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.codehaus.jackson.JsonNode;
import org.glassfish.grizzly.utils.BufferInputStream;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.mule.module.json.JsonData;

public class BackgroundHelper implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		BufferInputStream bis = (BufferInputStream) eventContext.getMessage().getPayload();
		String input = convertStreamToString(bis);
		System.out.println(input);
		eventContext.getMessage().setProperty("diplomas", getDiplomas(input), PropertyScope.SESSION);
		eventContext.getMessage().setProperty("offenses", getOffenses(input), PropertyScope.SESSION);
		return input;
	}

	private String convertStreamToString(java.io.InputStream is) {
		try (java.util.Scanner s = new java.util.Scanner(is)) {
			return s.useDelimiter("\\A").hasNext() ? s.next() : "";
		}
	}

	private List<String> getDiplomas(String input) {
		List<String> s = new ArrayList<String>();
		Matcher m = Pattern.compile("diploma=(?<diploma>[^\\}]*)}").matcher(input);
		while (m.find())
			s.add(m.group("diploma"));
		return s;
	}

	private List<String> getOffenses(String input) {
		List<String> s = new ArrayList<String>();
		Matcher m = Pattern.compile("offense=(?<offense>[^,]*),").matcher(input);
		while (m.find())
			s.add(m.group("offense"));
		return s;
	}

}
