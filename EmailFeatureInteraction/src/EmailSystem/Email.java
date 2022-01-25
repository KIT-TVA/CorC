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
	
	private /*@spec_public@*/ boolean isDelivered;
	
	
	
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
	public void constructEmail(int idx) {
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
	public static Email createEmail(Client fromx, String tox, String subjectx, String bodyx) {
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

	Email cloneEmail(Email msg) {
		try {
			return (Email) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Clone not supported");
		}
	}
	
	 /*@
	   @ public normal_behavior
	   @ requires true;
	   @ ensures \result == isEncrypted;
	   @ assignable \nothing;
	   @*/
	 /*@pure@*//*@helper*/ boolean  isEncrypted() {
		return isEncrypted;
	}
	 
	 /*@
	   @ public normal_behavior
	   @ requires true;
	   @ ensures \result == isDelivered;
	   @ assignable \nothing;
	   @*/
	 /*@pure@*/ boolean  isDelivered() {
		return isDelivered;
	}
	 
		/*@
	  @ public normal_behavior
	  @ requires true;
	  @ ensures isEncrypted == value;
	  @ assignable isEncrypted;
	  @*/
	void /*@helper*/ setEmailIsEncrypted(boolean value) {
		isEncrypted = value;
	}
	/*@
	  @ public normal_behavior
	  @ requires true;
	  @ ensures isDelivered == value;
	  @ assignable isDelivered;
	  @*/
	void /*@helper*/setEmailIsDelivered(boolean value) {
		isDelivered = value;
	}
	
	/*@
	  @ public normal_behavior
	  @ requires true;
	  @ ensures encryptionKey == value;
	  @ assignable encryptionKey;
	  @*/
	void /*@helper*/ setEmailEncryptionKey(int value) {
		this.encryptionKey = value;
	}
	
	 /*@
	   @ public normal_behavior
	   @ requires true;
	   @ ensures \result == encryptionKey;
	   @ assignable \nothing;
	   @*/
	 /*@pure@*//*@helper*/ int getEmailEncryptionKey() {
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
	public /*@pure@*/ Client getEmailFrom() {
		return this.from;

	}

	/*@
	@ normal_behavior
	@ requires this.to != null;
	@ ensures \result == this.to;
	@ assignable \nothing;
	@*/
	public /*@pure*/  String getEmailTo() {
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
	public void setEmailBody(String valuex) {
		this.body = valuex;

	}

	/*@
	@ normal_behavior
	@ requires valuex != null;
	@ ensures from == valuex;
	@ assignable from;
	@*/
	public /*@helper*/void setEmailFrom(Client valuex) {
		this.from = valuex;

	}

	/*@
	@ normal_behavior
	@ requires valuex != null;
	@ ensures to == valuex;
	@ assignable to;
	@*/
	public /*@helper*/void setEmailTo(String valuex) {
		this.to = valuex;

	}
}

