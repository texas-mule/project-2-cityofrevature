package cityofrevature;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.mule.module.json.JsonData;

public class NametoIdHelper implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		System.out.println("Line 16");
		JsonNode[] jna = ((JsonData) eventContext.getMessage().getPayload()).toArray();

		String name = eventContext.getMessage().getProperty("fullname", PropertyScope.SESSION);
		String id;
		for (JsonNode jn : jna) {
			System.out.println(jn);
			System.out.println(jn.get("name"));
			System.out.println(jn.get("name").getTextValue());
			System.out.println(jn.get("id"));
			System.out.println(name);
			System.out.println("Line 26");
			if (jn.get("name").getTextValue().equals(name)) {
				if (jn.has("id"))
					return jn.get("id").getTextValue();
				if (jn.has("workerid"))
					return jn.get("workerid").getTextValue();
				if (jn.has("criminalid"))
					return jn.get("criminalid").getTextValue();
			}
		}
		Map<String, String> hm = new HashMap<String, String>();
		hm.put("nobody", "found");
		return "";
	}

}
