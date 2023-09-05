package de.tu_bs.cs.isf.cbc.mutation.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

import de.tu_bs.cs.isf.cbc.mutation.feature.Run;

public class EvalHelper {

	//7
	static ConfigEntry schorr = new ConfigEntry();
	public static void schorrInit() {
		schorr.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TheSchorrWaiteAlgorithm";
		schorr.sourceAndMethod = new String[][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TheSchorrWaiteAlgorithm\\HeapObject.java","void_setMark(boolean)","public void setMark(boolean mark)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TheSchorrWaiteAlgorithm\\HeapObject.java","void_incIndex()","public void incIndex()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TheSchorrWaiteAlgorithm\\HeapObject.java","boolean_isMarked()","public boolean isMarked()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TheSchorrWaiteAlgorithm\\HeapObject.java","boolean_hasNext()","public boolean hasNext()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TheSchorrWaiteAlgorithm\\HeapObject.java","int_getIndex()","public int getIndex()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TheSchorrWaiteAlgorithm\\HeapObject.java","int_getChildCount()","public int getChildCount()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TheSchorrWaiteAlgorithm\\HeapObject.java","HeapObject_getChild(int)","public HeapObject getChild(int pos)"},
		};
	}
	
	//3
	static ConfigEntry dnivra = new ConfigEntry();
	public static void dnivraInit() {
		dnivra.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\DnivraJive\\account";
		dnivra.sourceAndMethod = new String[][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\DnivraJive\\account\\Account.java","void_depositAmount(int)","public void depositAmount(int amt)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\DnivraJive\\account\\Account.java","void_withdrawAmount(int)","public void withdrawAmount(int amt)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\DnivraJive\\account\\Account.java","int_getBalance()","public int getBalance()"},
		};
	}
	
	//6
	static ConfigEntry paycard = new ConfigEntry();
	public static void paycardInit() {
		paycard.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Paycard\\paycard";
		paycard.sourceAndMethod = new String[][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Paycard\\paycard\\LogRecord.java","int_getTransactionID()","public int getTransactionId()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Paycard\\paycard\\LogRecord.java","int_getBalance()","public int getBalance()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Paycard\\paycard\\LogRecord.java","void_setRecord(int)","public void setRecord(int balance)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Paycard\\paycard\\PayCard.java","PayCard_createJuniorCard()","public static PayCard createJuniorCard()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Paycard\\paycard\\PayCard.java","boolean_charge(int)"," public boolean charge(int amount)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Paycard\\paycard\\PayCard.java","boolean_isValid()","public boolean isValid()"},
		};
	}
	
