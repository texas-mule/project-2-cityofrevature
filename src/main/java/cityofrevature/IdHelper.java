package cityofrevature;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

public class IdHelper implements Callable {

	@Override
	public List<String> onCall(MuleEventContext eventContext) throws Exception {

		List<Map<String, Object>> lmso = (List<Map<String, Object>>) eventContext.getMessage().getPayload();
		List<String> ids = new LinkedList<String>();
		if (lmso.size() > 0) {
			String id = (String) ((List<Map<String, Object>>) eventContext.getMessage().getPayload()).get(0).get("Id");
			ids.add(id);
		} else {
			ids.add("");
		}
		return ids;
	}

}
