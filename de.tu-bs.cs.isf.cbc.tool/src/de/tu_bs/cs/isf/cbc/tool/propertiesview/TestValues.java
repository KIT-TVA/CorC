package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import de.tu_bs.cs.isf.cbc.tool.exceptions.NotImplementedException;
import de.tu_bs.cs.isf.cbc.tool.exceptions.SettingsException;

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
            throw new SettingsException("Cannot read test values from '" + filePath + "'.");
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
		instance.setBooleanStr("false, true");
		instance.setByteStr(Byte.MIN_VALUE + ", -1, 0, 1, " + Byte.MAX_VALUE);
		instance.setCharStr("x, 0, \\s, @, ;");
		instance.setIntStr(Integer.MIN_VALUE + ", -1, 0, 1, " + Integer.MAX_VALUE);
		instance.setLongStr(Long.MIN_VALUE + ", -1, 0, 1, " + Long.MAX_VALUE);
		instance.setShortStr(Short.MIN_VALUE + ", -1, 0, 1, " + Short.MAX_VALUE);
		instance.setStringStr("x, xy, 1xy, 1xy@;,");
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
		instance.byteStr = byteStr;
	}

	public String getShortStr() {
		return instance.shortStr;
	}

	public void setShortStr(String shortStr) {
		instance.shortStr = shortStr;
	}

	public String getIntStr() {
		return instance.intStr;
	}

	public void setIntStr(String intStr) {
		instance.intStr = intStr;
	}

	public String getLongStr() {
		return instance.longStr;
	}

	public void setLongStr(String longStr) {
		instance.longStr = longStr;
	}

	public String getCharStr() {
		return instance.charStr;
	}

	public void setCharStr(String charStr) {
		instance.charStr = charStr;
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
		instance.booleanStr = booleanStr;
	}
}