	//19
	static ConfigEntry bank = new ConfigEntry();
	public static void bankInit() {
		bank.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank";
		bank.sourceAndMethod = new String[][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\ATM.java","boolean_proxyExists(int)","private boolean proxyExists (int accountNumber)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\ATM.java","void_insertCard(BankCard)","public void insertCard (BankCard card)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\ATM.java","OfflineAccountProxy_getProxy(int)","private OfflineAccountProxy getProxy (int accountNumber)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\Account.java","int_checkAndWithdraw(int)","public int checkAndWithdraw (int amount)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\Account.java","int_getAccountNumber()","public int getAccountNumber ()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\Account.java","void_addTransaction(Transaction)","protected void addTransaction (Transaction trance)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\PermanentAccount.java","int_accountBalance()","public int accountBalance ()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\PermanentAccount.java","boolean_dailyLimitIsImportant(int)", "private boolean dailyLimitIsImportant (int date)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\Clock.java","Clock_getInstance()","public static Clock getInstance ()"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\CentralHost.java","boolean_accountExists(int)","public boolean accountExists(int accountNumber)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\CentralHost.java","void_createAccount(int)","public void createAccount(int accountNumber)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\CentralHost.java","bank.PermanentAccount_getAccount(int)","public PermanentAccount getAccount(int accountNumber)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\CentralHost.java","BankCard_issueCard(int,int)","public BankCard issueCard(int accountNumber, int pin)"},
//			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\OfflineAccountProxy.java","int_accountBalance()","public int accountBalance ()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\OfflineAccountProxy.java","boolean_newWithdrawalIsPossible(int)","public boolean newWithdrawalIsPossible (int amount)"},
//			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\BankCard.java","boolean_pinIsCorrect(int)","public boolean pinIsCorrect (int pin)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\BankCard.java","void_makeCardInvalid()","public void makeCardInvalid ()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\BankCard.java","boolean_cardIsInvalid()"," public boolean cardIsInvalid ()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\Bank\\bank\\BankCard.java","int_getAccountNumber()","public int getAccountNumber ()"},
		};
	}
	
	//6
	static ConfigEntry bankAccountV2 = new ConfigEntry();
	public static void bankv2Init() {
		bankAccountV2.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\BankAccountV2";
		bankAccountV2.sourceAndMethod = new String[][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\BankAccountV2\\Account.java","boolean_update(int)","boolean update(int x)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\BankAccountV2\\Account.java","boolean_undoUpdate(int)","boolean undoUpdate(int x)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\BankAccountV2\\Account.java","int_calculateInterest()","int calculateInterest()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\BankAccountV2\\Account.java","int_estimatedInterest(int)","int estimatedInterest(int daysLeft)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\BankAccountV2\\Transaction.java","boolean_transfer(Account,Account,int)","public boolean transfer(Account source, Account destination, int amount)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\BankAccountV2\\Transaction.java","boolean_lock(Account,Account)","private static synchronized boolean lock(Account source, Account destination)"},
		};
	}
	
	//5
	static ConfigEntry payCardSPL = new ConfigEntry();
	public static void payCardSPLInit() {
		payCardSPL.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\PayCardSPL";
		payCardSPL.sourceAndMethod = new String [][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\PayCardSPL\\LogRecord.java","int_getBalance()","public int getBalance()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\PayCardSPL\\LogRecord.java","int_getTransactionId()","public int getTransactionId()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\PayCardSPL\\LogRecord.java","void_setRecord(int)","public void setRecord(int balance)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\PayCardSPL\\PayCard.java","PayCard_createJuniorCard()","public static PayCard createJuniorCard()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\PayCardSPL\\PayCard.java","boolean_charge__wrappee__Paycard(int)","private boolean  charge__wrappee__Paycard  (int amount)"},
		};
	}
	
	//10
	static ConfigEntry tobiasSamples = new ConfigEntry();
	public static void tobiasSamplesInit() {
		tobiasSamples.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples";
		tobiasSamples.sourceAndMethod = new String[][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Debug2.java","int_linearSearch(int,int)","public static  int linearSearch( int[] a, int x )"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Debug2.java","int_maxElement(int)","public static  int maxElement( int[] A )"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Debug2.java","int_fac(int)","public static  int fac( int n )"}, //takes ages | 2-3h //10501 aka 175min 1sek
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Debug2.java","int_DutchFlag(int)","public static  int[] DutchFlag( int[] A )"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Debug3.java","int_linearSearch(int,int)","public static int linearSearch(int[] a, int x)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Debug3.java","int_maxElement(int)","public static int maxElement(int[] A)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Debug3.java","int_DutchFlag(int)","public static int[] DutchFlag(int[] A)"}, //takes ages probs: 45min+ // 546, aka 9min 6sek, tippe auf KeY Laufzeitprobleme
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Helper.java","int_powRek(int,int)","public static int powRek(int a, int n)"}, //takes ages 13:30 | 15:00 //6230 aka 103min 50sek
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Helper.java","int_test(Helper)","public int[] test(Helper o)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\TobiasSamples\\Helper2.java","int_fac(int)","public static int fac(int n)"}, //Kein Log
		};
	}
	
	//8
	static ConfigEntry verificationtasksBank = new ConfigEntry();
	public static void verificationtasksBankInit()  {
		verificationtasksBank.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount";
		verificationtasksBank.sourceAndMethod = new String [][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount\\Account.java","boolean_update(int)","boolean update(int x)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount\\Account.java","boolean_undoUpdate(int)","boolean undoUpdate(int x)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount\\Account.java","boolean_credit(int)","boolean credit(int amount)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount\\Account.java","void_lock()","void lock()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount\\Account.java","void_unLock()","void unLock()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount\\Account.java","boolean_isLocked()","boolean isLocked()"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount\\Application.java","void_nextDay()","void nextDay()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\BankAccount\\Application.java","void_nextYear()","void nextYear()"},
		};
	}
	
	//7
	static ConfigEntry verificationtasksExamples7 = new ConfigEntry();
	public static void verificationtasksExamples7Init()  {
		verificationtasksExamples7.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_7";
		verificationtasksExamples7.sourceAndMethod = new String [][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_7\\ArraySearchWhile.java","boolean_search(int,int)","public boolean search(int[] a, int val)"}, //6-7 min
				
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_7\\ArraySumForEach.java","int_sum(int)","public static int sum(int[] array)"}, //40+ min //1min bei neuem Versuch, prob KeY Laufzeit Problem
				
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_7\\StudentA.java","void_addCredits(int)","public void addCredits(int c)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_7\\StudentA.java","void_updateCredits(int)","private void updateCredits(int c)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_7\\StudentA.java","void_changeToMaster()","private void changeToMaster()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_7\\StudentA.java","void_setName(String)","public void setName(String name)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_7\\StudentA.java","java.lang.String_getName()","public String getName()"},
		};
	}
		
	//3
	static ConfigEntry verificationtasksExamples16 = new ConfigEntry();
	public static void verificationtasksExamples16Init()  {
		verificationtasksExamples16.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_16";
		verificationtasksExamples16.sourceAndMethod = new String [][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_16\\PostInc.java","void_postinc()","public void postinc()"},
				
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_16\\Sort.java","int_max(int)","int max(int start)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\Examples_from_Chapter_16\\Sort.java","void_sort()","void sort()"}, //30-45 min //start 19:43 Ende um ca. 20:12 -> 30min
		};
	}
		
	//2
	static ConfigEntry verificationtasksJavaLang = new ConfigEntry();
	public static void verificationtasksJavaLangInit()  {
		verificationtasksJavaLang.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\lang";
		verificationtasksJavaLang.sourceAndMethod = new String [][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\lang\\Math.java","int_max(int,int)","public static int max(int a, int b)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\lang\\Math.java","int_min(int,int)","public static int min(int a, int b)"},
		};
	}
		
	//7
	static ConfigEntry verificationtasksJavaUtil = new ConfigEntry();
	public static void verificationtasksJavaUtilInit()  {
		verificationtasksJavaUtil.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\util";
		verificationtasksJavaUtil.sourceAndMethod = new String [][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\util\\WoCoCollection.java","boolean_isEmpty()","boolean isEmpty()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\util\\WoCoCollection.java","void_clear()","void clear()"},
				
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\util\\ArrayList.java","void_trimToSize()","public void trimToSize()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\util\\ArrayList.java","void_fastRemove(int)","private void fastRemove(int index)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\util\\ArrayList.java","void_rangeCheck(int)","private void rangeCheck(int index)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\util\\ArrayList.java","void_rangeCheckForAdd(int)","private void rangeCheckForAdd(int index)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\java\\util\\ArrayList.java","String_outOfBoundsMsg(int)","private String outOfBoundsMsg(int index)"},
		};
	}
		
	//20
	static ConfigEntry verificationtasksOpenJML = new ConfigEntry();
	public static void verificationtasksOpenJMLInit()  {
		verificationtasksOpenJML.projectPath = "F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml";
		verificationtasksOpenJML.sourceAndMethod = new String [][] {
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\TickTockClock.java","int_getHour()","public int getHour()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\TickTockClock.java","int_getMinute()","public int getMinute()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\TickTockClock.java","int_getSecond()","public int getSecond()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\TickTockClock.java","void_tick()","public void tick()"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\BeanCan.java","void_remove(boolean)","public void remove(boolean color)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\BeanCan.java","boolean_pick_random()","public abstract boolean pick_random()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\BeanCan.java","boolean_play_game()","public boolean play_game()"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\LoopExamples.java","void_setA(int)","public void setA(int [] a)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\Time.java","void_setTime(byte,byte)","public void setTime(byte h, byte m)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\Time.java","short_getTime(byte,short)","public short getTime(byte [] bArray, short offset)"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\Time.java","byte_getMinute()","public byte getMinute()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\Time.java","byte_getHour()","public byte getHour()"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\MaxByElimination.java","int_max(int)","public static int max(int[] a)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\Bag.java","void_add(int)","void add(int elt)"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\Customer.java","void_enter()","void enter()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\Customer.java","void_leave()","void leave()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\Customer.java","void_request()","void request()"},
			
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\CashAmount.java","openjml.CashAmount_negate()","public openjml.CashAmount negate()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\CashAmount.java","int_dollars()","public int dollars()"},
			{"F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\Studies\\verification-tasks\\ReduxProblemSolved\\openjml\\CashAmount.java","int_cents()","public int cents()"},
		};
	}
	
	static void init() {
		schorrInit();
		dnivraInit();
		bankInit();
		paycardInit();
		bankv2Init();
		payCardSPLInit();
		tobiasSamplesInit();
		verificationtasksBankInit();
		verificationtasksExamples7Init();
		verificationtasksExamples16Init();
		verificationtasksJavaLangInit();
		verificationtasksJavaUtilInit();
		verificationtasksOpenJMLInit();
	}
	
	//Insgesammt 103 Sample Methods
	public static void main(String[] args) {
		long startTime = System.nanoTime();
		init();
//		runEvalEntry(schorr); //1 Done
//		runEvalEntry(dnivra); //2 Done
//		runEvalEntry(bank); //4 Done
//		runEvalEntry(paycard); //5 Done
//		runEvalEntry(bankAccountV2); //7 Done
//   	runEvalEntry(payCardSPL); //8 //genauer 492 Sekunden aka 8 Minuten 12 Sekunden letzter Durchlauf bis hier her
//		runEvalEntry(tobiasSamples); //60 - done
//		runEvalEntry(verificationtasksBank); //65
//		runEvalEntry(verificationtasksExamples7); //done 111 - abbruch bei 40+min arraysum, rest < 1 //470 Sekunden, aka 7min 50s, ohne lange Meths, tobi, vBank, ex7
//		runEvalEntry(verificationtasksExamples16); done//2
//		runEvalEntry(verificationtasksJavaLang); //3
//		runEvalEntry(verificationtasksJavaUtil); //3
		runEvalEntry(verificationtasksOpenJML); //7 //429 aka 7min 9s,ohne lange, ex16,lang,util,open
		long endTime = System.nanoTime();
		System.out.println("Gesamtdauer in Nanos: " + (endTime - startTime));
	}

	static void runEvalEntry(ConfigEntry entry) {
		for(String[] entr: entry.sourceAndMethod) {
			try {
				System.gc();
				writeJSONFile(entry.projectPath, entr[0], entr[1], entr[2]);
				Run.main(null);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	static void writeJSONFile(String proj, String source, String method, String methodSignature) throws FileNotFoundException {
		File json = new File("F:\\Homeoffice\\Abschlussarbeiten\\masterarbeit-leon-schaer\\MutationFramework\\src\\MutationFrameworkConfig");
		PrintWriter writer = new PrintWriter(json);
		writer.println("{");
		writer.println("\"MuJava_Home\": \"F:\\\\Homeoffice\\\\Abschlussarbeiten\\\\masterarbeit-leon-schaer\\\\MutationFramework\\\\muJava\\\\muJavaMutantStructure\",");
		writer.println("\"Source\": \"" + source.replace("\\", "\\\\") + "\",");
		writer.println("\"Method\": \"" + method + "\",");
		writer.println("\"MethodSignature\": \"" + methodSignature + "\","); //TODO
		writer.println("\"ProjectPath\": \"" + proj.replace("\\", "\\\\") + "\",");
		writer.println("\"Traditional\": [\"PS\"],");
		writer.println("}");
		writer.flush();
		writer.close();
	}
}
