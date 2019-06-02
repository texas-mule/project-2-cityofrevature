package cityofrevature;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.api.transport.PropertyScope;

public class SalesforceNewContactHelper implements Callable {

	@Override
	public List<Map<String, Object>> onCall(MuleEventContext eventContext) throws Exception {
		Map<String, Object> newMap = new HashMap<String, Object>();
		newMap.put("AccountId", "");
		newMap.put("Email", eventContext.getMessage().getProperty("email", PropertyScope.SESSION));
		newMap.put("FirstName", eventContext.getMessage().getProperty("first", PropertyScope.SESSION));
		newMap.put("LastName", eventContext.getMessage().getProperty("last", PropertyScope.SESSION));
		newMap.put("Phone", "");
		List<Map<String, Object>> lc = new ArrayList<Map<String, Object>>();
		lc.add(newMap);
		return lc;
	}

}
