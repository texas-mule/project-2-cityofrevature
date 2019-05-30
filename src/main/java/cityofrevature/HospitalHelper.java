package cityofrevature;

import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class HospitalHelper implements Callable {

	@SuppressWarnings("unchecked")
	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		return ((List<Map<String, Object>>) eventContext.getMessage().getPayload()).stream()
				.filter(m -> m.get("id") == eventContext.getMessage().getInvocationProperty("id")).toArray()[0];
	}

}
