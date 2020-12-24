package project.pkg1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Project2Interface extends Application implements Comparable<MediaItem>{

    List<MediaItem> arrayList = new ArrayList<>();
    boolean titleList;

    private static Scanner in = new Scanner(System.in);

    public static int getMenuOption() {
        int option = 0;
        String inputString = "";

        while (option < 1 || option > 6) {

            System.out.println("");
            System.out.println("1. List the items");
            System.out.println("2. Add a new item");
            System.out.println("3. Mark an item as on loan");
            System.out.println("4. Mark an item as returned");
            System.out.println("5. Remove an item");
            System.out.println("6. Quit");
            System.out.print("What would you like to do? ");

            try {
                inputString = in.nextLine().trim();
                option = Integer.parseInt(inputString);
            } catch (NumberFormatException e) {
                // intentionally empty
            }

            if (option < 1 || option > 6) {
                System.out.println("Error: " + option
                        + " is not a valid option");
            }
        }

        System.out.println("");
        return option;
    }

    private static String getInput(String prompt) {
        System.out.print(prompt + " ");
        return in.nextLine().trim();
    }

    private static Date getDate(String prompt) throws Exception {
        System.out.print(prompt + " ");
        String dateString = in.nextLine().trim();

        try {
            Date loanedOn = new SimpleDateFormat(
                    "MM-dd-yyyy").parse(dateString);
            return loanedOn;
        } catch (ParseException e) {
            throw new Exception(dateString + " is not a valid date.");
        }
    }

    public static void main(String[] args) {

        MediaCollection collection = new MediaCollection();

        try {
            collection.readCollection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Continuing with a fresh collection");
        }

        Application.launch(args);

        int choice = getMenuOption();

        while (choice != 6) {
            try {
                switch (choice) {
                    case 1:
                        collection.listItems();
                        break;
                    case 2:
                        collection.addItem(getInput("What is the item's title?"),
                                getInput("What is the item's format (DVD, X-box, etc.)?"));
                        break;
                    case 3:
                        MediaItem toLoan = collection.retrieveItem(
                                getInput("What is the item's title?"));
                        if (collection.isLoanable(toLoan)) {
                            collection.loanItem(toLoan,
                                    getInput("Who did you loan " + toLoan.getTitle() + " to?"),
                                    getDate("What date did you loan it the them on? (MM-DD-YYYY)"));
                        } else {
                            System.out.println(toLoan.getTitle() + " is already on loan.");
                        }
                        break;
                    case 4:
                        MediaItem toReturn = collection.retrieveItem(
                                getInput("What is the item's title?"));
                        collection.returnItem(toReturn);
                        break;
                    case 5:
                        MediaItem toRemove = collection.retrieveItem(
                                getInput("What is the item's title?"));
                        collection.removeItem(toRemove);
                        break;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            choice = getMenuOption();
        }

        try {
            collection.storeCollection();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //sets gui title
        primaryStage.setTitle("Media Collection");

        MediaCollection media = new MediaCollection();
        media.readCollection();

        //Sets the min window size
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(700);

        //all the panes for the gui
        HBox mainPane = new HBox();
        VBox VBox1 = new VBox();
        VBox VBox2 = new VBox();
        VBox VBox3 = new VBox();
        VBox VBox4 = new VBox();
        HBox HBox1 = new HBox();
        HBox HBox2 = new HBox();
        HBox HBox3 = new HBox();
        HBox HBox4 = new HBox();
        HBox HBox5 = new HBox();
        HBox HBox6 = new HBox();
        HBox HBox7 = new HBox();
        HBox HBox8 = new HBox();

        //observal list used to display the items
        Collections.sort(media.getCollection(), Comparator.comparing((MediaItem) -> MediaItem.getTitle().toLowerCase()));
        ObservableList<MediaItem> list = FXCollections.observableArrayList(media.getCollection());

        //the display for the items
        ListView<MediaItem> list2 = new ListView<>();
        list2.setItems(list);
        list2.prefWidthProperty().bind(mainPane.widthProperty());
        list2.prefHeightProperty().bind(mainPane.heightProperty());

        //sets the display to the first vbox
        VBox1.getChildren().addAll(list2);

        //sets the first vbix in teh left and the second on the right
        mainPane.getChildren().addAll(VBox1);
        mainPane.getChildren().addAll(VBox2);

        //sets the padding for all the items on the right side vbox and the border for the vbox 3
        VBox2.prefWidthProperty().bind(mainPane.widthProperty());
        Insets vh1 = new Insets(15, 10, 10, 15);
        VBox2.setPadding(vh1);
        VBox3.setPadding(vh1);
        VBox4.setPadding(vh1);
        HBox1.setPadding(vh1);
        HBox2.setPadding(vh1);
        HBox3.setPadding(vh1);
        HBox4.setPadding(vh1);
        VBox3.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(0.5), new Insets(5))));
        VBox2.getChildren().addAll(VBox3);
        

        //adding the input for title into the gui
        VBox3.getChildren().addAll(HBox1);
        Label title2 = new Label("Title:    ");
        title2.setPadding(vh1);
        HBox1.getChildren().addAll(title2);
        TextField title1 = new TextField();
        title1.setPadding(vh1);
        HBox1.getChildren().addAll(title1);

        //adding the format input for the gui
        VBox3.getChildren().addAll(HBox2);
        Label format2 = new Label("Format:");
        format2.setPadding(vh1);
        HBox2.getChildren().addAll(format2);
        TextField format1 = new TextField();
        format1.setPadding(vh1);
        HBox2.getChildren().addAll(format1);

        //adds the button to add media into the collection
        Button buttonAdd = new Button("Add:");
        HBox5.setPadding(vh1);
        VBox3.getChildren().addAll(HBox5);
        HBox5.getChildren().addAll(buttonAdd);

        //adds the remove button into the gui
        Button buttonRemove = new Button("Remove:");
        HBox6.setPadding(vh1);
        VBox2.getChildren().addAll(HBox6);
        HBox6.getChildren().addAll(buttonRemove);

        //adds the return button into the gui
        Button buttonReturn = new Button("Return:");
        HBox7.setPadding(vh1);
        VBox2.getChildren().addAll(HBox7);
        HBox7.getChildren().addAll(buttonReturn);
        
        //Adds boarder for vbox 4 and puts it into the second vbox
        VBox4.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(0.5), new Insets(5))));
        VBox2.getChildren().addAll(VBox4);

        //adds the input for loaned to in the gui
        VBox4.getChildren().addAll(HBox3);
        Label loanedTo2 = new Label("Loaned To:");
        HBox3.getChildren().addAll(loanedTo2);
        TextField loanedTo1 = new TextField();
        HBox3.getChildren().addAll(loanedTo1);

        //adds the date picker for the dated loaned
        VBox4.getChildren().addAll(HBox4);
        Label loanedOn2 = new Label("Loaned On:");
        HBox4.getChildren().addAll(loanedOn2);
        DatePicker loanedOn1 = new DatePicker();
        HBox4.getChildren().addAll(loanedOn1);

        // adds the button allowing an item to be loaned
        Button buttonLoan = new Button("Loan");
        HBox8.setPadding(vh1);
        VBox4.getChildren().addAll(HBox8);
        HBox8.getChildren().addAll(buttonLoan);

        //adds the radio buttons for how you want to sort your list
        Label sort = new Label("Sort");
        VBox2.getChildren().addAll(sort);
        ToggleGroup group = new ToggleGroup();
        RadioButton ButtonTitle = new RadioButton("By title");
        ButtonTitle.setToggleGroup(group);
        RadioButton ButtonDate = new RadioButton("By date loaned");
        ButtonDate.setToggleGroup(group);
        group.selectToggle(ButtonTitle);
        VBox2.getChildren().addAll(ButtonTitle);
        VBox2.getChildren().addAll(ButtonDate);

        //adds the items to the media collection
        buttonAdd.setOnAction(e -> {
            try {
                media.addItem(title1.getText(), format1.getText());
                media.storeCollection();
                if(titleList){
                    //title sort
                    Collections.sort(media.getCollection(), Comparator.comparing((MediaItem) -> MediaItem.getTitle().toLowerCase()));
                    media.storeCollection();
                }
                else{
                    //sort by date loaned if it worked
                }
                ObservableList<MediaItem> list3 = FXCollections.observableList(media.getCollection());
                list2.setItems(list3);
                list2.refresh();
                title1.setText("");
                format1.setText("");

            } catch (Exception ex) {
                System.out.println("Error: Can't Add");
            }
        });

        //Removes items from collection
        buttonRemove.setOnAction(e -> {
            try {
                media.removeItem(list2.getSelectionModel().getSelectedItem());
                media.storeCollection();
                if(titleList){
                    //title sort
                    Collections.sort(media.getCollection(), Comparator.comparing((MediaItem) -> MediaItem.getTitle().toLowerCase()));
                    media.storeCollection();
                }
                else{
                    //sort by date loaned if it worked
                }
                ObservableList<MediaItem> list3 = FXCollections.observableList(media.getCollection());
                list2.setItems(list3);
                list2.refresh();

            } catch (Exception ex) {
                System.out.println("Error: Can't Remove");
            }
        });

        //Return a loaned item
        buttonReturn.setOnAction(e -> {
            try {
                media.returnItem(list2.getSelectionModel().getSelectedItem());
                media.storeCollection();
                if(titleList){
                    //title sort
                    Collections.sort(media.getCollection(), Comparator.comparing((MediaItem) -> MediaItem.getTitle().toLowerCase()));
                    media.storeCollection();
                }
                else{
                    //sort by date loaned if it worked
                }
                ObservableList<MediaItem> list3 = FXCollections.observableList(media.getCollection());
                list2.setItems(list3);
                list2.refresh();

            } catch (Exception ex) {
                System.out.println("Error: not on loan");
            }
        });

        //Button for loaning an item
        buttonLoan.setOnAction(e -> {
            try {
                
                media.loanItem(list2.getSelectionModel().getSelectedItem(), loanedTo1.getText(), java.sql.Date.valueOf(loanedOn1.getValue()));
                media.storeCollection();
                if(titleList){
                    //title sort
                    Collections.sort(media.getCollection(), Comparator.comparing((MediaItem) -> MediaItem.getTitle().toLowerCase()));
                    media.storeCollection();
                }
                else{
                    //sort by date loaned if it worked
                }
                ObservableList<MediaItem> list3 = FXCollections.observableList(media.getCollection());
                list2.setItems(list3);
                list2.refresh();
                loanedTo1.setText("");
                
            } catch (Exception ex) {
                System.out.println("Error: Can't Loan");
            }
        });

        //sorts by title
        ButtonTitle.setOnAction(e -> {
            titleList = true;
            Collections.sort(media.getCollection(), Comparator.comparing((MediaItem) -> MediaItem.getTitle().toLowerCase()));
            try {
                media.storeCollection();
                ObservableList<MediaItem> list3 = FXCollections.observableList(media.getCollection());
                list2.setItems(list3);
                list2.refresh();
            } catch (Exception ex) {
                System.out.println("Error");
            }

        });

        //sort for date loaned(doesn't work)
        ButtonDate.setOnAction(e -> {
            titleList = false;
        });

        //allows the gui to be showen
        Scene scene = new Scene(mainPane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    //comaparing thing that i dont know how this works
    @Override
    public int compareTo(MediaItem t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
