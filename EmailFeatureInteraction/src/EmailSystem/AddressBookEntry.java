package EmailSystem;


public class AddressBookEntry {
	String alias;
	//ArrayList<String> receivers;
	String receiver;

	public AddressBookEntry(String alias) {
		super();
		this.alias = alias;
		//this.receivers = new ArrayList<String>();
	}

	public void addReceiver(String address) {
		receiver = address;
	}

	public String getAlias() {
		return alias;
	}

	public String getReceivers() {
		return receiver;
	}
}
