package com.example.paint_1;

import javafx.scene.image.Image;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.*;

public class ImageHandler {
    // Map holding active Images. Key = File of image, Value = image of File
    private Map<File, Image> iMap;
    //List of currently open Images
    private List<File> openImagesF;
    // Store directory for Temp Image files
    Path tempDir;
    // List of temporary Images - Saves (temporarily) a new image whenever a change to the image is made
    private List<File> tempImages = new ArrayList<>();

    File currentImage;


    public ImageHandler(){
        currentImage = null;
        iMap  = new HashMap<>();
        openImagesF = new ArrayList<>();
        tempDir = FileSystems.getDefault().getPath("/images","/temp");
    }

    public ImageHandler(File f) throws IOException {
        currentImage = null;
        iMap = new HashMap<>();
        openImagesF = new ArrayList<>();
        // Add File and corresponding image to iMap
        this.addNewImage(f);
        // Add Image corresponding to f to currently List of open Images (if not there already), at the front of the List
        openImagesF.add(0, f);
        tempDir = FileSystems.getDefault().getPath("/images","/temp");
    }

    public ImageHandler(Map<File, Image> map){
        currentImage = null;
        // Sets passed Map as iMap
        iMap = map;
        openImagesF = new ArrayList<>();
        tempDir = FileSystems.getDefault().getPath("/images","/temp");
    }

    public ImageHandler(Map<File, Image> map, List<File> l){
        currentImage = null;
        // Sets passed Map as iMap
        iMap = map;
        // Retain opened
        openImagesF = l;
        tempDir = FileSystems.getDefault().getPath("/images","/temp");
    }

    public void addNewImage(File f) throws IOException {
        // Check that given File (and corresponding Image) are not already Mapped
        if (!iMap.containsKey(f)) {
            // Get File Input Stream for given File
            FileInputStream fIS = new FileInputStream(f);
            //Get Image from file location
            Image i = new Image(fIS);
            // Close File input Stream
            fIS.close();
            // Add File location and matching Image to Map

            iMap.put(f, i);
        }
        else System.out.println("Image at \"" + f.getAbsolutePath() + "\" already exists!");
    }

    // Add New File with Image and File Path
    public void addNewImage(Image i, Path p){
        // Createnew File at destination path
        File f = new File(p.toString());
        // Enter File and Image into Map if not already there
        if (!iMap.containsKey(f)) iMap.put(f, i);
        else System.out.println("Image at \"" + f.getAbsolutePath() + "\" already exists!");

    }

    public void addImage(File f) throws IOException {
        if (!iMap.containsKey(f)) this.addNewImage(f);
        //if (openImagesF.contains(f)) openImagesF.remove(iMap.get(f));

        openImagesF.add(0, f);

    }

    public Image getImage(File f) throws IOException {
        // Get image from Image Map
        if (iMap.containsKey(f)) {
            if (!openImagesF.contains(f)) openImagesF.add(0, f);
            return iMap.get(f);
        }
        else {
            System.out.println("Image at \"" + f.getAbsolutePath() + "\" is not in Map!\nAdding to map.");
            addImage(f);
            return iMap.get(f);

        }
    }

    public File getCurrentImage(){
        return currentImage;
    }

    public void setCurrentImage(File i){
        currentImage = i;
    }

    public List<File> getOpenImages(){
        return openImagesF;
    }


    public void closeImage(){
        currentImage = null;
        iMap.remove(openImagesF.get(0));
        openImagesF.remove(0);

    }

    // Create new list of Temp Images based on File f (this is the saved original)
    public void newTempList(File f){
        try {
            //System.out.println("clearing list");
            System.out.println("Empty List? " + tempImages.isEmpty());
            //tempImages.clear();
            System.out.println("adding " + f.getAbsolutePath() + " to list");
            tempImages.add(0, f);
            System.out.println("done");
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public void addTempImage(File f){
        tempImages.add(f);
    }

    public void backTempImage(){
        if (tempImages.size() > 1) {
            tempImages.remove(tempImages.size() -1);
        }
        else System.out.println("Unable to remove Temp Image from List");
    }

    public void clearTempList(){
        tempImages.clear();
    }

    public List<File> getTempList(){
        return tempImages;
    }

    public File getLatestTempImage(){
        return tempImages.get(tempImages.size() -1);
    }

    public File getOriginalImage(){

        try {
            return tempImages.get(0);
        }
        catch (NullPointerException e){
            e.printStackTrace();
        }
        System.out.println("UNABLE TO GET LIST(0)");
        return null;
    }



}
