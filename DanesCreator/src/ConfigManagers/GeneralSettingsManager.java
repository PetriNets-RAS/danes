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
    private Properties prop;
    private int elementColor1;
    private int elementColor2;
    private int backgroundColor;
    private int fontSize;
    private int windowHeight;
    private int windowWidth;
    private int windowX;
    private int windowY;
    private String[] recentFiles;


    private int     placeWidth;
    private int     placeHeight;
    private String  placePref;
    private int     placeCol1;
    private int     placeCol2;
    
    private int     resourceWidth;
    private int     resourceHeight;
    private String  resourcePref;
    private int     resourceCol1;
    private int     resourceCol2;
    
    private int     transitionWidth;
    private int     transitionHeight;
    private String  transitionPref;
    private int     transitionCol1;
    private int     transitionCol2;
    
    private int     arcWidth;
    private int     arcHeight;
    private String  arcPref;
    private int     arcCol1;
    private int     arcCol2;
    
    private int     nodeWidth;
    private int     nodeHeight;
    private String  nodePref;
    private int     nodeCol1;  
    private int     nodeCol2;
            
           
    public GeneralSettingsManager() {
        this.recentFiles  = new String[5];
        this.prop = new Properties();
        this.readConfig();
    }
    
    public void readConfig(){
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
                
                this.readArcConfiAg();
                this.readNodeConfig();
                this.readPlaceConfig();
                this.readTransitionConfig();
                this.readResourceConfig();
                this.readRecentFile();
                
                
    	} catch (IOException ex) {
    		ex.printStackTrace();
        } catch (NumberFormatException numException){
                numException.getMessage();
        }
    }
    
    public void writeConfig(){
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
                
                this.writeArcConfig();
                this.writeNodeConfig();
                this.writePlaceConfig();
                this.writeResourceConfig();
                this.writeTransitionConfig();
                
                /*
                StringBuilder filesPath = new StringBuilder();
                for (int i = 0; i < this.recentFiles.length; i++) {
                    if(i == (this.recentFiles.length - 1)){
                        filesPath.append(recentFiles[i]);
                    }else{
                        filesPath.append(recentFiles[i]).append(":?");
                    }
                }
                */ 
                //prop.setProperty("recentFiles", filesPath.toString());
                
    		//save properties to project root folder
    		prop.store(new FileOutputStream(CONFIGFILE), null);
 
    	} catch (IOException ex) {
    		ex.printStackTrace();
        }
        
    }
    
    public void writePlaceConfig() {
        this.prop.setProperty("placeWidth", String.valueOf(this.placeWidth));
        this.prop.setProperty("placeHeight", String.valueOf(this.placeHeight));
        this.prop.setProperty("placePref", this.placePref);
        this.prop.setProperty("placeCol1", String.valueOf(this.placeCol1));
        this.prop.setProperty("placeCol2", String.valueOf(this.placeCol2));
    }
    
    public void writeResourceConfig() {
        this.prop.setProperty("resourceWidth", String.valueOf(this.resourceWidth));
        this.prop.setProperty("resourceHeight", String.valueOf(this.resourceHeight));
        this.prop.setProperty("resourcePref", this.resourcePref);
        this.prop.setProperty("resourceCol1", String.valueOf(this.resourceCol1));
        this.prop.setProperty("resourceCol2", String.valueOf(this.resourceCol2));
    }
    
    public void writeTransitionConfig() {
        this.prop.setProperty("transitionWidth", String.valueOf(this.transitionWidth));
        this.prop.setProperty("transitionHeight", String.valueOf(this.transitionHeight));
        this.prop.setProperty("transitionPref", this.transitionPref);
        this.prop.setProperty("transitionCol1", String.valueOf(this.transitionCol1));
        this.prop.setProperty("transitionCol2", String.valueOf(this.transitionCol2));
    }
    
    public void writeArcConfig() {
        this.prop.setProperty("arcWidth", String.valueOf(this.arcWidth));
        this.prop.setProperty("arcHeight", String.valueOf(this.arcHeight));
        this.prop.setProperty("arcPref", this.arcPref);
        this.prop.setProperty("arcCol1", String.valueOf(this.arcCol1));
        this.prop.setProperty("arcCol2", String.valueOf(this.arcCol2));
    }
    
    public void writeNodeConfig() {
        this.prop.setProperty("nodeWidth", String.valueOf(this.nodeWidth));
        this.prop.setProperty("nodeHeight", String.valueOf(this.nodeHeight));
        this.prop.setProperty("nodePref", this.nodePref);
        this.prop.setProperty("nodeCol1", String.valueOf(this.nodeCol1));
        this.prop.setProperty("nodeCol2", String.valueOf(this.nodeCol2));
    }
    
    public void writeRecentFile() {
        for (int i = 0; i < this.recentFiles.length; i++) {
            this.prop.setProperty("recentFile"+i, this.recentFiles[i]);
        }
        try{
            prop.store(new FileOutputStream(CONFIGFILE), null);
        } catch (IOException ex) {
    		ex.printStackTrace();
        }
    }
    
    public void readRecentFile() {
        this.getRecentFiles()[0] = this.prop.getProperty("recentFile0");
        this.getRecentFiles()[1] = this.prop.getProperty("recentFile1");
        this.getRecentFiles()[2] = this.prop.getProperty("recentFile2");
        this.getRecentFiles()[3] = this.prop.getProperty("recentFile3");
        this.getRecentFiles()[4] = this.prop.getProperty("recentFile4");
    }
    
    
    
    public void readPlaceConfig() {
        this.setPlaceCol1(Integer.parseInt(this.prop.getProperty("placeCol1")));
        this.setPlaceCol2(Integer.parseInt(this.prop.getProperty("placeCol2")));
        this.setPlacePref(this.prop.get("placePref").toString());
        this.setPlaceWidth(Integer.parseInt(this.prop.getProperty("placeWidth")));
        this.setPlaceHeight(Integer.parseInt(this.prop.getProperty("placeHeight")));
    }

    public void readTransitionConfig() {
        this.setTransitionCol1(Integer.parseInt(this.prop.getProperty("transitionCol1")));
        this.setTransitionCol2(Integer.parseInt(this.prop.getProperty("transitionCol2")));
        this.setTransitionPref(this.prop.getProperty("transitionPref"));
        this.setTransitionWidth(Integer.parseInt(this.prop.getProperty("transitionWidth")));
        this.setTransitionHeight(Integer.parseInt(this.prop.getProperty("transitionHeight")));
    }

    public void readNodeConfig() {
        this.setNodeCol1(Integer.parseInt(this.prop.getProperty("nodeCol1")));
        this.setNodeCol2(Integer.parseInt(this.prop.getProperty("nodeCol2")));
        this.setNodePref(this.prop.getProperty("nodePref"));
        this.setNodeWidth(Integer.parseInt(this.prop.getProperty("nodeWidth")));
        this.setNodeHeight(Integer.parseInt(this.prop.getProperty("nodeHeight")));
    }

    public void readArcConfiAg() {
        this.setArcCol1(Integer.parseInt(this.prop.getProperty("arcCol1")));
        this.setArcCol2(Integer.parseInt(this.prop.getProperty("arcCol2")));
        this.setArcPref(this.prop.getProperty("arcPref"));
        this.setArcWidth(Integer.parseInt(this.prop.getProperty("arcWidth")));
        this.setArcHeight(Integer.parseInt(this.prop.getProperty("arcHeight")));
    }

    public void readResourceConfig() {
        this.setResourceCol1(Integer.parseInt(this.prop.getProperty("resourceCol1")));
        this.setResourceCol2(Integer.parseInt(this.prop.getProperty("resourceCol2")));
        this.setResourcePref(this.prop.getProperty("resourcePref"));
        this.setResourceWidth(Integer.parseInt(this.prop.getProperty("resourceWidth")));
        this.setResourceHeight(Integer.parseInt(this.prop.getProperty("resourceHeight")));
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

    public int getPlaceWidth() {
        return placeWidth;
    }

    public void setPlaceWidth(int placeWidth) {
        this.placeWidth = placeWidth;
    }

    public int getPlaceHeight() {
        return placeHeight;
    }

    public void setPlaceHeight(int placeHeight) {
        this.placeHeight = placeHeight;
    }

    public String getPlacePref() {
        return placePref;
    }

    public void setPlacePref(String placePref) {
        this.placePref = placePref;
    }

    public int getPlaceCol1() {
        return placeCol1;
    }

    public void setPlaceCol1(int placeCol1) {
        this.placeCol1 = placeCol1;
    }

    public int getPlaceCol2() {
        return placeCol2;
    }

    public void setPlaceCol2(int placeCol2) {
        this.placeCol2 = placeCol2;
    }

    public Properties getProp() {
        return prop;
    }

    public void setProp(Properties prop) {
        this.prop = prop;
    }

    public int getResourceWidth() {
        return resourceWidth;
    }

    public void setResourceWidth(int resourceWidth) {
        this.resourceWidth = resourceWidth;
    }

    public int getResourceHeight() {
        return resourceHeight;
    }

    public void setResourceHeight(int resourceHeight) {
        this.resourceHeight = resourceHeight;
    }

    public String getResourcePref() {
        return resourcePref;
    }

    public void setResourcePref(String resourcePref) {
        this.resourcePref = resourcePref;
    }

    public int getResourceCol1() {
        return resourceCol1;
    }

    public void setResourceCol1(int resourceCol1) {
        this.resourceCol1 = resourceCol1;
    }

    public int getResourceCol2() {
        return resourceCol2;
    }

    public void setResourceCol2(int resourceCol2) {
        this.resourceCol2 = resourceCol2;
    }

    public int getTransitionWidth() {
        return transitionWidth;
    }

    public void setTransitionWidth(int transitionWidth) {
        this.transitionWidth = transitionWidth;
    }

    public int getTransitionHeight() {
        return transitionHeight;
    }

    public void setTransitionHeight(int transitionHeight) {
        this.transitionHeight = transitionHeight;
    }

    public String getTransitionPref() {
        return transitionPref;
    }

    public void setTransitionPref(String transitionPref) {
        this.transitionPref = transitionPref;
    }

    public int getTransitionCol1() {
        return transitionCol1;
    }

    public void setTransitionCol1(int transitionCol1) {
        this.transitionCol1 = transitionCol1;
    }

    public int getTransitionCol2() {
        return transitionCol2;
    }

    public void setTransitionCol2(int transitionCol2) {
        this.transitionCol2 = transitionCol2;
    }

    public int getArcWidth() {
        return arcWidth;
    }

    public void setArcWidth(int arcWidth) {
        this.arcWidth = arcWidth;
    }

    public int getArcHeight() {
        return arcHeight;
    }

    public void setArcHeight(int arcHeight) {
        this.arcHeight = arcHeight;
    }

    public String getArcPref() {
        return arcPref;
    }

    public void setArcPref(String arcPref) {
        this.arcPref = arcPref;
    }

    public int getArcCol1() {
        return arcCol1;
    }

    public void setArcCol1(int arcCol1) {
        this.arcCol1 = arcCol1;
    }

    public int getArcCol2() {
        return arcCol2;
    }

    public void setArcCol2(int arcCol2) {
        this.arcCol2 = arcCol2;
    }

    public int getNodeWidth() {
        return nodeWidth;
    }

    public void setNodeWidth(int nodeWidth) {
        this.nodeWidth = nodeWidth;
    }

    public int getNodeHeight() {
        return nodeHeight;
    }

    public void setNodeHeight(int nodeHeight) {
        this.nodeHeight = nodeHeight;
    }

    public String getNodePref() {
        return nodePref;
    }

    public void setNodePref(String nodePref) {
        this.nodePref = nodePref;
    }

    public int getNodeCol1() {
        return nodeCol1;
    }

    public void setNodeCol1(int nodeCol1) {
        this.nodeCol1 = nodeCol1;
    }

    public int getNodeCol2() {
        return nodeCol2;
    }

    public void setNodeCol2(int nodeCol2) {
        this.nodeCol2 = nodeCol2;
    }
    
    
    
}
