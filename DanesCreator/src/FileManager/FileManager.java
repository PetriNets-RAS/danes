/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.Graph;
import Core.PetriNet;
import Core.PrecedenceGraph;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author MISO
 */
public class FileManager {

    private Graph graph;
    private File selectedFile;
    private String selectedFileName;
    private String selectedFilePath;

    public FileManager() {
    }

    public void saveGraph(Graph graph, Component c) {
        this.graph = graph;
        XMLPetriManager newXML = new XMLPetriManager();
        if (getSelectedFile() == null) {
            JFileChooser fileChooser = new JFileChooser();
            if (graph instanceof PetriNet) {
                fileChooser.setAcceptAllFileFilterUsed(false);
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Danes PetriNet files", "dpn"));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CoBA PetriNet files", "pn2"));
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CPN Tools PetriNet files", "cpn"));
            } else {
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PrecedenceGraph", "dpg"));
            }
            int showOpenDialog = fileChooser.showOpenDialog(c);
            setSelectedFile(fileChooser.getSelectedFile());
            if (selectedFile == null) {
                return;
            }
            if (!selectedFile.exists()) {
                setSelectedFile(new File(getSelectedFile().getAbsolutePath()));
                try {
                    getSelectedFile().createNewFile();
                } catch (IOException e) {
                    System.out.print("Chyba pri praci so suborom");
                }
            } else {
            }
            if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }
            checkAndSave(getSelectedFile(), null);
        } else {
            checkAndSave(getSelectedFile(), null);
        }
        getInfoAboutFile(getSelectedFile());
    }

    public void saveGraphAs(Graph graph, Component c) {
        this.graph = graph;
        JFileChooser fileChooser = new JFileChooser();
        if (graph instanceof PetriNet) {
            fileChooser.setAcceptAllFileFilterUsed(false);
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Danes PetriNet files", "dpn"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CoBA PetriNet files", "pn2"));
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CPN Tools PetriNet files", "cpn"));
        } else {
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PrecedenceGraph", "dpg"));
        }
        int showOpenDialog = fileChooser.showSaveDialog(c);
        setSelectedFile(fileChooser.getSelectedFile());
        if (selectedFile == null) {
            return;
        }
        if (!selectedFile.exists()) {
            setSelectedFile(new File(getSelectedFile().getAbsolutePath()));
            try {
                getSelectedFile().createNewFile();
            } catch (IOException e) {
                System.out.print("Chyba pri praci so suborom");
            }
        }
        FileFilter ff = fileChooser.getFileFilter();
        if (getSelectedFile() != null) {
            getInfoAboutFile(getSelectedFile());
            if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }
            checkAndSave(getSelectedFile(), ff);
        }
    }

    private void checkAndSave(File selectFile, FileFilter ff) {
        String sufix;
        if (this.graph instanceof PetriNet) {
            sufix = ".dpn";
            XMLPetriManager newXML = new XMLPetriManager();
            if ((getSelectedFile().getName().length() < 5) || (!"dpn".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3)))) {
                File temp = getSelectedFile();
                if ("Danes PetriNet files".equals(ff.getDescription())) {
                    setSelectedFile(new File(getSelectedFile().getAbsolutePath() + sufix));
                    temp.delete();
                    newXML.createPetriXML(this.graph, getSelectedFile(), false);
                } else if ("CoBA PetriNet files".equals(ff.getDescription())) {
                    sufix = ".pn2";
                    setSelectedFile(new File(getSelectedFile().getAbsolutePath() + sufix));
                    temp.delete();
                    newXML.createPetriXML(this.graph, getSelectedFile(), true);
                } else {
                    System.out.println("UKLADAM CPN");
                    sufix = ".cpn";
                    setSelectedFile(new File(getSelectedFile().getAbsolutePath() + sufix));
                    //add copy of clean 
                    createCleanCPNNetTo(getSelectedFile());
                    
                    
                    temp.delete();
                    XMLCPNManager cpnm = new XMLCPNManager();
                    cpnm.createPetriXML(this.graph, getSelectedFile());
                }


            }


            //System.out.println("UKLADAM PN");
        } else {
            sufix = ".dpg";

            XMLPrecedenceManager newXML = new XMLPrecedenceManager();
            if ((getSelectedFile().getName().length() < 5) || (!"dpg".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3)))) {
                File temp = getSelectedFile();
                setSelectedFile(new File(getSelectedFile().getAbsolutePath() + sufix));
                temp.delete();
            }
            newXML.createPrecedenceXML(this.graph, getSelectedFile());
        }
        getInfoAboutFile(selectFile);
    }

    public void createCleanCPNNetTo(File destination){
    	FileOutputStream outStream = null;
        FileInputStream inStream = null;
        try {
            File cleanNet=new File("cleanCPNNet.xml");
            System.out.println(cleanNet.getAbsolutePath());
            
            inStream = new FileInputStream(cleanNet);
            outStream = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];
 
    	    int length;
    	    //copy the file content in bytes 
    	    while ((length = inStream.read(buffer)) > 0){
 
    	    	outStream.write(buffer, 0, length);
 
    	    }
    	    inStream.close();
    	    outStream.close();
            
        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error file copping");
        } finally {
            try {
                if( outStream !=null) outStream.close();               
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public Graph loadGraph(Component c) {
        JFileChooser jFileChooser = new JFileChooser();
        int openShowDialog = jFileChooser.showOpenDialog(c);

        selectedFile = jFileChooser.getSelectedFile();
        if (selectedFile == null) {
            return null;
        }
        File inputFile = new File(selectedFile.getAbsolutePath());
        int x, y;

        if ("dpn".equals(inputFile.getName().substring(inputFile.getName().length() - 3))
                || "pn2".equals(inputFile.getName().substring(inputFile.getName().length() - 3))) {
            XMLPetriManager loader = new XMLPetriManager();
            boolean cobaFile = false;
            if ("pn2".equals(inputFile.getName().substring(inputFile.getName().length() - 3))) {
                cobaFile = true;
            }
            PetriNet p = loader.getPetriNetFromXML(inputFile, cobaFile);
            this.graph = p;
            x = p.getListOfPlaces().get(0).getX();
            y = p.getListOfPlaces().get(0).getY();
        } else if ("cpn".equals(inputFile.getName().substring(inputFile.getName().length() - 3))) {
            XMLCPNManager loader = new XMLCPNManager();
            PetriNet p = loader.getPetriNetFromCPN(inputFile);
            this.graph = p;
            x = p.getListOfPlaces().get(0).getX();
            y = p.getListOfPlaces().get(0).getY();

        } else {
            XMLPrecedenceManager loader = new XMLPrecedenceManager();
            PrecedenceGraph pg = loader.getPrecedenceFromXML(inputFile);
            this.graph = pg;
            x = pg.getListOfNodes().get(0).getX();
            y = pg.getListOfNodes().get(0).getY();
        }
        getInfoAboutFile(inputFile);
        return this.graph;
    }

    private void getInfoAboutFile(File file) {
        setSelectedFileName(file.getName());
        setSelectedFilePath(file.getPath());
    }

    /**
     * @return the selectedFile
     */
    public File getSelectedFile() {
        return selectedFile;
    }

    /**
     * @param selectedFile the selectedFile to set
     */
    public void setSelectedFile(File selectedFile) {
        this.selectedFile = selectedFile;
    }

    /**
     * @return the selectedFileName
     */
    public String getSelectedFileName() {
        return selectedFileName;
    }

    /**
     * @param selectedFileName the selectedFileName to set
     */
    public void setSelectedFileName(String selectedFileName) {
        this.selectedFileName = selectedFileName;
    }

    /**
     * @return the selectedFilePath
     */
    public String getSelectedFilePath() {
        return selectedFilePath;
    }

    /**
     * @param selectedFilePath the selectedFilePath to set
     */
    public void setSelectedFilePath(String selectedFilePath) {
        this.selectedFilePath = selectedFilePath;
    }
}
