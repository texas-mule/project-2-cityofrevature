package cityofrevature;

import java.io.InputStream;
import java.util.GregorianCalendar;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.glassfish.grizzly.utils.BufferInputStream;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.sforce.soap.enterprise.sobject.Contact;

public class SalesforceContactHelper implements Callable {

	private Contact contactFromDomainContact(DomainContact a) {
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTime(a.getBirthdate());
		Contact newContact = new Contact();
		newContact.setAccountId(a.getAccountid());
		newContact.setBirthdate(gc);
		newContact.setEmail(a.getEmail());
		newContact.setFirstName(a.getFirst());
		newContact.setId(a.getId());
		newContact.setLastName(a.getLast());
		newContact.setPhone(a.getPhone());
		return newContact;
	}

	private String convertStreamToString(InputStream is) {
		try (Scanner s = new Scanner(is)) {
			return s.useDelimiter("\\A").hasNext() ? s.next() : "";
		}
	}

	@Override
	public Object onCall(MuleEventContext eventContext) throws Exception {
		BufferInputStream payload = (BufferInputStream) eventContext.getMessage().getPayload();
		String stringpayload = convertStreamToString(payload);
		System.out.println(stringpayload);
		String first = getFirst(stringpayload);
		String last = getLast(stringpayload);
		System.out.println(first);
		System.out.println(last);
		DomainContact dc = new DomainContact();
		// dc.setFirst(payload.getFirst());
		return contactFromDomainContact(dc);
	}

	private String getFirst(String input) {
		return getNamedMatchGroups(input, "\"first\": \"(?<first>[^\"]*)", "first");
	}

	private String getLast(String input) {
		return getNamedMatchGroups(input, "\"last\": \"(?<last>[^\"]*)", "last");
	}

	private String getNamedMatchGroups(String input, String pattern, String name) {
		StringBuilder s = new StringBuilder();
		Matcher m = Pattern.compile(pattern).matcher(input);
		while (m.find()) {
			s.append(m.group(name));
		}
		return s.toString();
	}
}
