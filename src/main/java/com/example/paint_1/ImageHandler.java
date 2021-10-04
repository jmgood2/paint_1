package com.example.paint_1;

import javafx.scene.image.Image;

import java.io.*;
import java.util.*;

public class ImageHandler {
    //private Map<String, File> iMap = new HashMap<>();
    private Map<File, Image> iMap = new HashMap<>();
    File openImage = null;

    public ImageHandler(){

    }
    public ImageHandler(File f) throws FileNotFoundException {
        openImage = f;
        addImage(f);
    }
    public ImageHandler(String s) throws FileNotFoundException {
        openImage = new File(s);
        addImage(openImage);
    }

    public void setOpenImage(File f){
        openImage = f;
    }


    public void closeImage(){
        iMap.remove(openImage);
        openImage = null;
    }


    public void addImage(String s) throws FileNotFoundException {
        File f = new File(s);
        openImage = f;
        if (!iMap.containsKey(f)) {
            iMap.put(openImage, new Image(new FileInputStream(openImage)));
        }


    }

    public void addImage(File f) throws FileNotFoundException {
        openImage = f;
        if (!iMap.containsKey(f)){
            iMap.put(openImage, new Image(new FileInputStream(openImage)));
        }

    }

    public Image getImage() {
        return iMap.get(openImage);
    }

    public Image getImage(File f) throws FileNotFoundException {
        if (!iMap.containsKey(f)) iMap.put(f, new Image(new FileInputStream(f)));
        if (openImage != f) openImage = f;
        return iMap.get(openImage);

    }

    public Image getImage(String s) throws FileNotFoundException {
        File f = new File(s);
        if (!iMap.containsKey(f)) iMap.put(f, new Image(new FileInputStream(f)));
        if (openImage != f) openImage = f;
        return iMap.get(openImage);
    }


    public File getOpenImage(){
        return openImage;
    }

}
