package com.example.paint_1;

import javafx.scene.image.Image;

import java.io.*;
import java.util.*;

public class ImageHandler {
    private Map<String, Image> iMap = new HashMap<>();

    public ImageHandler(){

    }
    public ImageHandler(File f) throws FileNotFoundException {
        Image i = new Image(new FileInputStream(f.getAbsolutePath()));
        iMap.put(f.getAbsolutePath(),i);
    }
    public ImageHandler(String f) throws FileNotFoundException {
        Image i = new Image(new FileInputStream(f));
        iMap.put(f,i);
    }





}
