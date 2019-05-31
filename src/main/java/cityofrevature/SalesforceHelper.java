package cityofrevature;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;
import org.mule.streaming.ConsumerIterator;

public class SalesforceHelper implements Callable {

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		ConsumerIterator ci = (ConsumerIterator) eventContext.getMessage().getPayload();
		List<Map<String, Object>>listofhashmaps = new LinkedList<Map<String, Object>>();
		while (ci.hasNext()){
			listofhashmaps.add((Map<String,Object>)ci.next());
		
		}
		return listofhashmaps;
	}

}
