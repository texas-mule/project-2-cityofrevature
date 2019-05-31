package cityofrevature;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;
import org.mule.module.json.JsonData;

public class NameHelper implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		JsonNode[] jna = ((JsonData) eventContext.getMessage().getPayload()).toArray();
		String name = eventContext.getMessage().getProperty("fullname", PropertyScope.SESSION);
		for (JsonNode jn : jna)
			if (jn.get("name").getTextValue().equals(name))
				return jn;
		Map<String,String> hm = new HashMap<String,String>();
		hm.put("nobody", "found");
		return hm;
	}

}