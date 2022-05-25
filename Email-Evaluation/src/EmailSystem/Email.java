package EmailSystem;

import de.tu_bs.cs.isf.cbc.parser.annotations.MDF;
import de.tu_bs.cs.isf.cbc.parser.annotations.MethodReceiver;
import de.tu_bs.cs.isf.cbc.parser.annotations.MutationModifier;
import de.tu_bs.cs.isf.cbc.parser.annotations.SecurityLevel;

public class Email implements IEmail {
	protected @SecurityLevel("low") int id;

	protected @SecurityLevel("low") String subject;

	protected @SecurityLevel("high") @MutationModifier(MDF.MUTABLE) String body;

	protected @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client from;

	protected @SecurityLevel("low") String to;

	static @SecurityLevel("low") int emailCounter = 1;

	public Email(@SecurityLevel("low") int id,
			@SecurityLevel("low") String subject,
			@SecurityLevel("high") String body,
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client from,
			@SecurityLevel("low") String to,
			@SecurityLevel("high") boolean isEncrypted,
			@SecurityLevel("high") int encryptionKey,
			@SecurityLevel("high") boolean signed,
			@SecurityLevel("high") int signkey,
			@SecurityLevel("high") boolean isSignatureVerified
			) {
		this.id = id;
		this.subject = subject;
		this.body = body;
		this.from = from;
		this.to = to;
		this.isEncrypted = isEncrypted;
		this.encryptionKey = encryptionKey;
		this.signed = signed;
		this.signkey = signkey;
		this.isSignatureVerified = isSignatureVerified;
	}

	static @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email createEmail(
			@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client from, 
			@SecurityLevel("low") String to, @SecurityLevel("low") String subject, 
			@SecurityLevel("high") String body) {
		@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg = new Email(emailCounter++, subject, body, from, to, false, 0, false, 0, false);			
		return msg; 							
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public /*@pure@*/ @SecurityLevel("high") boolean isReadable() {
		if (!isEncrypted())
			return true;
		else
			return false;
	}

	private static void printMail__wrappee__Base(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		Util.prompt("ID:  " + msg.id);
		Util.prompt("FROM: " + msg.from);
		Util.prompt("TO: " + msg.to);
		Util.prompt("SUBJECT: " + msg.subject);
		//Util.prompt("IS_READABLE " + msg.isReadable()); //high
		//Util.prompt("BODY: " + msg.getEmailBody());		//high
	}

	private static void printMail__wrappee__Encrypt(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		printMail__wrappee__Base(msg);
		//Util.prompt("ENCRYPTED " + msg.isEncrypted()); //high

	}

	private static void printMail__wrappee__Sign(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		printMail__wrappee__Encrypt(msg);
		//Util.prompt("SIGNED " + msg.isSigned()); //high
		//Util.prompt("SIGNATURE " + msg.getEmailSignKey()); //high
	}

	static void printEmail(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		printMail__wrappee__Sign(msg);
		//Util.prompt("SIGNATURE VERIFIED " + msg.isSignatureVerified()); //high
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email cloneEmail(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email msg) {
		try {
			return (Email) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new Error("Clone not supported");
		}
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public /*@pure@*/ @SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client getEmailFrom() {
		return from;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") int getId() {
		return id;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") String getEmailSubject() {
		return subject;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("low") String getEmailTo() {
		return to;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setEmailBody(@SecurityLevel("high") String value) {
		body = value;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setEmailFrom(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Client value) {
		this.from = value;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setEmailSubject(@SecurityLevel("low") String value) {
		this.subject = value;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setEmailTo(@SecurityLevel("low") String value) {
		to = value;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("high") String getEmailBody() {
		return body;
	}

	protected @SecurityLevel("high") boolean isEncrypted;

	protected @SecurityLevel("high") int encryptionKey;

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	/*@pure@*/
	@SecurityLevel("high") boolean isEncrypted() {
		return isEncrypted;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setEmailIsEncrypted(@SecurityLevel("high") boolean value) {
		isEncrypted = value; //should encrypt or decrypt the body of the message
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setEmailEncryptionKey(@SecurityLevel("high") int value) {
		this.encryptionKey = value;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public /*@pure@*/ @SecurityLevel("high") int getEmailEncryptionKey() {
		return encryptionKey;
	}

	protected @SecurityLevel("high") boolean signed;

	protected @SecurityLevel("high") int signkey;

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setEmailIsSigned(@SecurityLevel("high") boolean value) {
		signed = value;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setEmailSignKey(@SecurityLevel("high") int value) {
		signkey = value;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public @SecurityLevel("high") boolean isSigned() {
		return signed;
	}

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public /*@pure@*/ @SecurityLevel("high") int getEmailSignKey() {
		return signkey;
	}

	// TODO: @Tobias, hier sind zwei SL für ein Objekt deklariert. Welche war gemeint?
	// @MethodReceiver("low") @MutationModifier(MDF.READ) protected @SecurityLevel("high") boolean isSignatureVerified;
	protected @SecurityLevel("high") boolean isSignatureVerified;

	@MethodReceiver(SL = "low", MDF = MDF.READ)
	public /*@pure@*/ @SecurityLevel("high") boolean isSignatureVerified() {
		return isSignatureVerified;
	}

	@MethodReceiver(SL = "low", MDF = MDF.MUTABLE)
	public void setIsSignatureVerified(@SecurityLevel("high") boolean value) {
		this.isSignatureVerified = value;
	}
	
	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public static @SecurityLevel("low") @MutationModifier(MDF.IMMUTABLE) String declassifyEmailBody(@SecurityLevel("high") Email msg) {
		return declassify(msg.body);
	}
	
	private static String declassify(String string) {
		Integer hashCode = string.hashCode();
		return hashCode.toString();
	}

	@MethodReceiver(SL = "low", MDF = MDF.IMMUTABLE)
	public void printMail(@SecurityLevel("low") @MutationModifier(MDF.MUTABLE) Email entry) {
		System.out.println(entry);
	}
}
