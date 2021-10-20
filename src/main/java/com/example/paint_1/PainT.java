package com.example.paint_1;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

//import org.imgscalr.Scalr;
//import java.awt.*;
//import java.awt.*;


public class PainT extends Application {
    @SuppressWarnings("ConstantConditions")
    @Override
    public void start(Stage stage) throws IOException {
        //Create temp file directory if not already there
        Path workspace = Paths.get("Images");
        Files.createDirectories(workspace);
        Path tempDir = Files.createTempDirectory(workspace, "temp");



        stage.setTitle("Paint (t)");
        stage.setMaximized(true);




        /**************************
         * HANDLERS
         ****************** ***/

        ImageHandler iHandler = new ImageHandler();
        DrawHandler dHandler = new DrawHandler();
        PaletteHandler pHandler = new PaletteHandler();

        /**************************
         * IMAGES
         ****************** ***/

        Image currentImage = null;



        /**************************
         * MENU Bars
         ****************** ***/

        /**** Main Menu *******/
        // File menu
        Menu mFile = new Menu("File");
        MenuItem openImage = new MenuItem("Open Image");
        openImage.setAccelerator(KeyCombination.keyCombination("Shortcut+Shift+N"));
        MenuItem saveI = new MenuItem("Save Image");
        saveI.setAccelerator(KeyCombination.keyCombination("Shortcut+S"));
        MenuItem saveIAs= new MenuItem("Save Image As");
        saveIAs.setAccelerator(KeyCombination.keyCombination("Shortcut+Shift+S"));
        MenuItem closeI = new MenuItem("Close Image");
        MenuItem exit = new MenuItem("Close");
        mFile.getItems().add(openImage);
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

        /********* Button Menu ***/

        ToggleGroup buttons = new ToggleGroup();
        ButtonBar bBar = new ButtonBar();
        bBar.setButtonMinWidth(50);

        ToggleButton drawFree = new ToggleButton("FREE");
        drawFree.setToggleGroup(buttons);
        ButtonBar.setButtonData(drawFree, ButtonBar.ButtonData.LEFT);
        ToggleButton drawLine = new ToggleButton("LINE");
        drawLine.setToggleGroup(buttons);
        ButtonBar.setButtonData(drawLine, ButtonBar.ButtonData.LEFT);

        bBar.getButtons().addAll(drawFree, drawLine);

        /********* Color Palette ***/

        VBox vB1 = new VBox(5);
        vB1.setAlignment(Pos.CENTER);
        vB1.setPadding(new Insets(10));
        for (int i = 0; i < 8; i++){
            vB1.getChildren().add(pHandler.getRect(i));
        }
        vB1.getChildren().add(pHandler.getCurrentColorRect());


        /********* Line Width Selection ***/
        Menu pMenu = new Menu("",
                pHandler.getMenuLine());
        MenuItem thinW = new MenuItem("Thin",
                pHandler.getLine(0));
        MenuItem defW = new MenuItem("Default",
                pHandler.getLine(1));
        MenuItem thickW = new MenuItem("Thick",
                pHandler.getLine(2));
        pMenu.getItems().add(thinW);
        pMenu.getItems().add(defW);
        pMenu.getItems().add(thickW);

        MenuBar pBar = new MenuBar(pMenu);
        vB1.getChildren().add(pBar);

        TextField textW = new TextField(Double.toString(dHandler.getLineWidth()));
        textW.setMinWidth(20);
        textW.setMaxWidth(40);
        vB1.getChildren().add(textW);







        /**************************
         *  SCENE Setup
         ****************** ***/
        BorderPane bRoot = new BorderPane();
        Scene baseScene = new Scene(bRoot,
                Screen.getPrimary().getVisualBounds().getWidth(),
                Screen.getPrimary().getVisualBounds().getHeight());


        /**************************
         *  Canvas Setup
         ****************** ***/
        Pane cPane = new Pane();
        bRoot.setCenter(cPane);

        Canvas canvas = new Canvas();
        cPane.getChildren().add(canvas);
        cPane.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID,
                CornerRadii.EMPTY,
                BorderWidths.DEFAULT)));

        canvas.widthProperty().bind(cPane.widthProperty());
        canvas.heightProperty().bind(cPane.heightProperty());

        GraphicsContext gContent = canvas.getGraphicsContext2D();
        gContent.setFill(Color.WHITE);
        gContent.fillRect(0,
                0,
                canvas.getWidth(),
                canvas.getHeight());


        //Group group1 = new Group(canvas);
        //cPane.setVisible(true);



        /**************************
         *  LAYOUT Setup
         ****************** ***/

        HBox hB1 = new HBox(mBar);
        HBox hB2 = new HBox(bBar);
        VBox vB2 = new VBox();
        bRoot.setTop(hB1);
        bRoot.setBottom(hB2);
        bRoot.setLeft(vB1);
        bRoot.setRight(vB2);








        /**************************
         * SCENE creation and update
         ****************** ***/

        stage.setScene(baseScene);

        stage.show();



        /**************************
         * Menu Item Actions
         ****************** ***/

        // OPEN image
        openImage.setOnAction(
                aE -> {

                    File openF = openImage(stage);
                    String openFString = openF.getAbsolutePath();
                    System.out.println("Opening " + openFString + "...");

                    try {
                        iHandler.addImage(openF);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Image openI = null;
                    try {
                        openI = new Image(new FileInputStream(openFString));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                    gContent.clearRect(
                            0,0,
                            canvas.getWidth(),
                            canvas.getHeight());

                    gContent.drawImage(openI,
                            0,
                            0);









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
                        System.out.println("SAVING...");


                    }

                }
        );

        // CLOSE image
        closeI.setOnAction(
                aE -> {
                    iHandler.closeImage();
                    gContent.clearRect(0,
                            0,
                            canvas.getWidth(),
                            canvas.getHeight());
                    //cPane.setVisible(false);
                }
        );

        // EXIT program
        exit.setOnAction(
                aE -> System.exit(0)
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
                aE -> aboutPop()
        );


        /**************************
         * Button Actions
         ****************** ***/

        drawFree.setOnAction(
                bE -> dHandler.setDrawType(DrawType.FREE)
        );

        drawLine.setOnAction(
                bE -> dHandler.setDrawType(DrawType.LINE)
        );

        /**************************
         * Palette Commands
         ****************** ***/

        /**** Color Selection *******/

        vB1.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> {

            try {

                Rectangle target = (Rectangle) e.getTarget();
                dHandler.setCurrentColor((Color) target.getFill());
                pHandler.setCurrentColor(dHandler.getCurrentColor());
            } catch (ClassCastException cE){
                cE.printStackTrace();
            }

                });


        /**** Line Thickness Selection *******/

        pMenu.setOnAction(
                aE -> pMenu.setGraphic(pHandler.getMenuLine())
        );

        thinW.setOnAction(
                aE -> {
                    dHandler.setLineWidth(pHandler.thin);
                    pHandler.setCurrentLine(1);
                    textW.setText(Double.toString(dHandler.lineWidth));
                    Line mLine = new Line(0,
                            10,
                            20,
                            10);
                    mLine.setStroke(pHandler.getCurrentColor());
                    mLine.setStrokeWidth(1);
                    pMenu.setGraphic(mLine);
                }
        );
        defW.setOnAction(
                aE -> {
                    dHandler.setLineWidth(pHandler.def);
                    pHandler.setCurrentLine(5);
                    textW.setText(Double.toString(dHandler.lineWidth));
                    Line mLine = new Line(0,
                            10,
                            20,
                            10);
                    mLine.setStroke(pHandler.getCurrentColor());
                    mLine.setStrokeWidth(5);
                    pMenu.setGraphic(mLine);
                }
        );
        thickW.setOnAction(
                aE -> {
                    dHandler.setLineWidth(pHandler.thick);
                    pHandler.setCurrentLine(10);
                    textW.setText(Double.toString(dHandler.lineWidth));
                    Line mLine = new Line(0,
                            10,
                            20,
                            10);
                    mLine.setStroke(pHandler.getCurrentColor());
                    mLine.setStrokeWidth(10);
                    pMenu.setGraphic(mLine);
                }
        );


        textW.setOnAction(
                aE -> {
                    char[] c = textW.getText().toCharArray();
                    System.out.println(String.valueOf(c));
                    boolean hasDecimal = false;
                    for (int i = 0; i < c.length; i++){
                        if (!Character.isDigit(c[i])){
                            boolean killChar = false;
                            for (int j = i; j + 1 < c.length; j++){
                                if (c[i] == '.' && !hasDecimal){
                                    hasDecimal = true;
                                    i++;
                                }
                                else  {
                                    c[j] = c[j+1];
                                    killChar = true;
                                }

                            }
                            if (killChar) {
                                c = Arrays.copyOfRange(c, 0, c.length - 1);
                                i--;
                            }
                            System.out.println(String.valueOf(c));

                        }


                    }
                    textW.setText(String.valueOf(c));
                    dHandler.setLineWidth(Double.parseDouble(textW.getText()));

                }
        );



        /**************************
         * Drawing
         ****************** ***/

        canvas.addEventHandler(MouseEvent.ANY,
                e -> {
                    switch (dHandler.getDrawType()) {
                        case FREE:
                            System.out.println("FREE");
                            if (e.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                                if (e.isPrimaryButtonDown()) {
                                    gContent.setFill(dHandler.getCurrentColor());
                                    gContent.fillRect(e.getX() - 3,
                                            e.getY() - 3,
                                            5,
                                            5);
                                } else if (e.isSecondaryButtonDown()) {

                                    gContent.clearRect(e.getX() - 3,
                                            e.getY() - 3,
                                            5,
                                            5);

                                }
                            }
                            break;

                        case LINE:
                            System.out.println("LINE");
                            if (e.getEventType() == MouseEvent.MOUSE_CLICKED) {
                                if (dHandler.isFirstClick()) {
                                    dHandler.setPosA(e.getX(),
                                            e.getY());
                                    System.out.println(dHandler.isFirstClick());
                                    dHandler.click();
                                } else {
                                    gContent.setLineWidth(dHandler.getLineWidth());
                                    gContent.setStroke(dHandler.getCurrentColor());
                                    gContent.strokeLine(dHandler.getPosAX(),
                                            dHandler.getPosAY(),
                                            e.getX(),
                                            e.getY());
                                    dHandler.setPosA(0, 0);
                                    System.out.println(dHandler.isFirstClick());
                                    dHandler.click();


                                }


                            }
                            break;
                        default:
                            break;
                    }

                });
















        /**************************
         * IMAGE
         ****************** ***/





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

    /**************
     * Image Resize Method
     * Resizes image to fil canvas
     */




    /** main method
     * launches Pain(T)
     * @param args
     */
    public static void main(String[] args) {
        launch();
    }

}