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
public class GeneralSettingsManager {
    private static final String CONFIGFILE = "settings.properties";
    private int elementColor1;
    private int elementColor2;
    private int backgroundColor;
    private int fontSize;
    private int windowHeight;
    private int windowWidth;
    private int windowX;
    private int windowY;
    private String[] recentFiles;


    public GeneralSettingsManager() {
        this.recentFiles  = new String[5];
        this.readConfig();
    }
    
    public void readConfig(){
        Properties prop = new Properties();
    	try {
               //load a properties file
    		prop.load(new FileInputStream(CONFIGFILE));
 
               //get the property value and print it out
                this.setElementColor1(Integer.parseInt(prop.getProperty("elementColor1")));
                this.setElementColor2(Integer.parseInt(prop.getProperty("elementColor2")));
                this.setBackgroundColor(Integer.parseInt(prop.getProperty("backgroundColor")));
                this.setFontSize(Integer.parseInt(prop.getProperty("fontSize")));
                this.setWindowWidth(Integer.parseInt(prop.getProperty("windowWidth")));
                this.setWindowHeight(Integer.parseInt(prop.getProperty("windowHeight")));
                this.setWindowX(Integer.parseInt(prop.getProperty("windowX")));
                this.setWindowY(Integer.parseInt(prop.getProperty("windowY")));
                //String filesPath = prop.getProperty("recentFiles");
//                this.recentFiles = filesPath.split(":?");
                
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } catch (NumberFormatException numException){
                numException.getMessage();
        }
    }
    
    public void writeConfig(){
        Properties prop = new Properties(); 
    	try {
    		//set the properties value
    		prop.setProperty("elementColor1", ""+this.getElementColor1());
    		prop.setProperty("elementColor2", ""+this.getElementColor2());
    		prop.setProperty("backgroundColor", ""+this.getBackgroundColor());
    		prop.setProperty("fontSize", ""+this.getFontSize());
    		prop.setProperty("windowWidth", ""+this.getWindowWidth());
    		prop.setProperty("windowHeight", ""+this.getWindowHeight());
    		prop.setProperty("windowX", ""+this.getWindowX());
    		prop.setProperty("windowY", ""+this.getWindowY());
                
                StringBuilder filesPath = new StringBuilder();
                for (int i = 0; i < this.recentFiles.length; i++) {
                    if(i == (this.recentFiles.length - 1)){
                        filesPath.append(recentFiles[i]);
                    }else{
                        filesPath.append(recentFiles[i]).append(":?");
                    }
                }
                //prop.setProperty("recentFiles", filesPath.toString());
                
    		//save properties to project root folder
    		prop.store(new FileOutputStream(CONFIGFILE), null);
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
    }
    
    public String[] getRecentFiles() {
        return recentFiles;
    }

    public void setRecentFiles(String[] recentFiles) {
        this.recentFiles = recentFiles;
    }
    
    public int getWindowX() {
        return windowX;
    }

    public void setWindowX(int windowX) {
        this.windowX = windowX;
    }

    public int getWindowY() {
        return windowY;
    }

    public void setWindowY(int windowY) {
        this.windowY = windowY;
    }


    public int getWindowHeight() {
        return windowHeight;
    }

    public void setWindowHeight(int windowHeight) {
        this.windowHeight = windowHeight;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public void setWindowWidth(int windowWidth) {
        this.windowWidth = windowWidth;
    }
    
    public int getFontSize() {
        return fontSize;
    }

    public void setFontSize(int fontSize) {
        this.fontSize = fontSize;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public int getElementColor2() {
        return elementColor2;
    }

    public void setElementColor2(int elementColor2) {
        this.elementColor2 = elementColor2;
    }
    

    public int getElementColor1() {
        return elementColor1;
    }

    public void setElementColor1(int elementColor1) {
        this.elementColor1 = elementColor1;
    }
}
