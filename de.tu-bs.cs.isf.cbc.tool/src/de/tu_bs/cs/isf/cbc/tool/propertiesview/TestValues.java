package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import de.tu_bs.cs.isf.cbc.exceptions.NotImplementedException;
import de.tu_bs.cs.isf.cbc.exceptions.SettingsException;

enum Type {
	BYTE,
	SHORT,
	INT,
	LONG,
	CHAR,
	STRING,
	BOOLEAN
}

public final class TestValues implements Serializable {
	private static TestValues instance;
    private static final long serialVersionUID = 1L;
    private static final String filePath = System.getProperty("user.dir") + "/testdata.ser";
    
    private String byteStr;
    private String shortStr;
    private String intStr;
    private String longStr;
    private String charStr;
    private String stringStr;
    private String booleanStr;
    
    public static TestValues get() throws SettingsException {
    	if (instance == null) {
    		read();
    	}
    	return instance;
    }
    
    public static void read() throws SettingsException {
        try (FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
        	instance = (TestValues)objectIn.readObject();
        } catch (Exception e) {
        	useDefaults();
            //throw new SettingsException("Cannot read test values from '" + filePath + "'.");
        }
    }
    
    public static boolean canRead() {
        try (FileInputStream fileIn = new FileInputStream(filePath);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
                return true;
            } catch (Exception e) {
                return false;
            }
    }

	public static void useDefaults() {
		instance = new TestValues();
		instance.setDefaultBooleanStr();
		instance.setDefaultByteStr();
		instance.setDefaultCharStr();
		instance.setDefaultIntStr();
		instance.setDefaultLongStr();
		instance.setDefaultShortStr();
		instance.setDefaultStringStr();
		instance.save();
	}

    private TestValues() {
    }
    
    public void save() {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(instance);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public String[] getValues(String valueStr){
    	var split = valueStr.split(",");
    	String[] arr = new String[split.length];
    	int i = 0;
    	for (var val : split) {
    		val = val.trim();
    		if (val.equals("\\s")) {
    			val = " ";
    		}
    		arr[i] = val;
    		i++;
    	}
    	return arr;
    }

	public String getByteStr() {
		return instance.byteStr;
	}

	public void setByteStr(String byteStr) {
		if (!checkByteInput(byteStr)) {
			instance.setDefaultByteStr();
		} else {
			instance.byteStr = byteStr;
		}
	}

	public void setDefaultBooleanStr() {
		instance.setBooleanStr("false, true");
	}
	
	public void setDefaultByteStr() {
		instance.setByteStr(Byte.MIN_VALUE + ", -1, 0, 1, " + Byte.MAX_VALUE);
	}

	public void setDefaultShortStr() {
		instance.setShortStr(Short.MIN_VALUE + ", -1, 0, 1, " + Short.MAX_VALUE);
	}

	public void setDefaultIntStr() {
		instance.setIntStr(Integer.MIN_VALUE + ", -1, 0, 1, " + Integer.MAX_VALUE);
	}

	public void setDefaultLongStr() {
		instance.setLongStr(Long.MIN_VALUE + ", -1, 0, 1, " + Long.MAX_VALUE);
	}

	public void setDefaultCharStr() {
		instance.setCharStr("x, 0, \\s, @, ;");
	}

	public void setDefaultStringStr() {
		instance.setStringStr("x, xy, 1xy, 1xy@;,");
	}

	public String getShortStr() {
		return instance.shortStr;
	}

	public void setShortStr(String shortStr) {
		if (!checkShortInput(shortStr)) {
			instance.setDefaultShortStr();
		} else {
			instance.shortStr = shortStr;
		}
	}

	public String getIntStr() {
		return instance.intStr;
	}

	public void setIntStr(String intStr) {
		if (!checkIntegerInput(intStr)) {
			instance.setDefaultIntStr();
		} else {
			instance.intStr = intStr;
		}
	}

	public String getLongStr() {
		return instance.longStr;
	}

	public void setLongStr(String longStr) {
		if (!checkLongInput(longStr)) {
			instance.setDefaultLongStr();
		} else {
			instance.longStr = longStr;
		}
	}

	public String getCharStr() {
		return instance.charStr;
	}

	public void setCharStr(String charStr) {
		if (!checkCharInput(charStr)) {
			instance.setDefaultCharStr();
		} else {
			instance.charStr = charStr;
		}
	}

	public String getStringStr() {
		return instance.stringStr;
	}

	public void setStringStr(String stringStr) {
		instance.stringStr = stringStr;
	}

	public String getBooleanStr() {
		return instance.booleanStr;
	}

	public void setBooleanStr(String booleanStr) {
		if (!checkBooleanInput(booleanStr)) {
			instance.setDefaultBooleanStr();
		} else {
			instance.booleanStr = booleanStr;
		}
	}
	
	private boolean checkBooleanInput(String input) {
		String[] splitted = input.split("\\,");
		for (var s : splitted) {
			if (!s.trim().equals("true") && !s.trim().equals("false")) {
				return false;
			}
		}
		return true;
	}
	
	private boolean checkByteInput(String input) {
		String[] splitted = input.split("\\,");
		for (var s : splitted) {
			try {
				Byte.parseByte(s.trim());
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	private boolean checkShortInput(String input) {
		String[] splitted = input.split("\\,");
		for (var s : splitted) {
			try {
				Short.parseShort(s.trim());
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	private boolean checkIntegerInput(String input) {
		String[] splitted = input.split("\\,");
		for (var s : splitted) {
			try {
				Integer.parseInt(s.trim());
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	private boolean checkLongInput(String input) {
		String[] splitted = input.split("\\,");
		for (var s : splitted) {
			try {
				Long.parseLong(s.trim());
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return true;
	}

	private boolean checkCharInput(String input) {
		String[] splitted = input.split("\\,");
		for (var s : splitted) {
			if (s.trim().length() != 1 && !s.trim().equals("\\s")) {
				return false;
			}
		}
		return true;
	}
}
