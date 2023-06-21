package de.tu_bs.cs.isf.cbc.tool.propertiesview;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Behavior implements Serializable {
    private static final long serialVersionUID = 1L;
    private boolean counterExamples;
    private static final String filePath = System.getProperty("user.dir") + "/behavior.ser";
    
    public static Behavior read() {
        try (FileInputStream fileIn = new FileInputStream(filePath);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {
            return (Behavior)objectIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
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

    public Behavior() {
        this.counterExamples = false;
    }

    public boolean getCounterExamples() {
        return counterExamples;
    }

    public void setCounterExamples(boolean counterExamples) {
        this.counterExamples = counterExamples;
    }

    public void save() {
        try (FileOutputStream fileOut = new FileOutputStream(filePath);
             ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)) {
            objectOut.writeObject(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
