package com.example.paint_1;

import javafx.scene.image.Image;

import java.io.*;
import java.util.*;

public class ImageHandler {
    private Map<String, Image> iMap = new HashMap<>();

    public ImageHandler(){

    }
    public ImageHandler(File f) throws FileNotFoundException {
        addImage(f.getAbsolutePath());
    }
    public ImageHandler(String f) throws FileNotFoundException {
        addImage(f);
    }


    public void addImage(String s) throws FileNotFoundException {
        Image i = new Image(new FileInputStream(s));
        iMap.put(s,i);
    }

    public Image getImage(File f){
        try {
            return iMap.get(f.getAbsolutePath());
        } catch (NullPointerException e){
            e.printStackTrace();
        }
        return null;


    }



}
