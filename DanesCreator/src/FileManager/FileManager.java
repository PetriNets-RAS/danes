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
import java.io.IOException;
import javax.swing.JFileChooser;

/**
 *
 * @author MISO
 */
public class FileManager {
    private Graph graph;
    private File selectedFile;
    private String selectedFileName;
    private String selectedFilePath;
    
    public FileManager(){
    }
    
    public void saveGraph(Graph graph, Component c){
        this.graph = graph;
        XMLPetriManager newXML = new XMLPetriManager();
        if (getSelectedFile() == null) {
            JFileChooser fileChooser = new JFileChooser();
            int showOpenDialog = fileChooser.showOpenDialog(c);
            setSelectedFile(fileChooser.getSelectedFile());
            if (!selectedFile.exists()) {
                setSelectedFile(new File(getSelectedFile().getAbsolutePath()));
                try {
                    getSelectedFile().createNewFile();
                } catch (IOException e) {
                    System.out.print("Chyba pri praci so suborom");
                }
            }
            if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
                return;
            }
            checkAndSave(getSelectedFile());
        } else {
            checkAndSave(getSelectedFile());
        }
        getInfoAboutFile(getSelectedFile());
    }
    
    public void saveGraphAs(Graph graph, Component c){
        this.graph = graph;
        JFileChooser fileChooser = new JFileChooser();
        int showOpenDialog = fileChooser.showSaveDialog(c);
        setSelectedFile(fileChooser.getSelectedFile());
        if (!selectedFile.exists()) {
            setSelectedFile(new File(getSelectedFile().getAbsolutePath()));
            try {
                getSelectedFile().createNewFile();
            } catch (IOException e) {
                System.out.print("Chyba pri praci so suborom");
            }
        }
        getInfoAboutFile(getSelectedFile());
        if (showOpenDialog != JFileChooser.APPROVE_OPTION) {
            return;
        }
        checkAndSave(getSelectedFile());        
    }
    
    private void checkAndSave(File selectFile) {
        String sufix;
        if (this.graph instanceof PetriNet) {
            sufix = ".dpn";
            XMLPetriManager newXML = new XMLPetriManager();

            if ((getSelectedFile().getName().length() < 5) || (!"dpn".equals(selectedFile.getName().substring(selectedFile.getName().length() - 3)))) {
                File temp = getSelectedFile();
                setSelectedFile(new File(getSelectedFile().getAbsolutePath() + sufix));
                temp.delete();
            }

            newXML.createPetriXML(this.graph, getSelectedFile());
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
    
    public Graph loadGraph(Component c){
        JFileChooser jFileChooser = new JFileChooser();
        int openShowDialog = jFileChooser.showOpenDialog(c);

        selectedFile = jFileChooser.getSelectedFile();
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