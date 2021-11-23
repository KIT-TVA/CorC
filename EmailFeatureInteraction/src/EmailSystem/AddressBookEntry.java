package EmailSystem;

import java.util.ArrayList;

public class AddressBookEntry {
	String alias;
	ArrayList<String> receivers;

	public AddressBookEntry(String alias) {
		super();
		this.alias = alias;
		this.receivers = new ArrayList<String>();
	}

	public void addReceiver(String address) {
		receivers.add(address);
	}

	public String getAlias() {
		return alias;
	}

	public ArrayList<String> getReceivers() {
		return receivers;
	}
}
