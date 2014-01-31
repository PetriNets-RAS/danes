/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package FileManager;

import Core.Graph;
import Core.PetriNet;
import Core.Place;
import Core.PrecedenceGraph;
import GUI.MagneticLine;
import GUI.View.DiagramPanel;
import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
    private ArrayList<MagneticLine> listOfMagneticLines;
    private ArrayList<String> bufferRecentFiles;
    private JFileChooser jFileChooser;
    
    public FileManager() {
        this.bufferRecentFiles = new ArrayList<String>();
        this.jFileChooser = new JFileChooser();
    }

    public void saveGraph(Graph graph, Component c) {
        this.graph = graph;
        XMLPetriManager newXML = new XMLPetriManager();
        if (getSelectedFile() == null) {
            jFileChooser = new JFileChooser();
            if (graph instanceof PetriNet) {
                jFileChooser.setAcceptAllFileFilterUsed(false);
                jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Danes PetriNet files", "dpn"));
                jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CoBA PetriNet files", "pn2"));
                jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("CPN Tools PetriNet files", "cpn"));
            } else {
                jFileChooser.addChoosableFileFilter(new FileNameExtensionFilter("PrecedenceGraph", "dpg"));
            }
            int showOpenDialog = jFileChooser.showOpenDialog(c);
            setSelectedFile(jFileChooser.getSelectedFile());
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
            System.out.println("ukladam subor");
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
            String fileName = selectFile.getAbsolutePath();
            XMLPetriManager newXML = new XMLPetriManager();

            File temp = getSelectedFile();
            if ((ff != null && "Danes PetriNet files".equals(ff.getDescription()))
                    || ("dpn".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3))
                    || "cpn".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3))
                    || "pn2".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3)))) {

                System.out.println("pripona:" + selectedFile.getName().substring(selectedFile.getName().length() - 3));
                if ("dpn".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3))) {
                    fileName = fileName.replace(".dpn", "");
                }
                if ("cpn".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3))) {
                    fileName = fileName.replace(".cpn", "");
                }
                if ("pn2".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3))) {
                    fileName = fileName.replace(".pn2", "");
                }
                sufix = ".dpn";
                setSelectedFile(new File(fileName + sufix));
                temp.delete();
                newXML.createPetriXML(this.graph, getSelectedFile(), false);
            } else if (ff != null && "CoBA PetriNet files".equals(ff.getDescription())) {
                if ("pn2".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3))) {
                    fileName = fileName.replace(".pn2", "");
                }
                sufix = ".pn2";
                //setSelectedFile(new File(getSelectedFile().getAbsolutePath() + sufix));
                setSelectedFile(new File(fileName + sufix));
                temp.delete();
                newXML.createPetriXML(this.graph, getSelectedFile(), true);
            } else if (ff != null && "CPN Tools PetriNet files".equals(ff.getDescription())) {
                if ("cpn".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3))) {
                    fileName = fileName.replace(".cpn", "");
                }
                sufix = ".cpn";
                setSelectedFile(new File(fileName + sufix));
                //add copy of clean 
                createCleanCPNNetTo(getSelectedFile());
                temp.delete();
                XMLCPNManager cpnm = new XMLCPNManager();
                cpnm.createPetriXML(this.graph, getSelectedFile());
            }
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

    public void createCleanCPNNetTo(File destination) {
        FileOutputStream outStream = null;
        FileInputStream inStream = null;
        try {
            File cleanNet = new File("cleanCPNNet.xml");
            System.out.println(cleanNet.getAbsolutePath());

            inStream = new FileInputStream(cleanNet);
            outStream = new FileOutputStream(destination);
            byte[] buffer = new byte[1024];

            int length;
            //copy the file content in bytes 
            while ((length = inStream.read(buffer)) > 0) {

                outStream.write(buffer, 0, length);

            }
            inStream.close();
            outStream.close();

        } catch (Exception ex) {
            Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error file copping");
        } finally {
            try {
                if (outStream != null) {
                    outStream.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(FileManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public Graph loadGraph(Component c) {
        
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
        this.bufferRecentFiles.add(selectedFile.getAbsolutePath());
        getInfoAboutFile(inputFile);
        return this.graph;
    }
    
    // load graph from recent files
    public Graph loadGraph(File file){
        selectedFile = file;
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
        this.bufferRecentFiles.add(selectedFile.getAbsolutePath());
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

    public ArrayList<String> getBufferRecentFiles() {
        return bufferRecentFiles;
    }

    public void setBufferRecentFiles(ArrayList<String> bufferRecentFiles) {
        this.bufferRecentFiles = bufferRecentFiles;
    }
    
    
}
