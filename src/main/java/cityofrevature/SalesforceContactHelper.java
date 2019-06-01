package cityofrevature;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.glassfish.grizzly.utils.BufferInputStream;
import org.mule.api.MuleEventContext;
import org.mule.api.lifecycle.Callable;

import com.sforce.soap.enterprise.sobject.Contact;

public class SalesforceContactHelper implements Callable {

	@Override
	public List<Contact> onCall(MuleEventContext eventContext) throws Exception {
		BufferInputStream payload = (BufferInputStream) eventContext.getMessage().getPayload();
		String stringpayload = convertStreamToString(payload);
		System.out.println(stringpayload);
		String first = getFirst(stringpayload);
		String last = getLast(stringpayload);
		String email = getEmail(stringpayload);
		System.out.println(first);
		System.out.println(last);
		System.out.println(email);
		DomainContact dc = new DomainContact();
		dc.setFirst(first);
		dc.setLast(last);
		dc.setEmail(email);
		List<Contact> lc = new ArrayList<Contact>();
		lc.add(contactFromDomainContact(dc));
		return lc;
	}

	private Contact contactFromDomainContact(DomainContact a) {
		GregorianCalendar gc = new GregorianCalendar();
		if (a.getBirthdate() != null)
			gc.setTime(a.getBirthdate());
		Contact newContact = new Contact();
		newContact.setAccountId(a.getAccountid());
		newContact.setBirthdate(gc);
		newContact.setEmail(a.getEmail());
		newContact.setFirstName(a.getFirst());
		newContact.setId("");
		newContact.setLastName(a.getLast());
		newContact.setPhone(a.getPhone());
		return newContact;
	}

	private String convertStreamToString(InputStream is) {
		try (Scanner s = new Scanner(is)) {
			return s.useDelimiter("\\A").hasNext() ? s.next() : "";
		}
	}

	private String getFirst(String input) {
		return getNamedMatchGroups(input, "\"first\": *\"(?<first>[^\"]*)", "first");
	}

	private String getLast(String input) {
		return getNamedMatchGroups(input, "\"last\": *\"(?<last>[^\"]*)", "last");
	}
	
	private String getEmail(String input) {
		return getNamedMatchGroups(input, "\"email\": *\"(?<email>[^\"]*)", "email");
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
