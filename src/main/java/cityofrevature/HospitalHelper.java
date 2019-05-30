package cityofrevature;

import org.codehaus.jackson.JsonNode;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.module.json.JsonData;

public class HospitalHelper implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		JsonNode[] jna = ((JsonData) eventContext.getMessage().getPayload()).toArray();
		String id = eventContext.getMessage().getInvocationProperty("id");
		for (JsonNode jn : jna)
			if (jn.get("id").getTextValue().equals(id))
				return jn;
		return null;
	}

}
