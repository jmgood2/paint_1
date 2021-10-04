package com.example.paint_1;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;


public class PainT extends Application {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void start(Stage stage) throws IOException {
        stage.setTitle("Paint (t)");

        /**************************
         * IMAGEHANDLER
         ****************** ***/

        ImageHandler iHandler = new ImageHandler();

        /**************************
         * MENU Bars
         ****************** ***/

        /**** Main Menu *******/
        // File menu
        Menu mFile = new Menu("File");
        MenuItem openI = new MenuItem("Open Image");
        openI.setAccelerator(KeyCombination.keyCombination("Shortcut+Shift+N"));
        MenuItem saveI = new MenuItem("Save Image");
        saveI.setAccelerator(KeyCombination.keyCombination("Shortcut+S"));
        MenuItem saveIAs= new MenuItem("Save Image As");
        saveIAs.setAccelerator(KeyCombination.keyCombination("Shortcut+Shift+S"));
        MenuItem closeI = new MenuItem("Close Image");
        MenuItem exit = new MenuItem("Close");
        mFile.getItems().add(openI);
        mFile.getItems().add(saveI);
        mFile.getItems().add(saveIAs);
        mFile.getItems().add(closeI);
        mFile.getItems().add(exit);

        //Edit menu
        Menu mEdit = new Menu("Edit");
        MenuItem placeholder = new MenuItem("Coming Soon");
        placeholder.setDisable(true);
        mEdit.getItems().add(placeholder);

        // Help menu
        Menu mHelp = new Menu("Help");
        MenuItem rNotes = new MenuItem("Release Notes");
        MenuItem about = new MenuItem("About");
        mHelp.getItems().add(rNotes);
        mHelp.getItems().add(about);

        // Menu bar
        MenuBar mBar = new MenuBar(mFile, mEdit, mHelp);

        /********* Secondary Menu ***/

        /**************************
         *  SCENE Setup
         ****************** ***/
        BorderPane bRoot = new BorderPane();
        Scene baseScene = new Scene(bRoot, 1000, 800);


        /**************************
         *  Canvas Setup
         ****************** ***/

        Canvas canvas = new Canvas(baseScene.getWidth(),
                baseScene.getHeight());

        GraphicsContext gContent = canvas.getGraphicsContext2D();


        Group group = new Group(canvas);
        group.setVisible(false);




        /**************************
         *  LAYOUT Setup
         ****************** ***/

        HBox hB1 = new HBox(mBar);
        bRoot.setTop(hB1);
        bRoot.setCenter(group);




        /**************************
         * IMAGE View
         ****************** ***/

        ImageView iView = new ImageView();
        iView.setVisible(false);
        iView.setX(5);
        iView.setY(5);
        iView.setFitWidth(600);
        iView.setPreserveRatio(true);






        /**************************
         * SCENE creation and update
         ****************** ***/

        stage.setScene(baseScene);

        stage.show();



        /**************************
         * Menu Item Actions
         ****************** ***/

        // OPEN image
        openI.setOnAction(
                aE -> {
                    File file = openImage(stage);
                    if (file != null){
                        if (iHandler.getImage() != null) {
                            gContent.clearRect(0,
                                    0,
                                    iHandler.getImage().getWidth(),
                                    iHandler.getImage().getHeight());
                        }
                        try {
                            iHandler.addImage(file);
                            Image i = iHandler.getImage();
                            gContent.drawImage(i,
                                    0,
                                    0);
                            group.setVisible(true);

                        } catch (NullPointerException | FileNotFoundException ex) {
                            ex.printStackTrace();
                            iHandler.closeImage();
                            group.setVisible(false);
                        }
                    }
                    else System.out.println("File is NULL! -- PainT.java - ln 113");
                }
        );

        // SAVE image
        saveI.setOnAction(
                aE -> {
                    File file = saveImage(stage);
                    String fType = file.getName().substring(
                            file.getName().lastIndexOf('.') + 1);
                    System.out.println("File extension of " + file.getAbsolutePath() + " is " + fType);
                    if (file == null){
                        //saveImageAs(stage);
                        saveImage(stage);
                    }
                    else {
                        try {
                            FileInputStream fIStream = new FileInputStream(iHandler.getOpenImage().getAbsolutePath());
                            BufferedImage bI = ImageIO.read(fIStream);
                            fIStream.close();

                            BufferedImage newImage = new BufferedImage(bI.getWidth(),
                                    bI.getHeight(),
                                    BufferedImage.TYPE_INT_RGB);
                            newImage.createGraphics().drawImage(bI,
                                    0,
                                    0,
                                    Color.WHITE,
                                    null);

                            FileOutputStream fOStream = new FileOutputStream(file.getAbsolutePath());
                            ImageIO.write(newImage,
                                    fType,
                                    fOStream);
                            fOStream.close();


                        } catch (ArrayIndexOutOfBoundsException | IOException e) {
                            e.printStackTrace();
                        }
                    }

                }
        );

        // CLOSE image
        closeI.setOnAction(
                aE -> {
                    gContent.clearRect(0,
                            0,
                            iHandler.getImage().getWidth(),
                            iHandler.getImage().getHeight());
                    iHandler.closeImage();
                    group.setVisible(false);
                }
        );

        // EXIT program
        exit.setOnAction(
                aE -> {
                    System.exit(0);
                }
        );

        // OPEN release notes
        rNotes.setOnAction(
                aE -> {
                    File file = new File("src/main/resources/com/example/paint_1/Release-Notes.md");
                    if (file.exists()){
                        System.out.println("file exists");
                        Desktop desktop = Desktop.getDesktop();
                        try {
                            desktop.open(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    else System.out.println("file does not exist");
                }
        );

        // POPUP about page
        about.setOnAction(
                aE -> {
                    aboutPop();
                }
        );

        /**************************
         * IMAGE
         ****************** ***/

        // Image MAP
        Map iMap = new HashMap();




    }

    /**************************☺☺
     * METHODS
     ****************** ***/

    /** openImage method
     * Launches a FileChooser explorer for locating an image
     * Images filtered by type
     * @param stage
     * @return File
     */
    public static File openImage(Stage stage){
        FileChooser fChooser = new FileChooser();
        fChooser.setTitle("Open Image");
        fChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("TIFF", "*.tif", "*.tiff")
        );
        return fChooser.showOpenDialog(stage);

    }

    public static File saveImage(Stage stage){
        FileChooser fChooser = new FileChooser();
        fChooser.setTitle("Save Image");
        fChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg", "*.jpeg"),
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("BMP", "*.bmp"),
                new FileChooser.ExtensionFilter("TIFF", "*.tif", "*.tiff")
        );
        return fChooser.showSaveDialog(stage);

    }




    /** aboutPop method
     * Opens a new Scene with About information
     */
    public static void aboutPop(){
        Stage aPop = new Stage();
        aPop.setTitle("About");

        Label aLabel = new Label("Pain(T) Alpha Build 1.0\n9/9/2021\n\nJonathan Good\nCS 250\n");

        VBox vB = new VBox(10);
        vB.getChildren().addAll(aLabel);
        vB.setAlignment(Pos.CENTER);

        Scene aScene = new Scene (vB, 100, 100);
        aPop.setScene(aScene);
        aPop.showAndWait();
    }


    /** main method
     * launches Pain(T)
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}