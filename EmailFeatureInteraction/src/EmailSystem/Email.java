package EmailSystem; 

public  class Email {
	private /*@spec_public@*/ int id;
	private /*@spec_public@*/ static int emailCounter;
	private /*@spec_public@*/ Client from;
	private /*@spec_public@*/ String to;
	private /*@spec_public@*/ String subject;
	private /*@spec_public@*/ String body;
	private /*@spec_public@*/ boolean isEncrypted;
	private /*@spec_public@*/ int encryptionKey;
	private /*@spec_public@*/ boolean signed;
	private /*@spec_public@*/ int signkey;
	private /*@spec_public@*/ boolean isSignatureVerified;
	
	/*@
	@ normal_behavior
	@ requires true;
	@ ensures this.id == idx;
	@ assignable this.id;
	@*/
	public /*@helper@*/ Email(int idx) {
		this.id = idx;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures this.id == idx;
	@ assignable this.id;
	@*/
	public /*@helper@*/ void constructEmail(int idx) {
		this.id = idx;
	}

	public Email() {	
	}
	
	/*@
	@ normal_behavior
	@ requires fromx != null && tox != null && subjectx != null && bodyx != null;
	@ ensures \result.from == fromx && \result.to == tox && \result.subject == subjectx && \result.body == bodyx&& \result.id == \old(Email.emailCounter) && Email.emailCounter == \old(Email.emailCounter) + 1&& \result != null;
	@ assignable Email.emailCounter;
	@*/
	public /*@helper@*/ static Email createEmail(Client fromx, String tox, String subjectx, String bodyx) {
		Email result = null;
		result = new Email(Email.emailCounter++);
		result.setEmailFrom(fromx);
		result.setEmailTo(tox);
		result.setEmailSubject(subjectx);
		result.setEmailBody(bodyx);
		return result;
	}

	private /*@pure@*/ boolean isReadable__wrappee__Keys  () {
		return true;
	}

	 /*@pure@*/ boolean  isReadable() {
		if (!isEncrypted())
			return isReadable__wrappee__Keys();
		else
			return false;
	}

	private static void printMail__wrappee__Keys  (Email msg) {
//		Util.prompt("ID:  " + msg.getId());
//		Util.prompt("FROM: " + msg.getEmailFrom().getId());
//		Util.prompt("TO: " + msg.getEmailTo());
//		Util.prompt("SUBJECT: " + msg.getEmailSubject());
//		Util.prompt("IS_READABLE " + msg.isReadable());
//		Util.prompt("BODY: " + msg.getEmailBody());
	}

	private static void printMail__wrappee__Addressbook  (Email msg) {
		printMail__wrappee__Keys(msg);
		// Util.prompt("ENCRYPTION KEY  "+ msg.getEmailEncryptionKey());
	}

	private static void printMail__wrappee__Forward  (Email msg) {
		printMail__wrappee__Addressbook(msg);
	}

	static void printMail(Email msg) {
		printMail__wrappee__Forward(msg);
	}

	Email cloneEmail(Email msg) {
		try {
			return (Email) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Clone not supported");
		}
	}

	 /*@pure@*/ boolean  isEncrypted() {
		return isEncrypted;
	}

	void setEmailIsEncrypted(boolean value) {
		isEncrypted = value;
	}

	void setEmailEncryptionKey(int value) {
		this.encryptionKey = value;
	}

	 /*@pure@*/ int getEmailEncryptionKey() {
		return encryptionKey;
	}

	void setEmailIsSigned(boolean value) {
		signed = value;
	}

	void setEmailSignKey(int value) {
		signkey = value;
	}

	boolean isSigned() {
		return signed;
	}

	 /*@pure@*/ int getEmailSignKey() {
		return signkey;
	}

	 /*@pure@*/ boolean isSignatureVerified() {
		return isSignatureVerified;
	}



	void setIsSignatureVerified(boolean value) {
		this.isSignatureVerified = value;
	}

	/*@
	@ normal_behavior
	@ requires valuex != null;
	@ ensures this.subject == valuex;
	@ assignable this.subject;
	@*/
	public /*@helper@*/ void setEmailSubject(String valuex) {
		this.subject = valuex;

	}

	/*@
	@ normal_behavior
	@ requires this.body != null;
	@ ensures \result == this.body;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ String getEmailBody() {
		return this.body;

	}

	/*@
	@ normal_behavior
	@ requires this.from != null;
	@ ensures \result == this.from;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ Client getEmailFrom() {
		return this.from;

	}

	/*@
	@ normal_behavior
	@ requires this.to != null;
	@ ensures \result == this.to;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ String getEmailTo() {
		return this.to;

	}

	/*@
	@ normal_behavior
	@ requires this.subject != null;
	@ ensures \result == this.subject;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ String getEmailSubject() {
		return this.subject;

	}

	/*@
	@ normal_behavior
	@ requires true;
	@ ensures \result == this.id;
	@ assignable \nothing;
	@*/
	public /*@helper@*/ int getId() {
		return this.id;

	}

	/*@
	@ normal_behavior
	@ requires valuex != null;
	@ ensures this.body == valuex;
	@ assignable this.body;
	@*/
	public /*@helper@*/ void setEmailBody(String valuex) {
		this.body = valuex;

	}

	/*@
	@ normal_behavior
	@ requires valuex != null;
	@ ensures this.from == valuex && this.from != null;
	@ assignable this.from;
	@*/
	public /*@helper@*/ void setEmailFrom(Client valuex) {
		this.from = valuex;

	}

	/*@
	@ normal_behavior
	@ requires valuex != null;
	@ ensures this.to == valuex;
	@ assignable this.to;
	@*/
	public /*@helper@*/ void setEmailTo(String valuex) {
		this.to = valuex;

	}
}

