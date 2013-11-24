/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ConfigManagers;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author MISO
 */
public class ShortcutsManager {
    private static final String CONFIGFILE = "keyConfig.properties";
    private int addPlaceKey;
    private int addResourceKey;
    private int addTransitionKey;
    private int addArcKey;

    public ShortcutsManager(){
        this.readKyesFromConfig();
    }
    
    
    public void writeKeysToConfig(){
        Properties prop = new Properties(); 
    	try {
    		//set the properties value
    		prop.setProperty("addPlaceKey", ""+this.getAddPlaceKey());
    		prop.setProperty("addResourceKey", ""+this.getAddResourceKey());
    		prop.setProperty("addTransitionKey", ""+this.getAddTransitionKey());
    		prop.setProperty("addArcKey", ""+this.getAddArcKey());
 
    		//save properties to project root folder
    		prop.store(new FileOutputStream(CONFIGFILE), null);
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    }
    
    public void readKyesFromConfig(){
        Properties prop = new Properties();
    	try {
               //load a properties file
    		prop.load(new FileInputStream(CONFIGFILE));
 
               //get the property value and print it out
                this.setAddPlaceKey(Integer.parseInt(prop.getProperty("addPlaceKey")));
                this.setAddResourceKey(Integer.parseInt(prop.getProperty("addResourceKey")));
                this.setAddTransitionKey(Integer.parseInt(prop.getProperty("addTransitionKey")));
                this.setAddArcKey(Integer.parseInt(prop.getProperty("addArcKey")));
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    }
    
    /**
     * @return the addPlaceKey
     */
    public int getAddPlaceKey() {
        return addPlaceKey;
    }

    /**
     * @return the addResourceKey
     */
    public int getAddResourceKey() {
        return addResourceKey;
    }

    /**
     * @return the addTransitionKey
     */
    public int getAddTransitionKey() {
        return addTransitionKey;
    }

    /**
     * @return the addArcKey
     */
    public int getAddArcKey() {
        return addArcKey;
    }

    /**
     * @param addPlaceKey the addPlaceKey to set
     */
    public void setAddPlaceKey(int addPlaceKey) {
        this.addPlaceKey = addPlaceKey;
    }

    /**
     * @param addResourceKey the addResourceKey to set
     */
    public void setAddResourceKey(int addResourceKey) {
        this.addResourceKey = addResourceKey;
    }

    /**
     * @param addTransitionKey the addTransitionKey to set
     */
    public void setAddTransitionKey(int addTransitionKey) {
        this.addTransitionKey = addTransitionKey;
    }

    /**
     * @param addArcKey the addArcKey to set
     */
    public void setAddArcKey(int addArcKey) {
        this.addArcKey = addArcKey;
    }
    
        
    @Override
    public String toString(){
        return this.addArcKey+","+this.addPlaceKey+","+this.addResourceKey+","+this.addTransitionKey;
    }
}
