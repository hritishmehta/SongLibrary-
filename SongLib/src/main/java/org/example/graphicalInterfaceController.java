/*
Names: Hritish Mehta and Likhit Krishnam
NETID: hmm112 and lk555
 */
package org.example;

import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.*;
import java.util.Collections;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class graphicalInterfaceController {
    @FXML ListView<Song> songList;
    @FXML Button addButton;
    @FXML Button editButton;
    @FXML Button deleteButton;
    @FXML Label songLabel;
    @FXML Label yearLabel;
    @FXML Label artistLabel;
    @FXML Label albumLabel;
    @FXML TextField songText;
    @FXML TextField albumText;
    @FXML TextField yearText;
    @FXML TextField artistText;
    @FXML TextArea songDisplay;
    private ObservableList<Song> obsList = FXCollections.observableArrayList();
    private static final String fileName = "src/main/java/org/example/Songs.txt";

    FileReader fileReader = null;
    BufferedReader br = null;
    FileWriter fileWriter = null;
    BufferedWriter bw = null;
    public void initialize() throws IOException{
        songList.setItems(obsList);
        loadSongs();

        if(!obsList.isEmpty()){
            songList.getSelectionModel().select(0);
            Song first = obsList.get(0);
            if(first.getYear() == -1 && first.getAlbum().equals("")){
                songDisplay.setText("Song: "+ first.getSong() + "\n" + "Artist: " + first.getArtist() + "\nYear: N/A\nAlbum: N/A" );
            }
            else if(first.getYear()!=-1 && first.getAlbum().equals("")){
                songDisplay.setText("Song: "+ first.getSong() + "\n" + "Artist: " + first.getArtist() + "\nYear: " + first.getYear() + "\nAlbum: N/A" );
            }
            else if(first.getYear()==-1 && !first.getAlbum().equals("")){
                songDisplay.setText("Song: "+ first.getSong() + "\n" + "Artist:" + first.getArtist() + "\nYear: N/A" + "\nAlbum: " + first.getAlbum());
            }
            else{
                songDisplay.setText("Song: "+ first.getSong() + "\n" + "Artist: " + first.getArtist() + "\nYear: " + first.getYear() +  "\nAlbum: " + first.getAlbum());
            }
        }


    }


    public void loadSongs() throws IOException{
        try{
            fileReader = new FileReader(fileName);
            br = new BufferedReader(fileReader);
            String line;
            while((line = br.readLine())!=null){
                String arr[] = line.split(",");
                Song s = new Song();
                for(int i = 0; i<arr.length; i++){
                    if(i==0){
                        s.setSongName(arr[0]);
                    }
                    if(i==1){
                        s.setArtist(arr[1]);
                    }
                    if(i==2){
                        try{
                            s.setYear(Integer.parseInt(arr[2]));
                        }catch(NumberFormatException e){
                            s.setAlbum(arr[2]);
                        }
                    }
                    if(i==3){
                        s.setAlbum(arr[3]);
                    }
                }
                /*
                if(arr.length==2){
                    s.setSongName(arr[0]);
                    s.setArtist(arr[1]);
                }
                if(arr.length==3){
                    s.setSongName(arr[0]);
                    s.setArtist(arr[1]);
                    s.setYear(Integer.parseInt(arr[2]));
                }
                else{
                    s.setSongName(arr[0]);
                    s.setArtist(arr[1]);
                    s.setYear(Integer.parseInt(arr[2]));
                    s.setAlbum(arr[3]);

                }*/
                obsList.add(s);
            }
        }finally{
            if(br != null){
                br.close();
            }
            if(fileReader != null){
                fileReader.close();
            }
        }
    }

    public void addFunction(ActionEvent e) throws IOException{
        //checks if songText and artistText fields aren't empty
        if(!songText.getText().isEmpty()  && !artistText.getText().isEmpty()){
            Song s = new Song();
            s.setSongName(songText.getText());
            s.setArtist(artistText.getText());
            if(!albumText.getText().isEmpty()){
                s.setAlbum(albumText.getText());
            }
            else{
                s.setAlbum("");
            }

            if(!yearText.getText().isEmpty()) {
                String year = yearText.getText();
                int year1 = Integer.parseInt(year);
                if(year1>=0) {
                    s.setYear(year1);
                }
                else{
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("The year must be a positive integer value");
                    alert.showAndWait();
                }
            }


            int check = 0;
            for(Song s1: obsList){
                if(s1.getSong().equalsIgnoreCase(songText.getText()) && s1.getArtist().equalsIgnoreCase(artistText.getText())){
                    check = 1;
                }
            }

            if(check == 0){
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText("Add Item");
                alert.setContentText("Are you sure you want to add the following song?");

                Optional<ButtonType> result = alert.showAndWait();
                if(result.get() == ButtonType.OK){
                    obsList.add(s);
                    Collections.sort(obsList);
                    songDisplay.setText(displayText(s));
                    songList.getSelectionModel().select(obsList.indexOf(s));
                    songText.clear();
                    albumText.clear();
                    yearText.clear();
                    artistText.clear();
                    saveSongs();
                }
            }
            else{
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("This song already exists!");
                alert.showAndWait();
            }
        }
        else{
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("Need to include Song Name and Artist");
            alert.showAndWait();
        }
    }

    private String displayText(Song s) throws IOException{
        if(s.getYear() == -1 && s.getAlbum().equals("")){
            return("Song: "+ s.getSong() + "\n" + "Artist: " + s.getArtist() + "\nYear: N/A\nAlbum: N/A");
        }
        else if(s.getYear()!=-1 && s.getAlbum().equals("")){
            return ("Song: "+ s.getSong() + "\n" + "Artist: " + s.getArtist() + "\nYear: " + s.getYear() + "\nAlbum: N/A" );
        }
        else if(s.getYear()==-1 && !s.getAlbum().equals("")){
            return ("Song: "+ s.getSong() + "\n" + "Artist: " + s.getArtist() + "\nYear: N/A" + "\nAlbum: " + s.getAlbum());
        }
        else{
            return ("Song: "+ s.getSong() + "\n" + "Artist: " + s.getArtist() + "\nYear: " + s.getYear() +  "\nAlbum: " + s.getAlbum());
        }
    }
    public void saveSongs() throws IOException {
        fileWriter = new FileWriter(fileName);
        bw = new BufferedWriter(fileWriter);
        for(Song s: obsList){
            bw.write(s.toDisplay());
            bw.newLine();
        }
        bw.close();
    }
    @FXML
    private void editFunction(ActionEvent e) throws IOException{
        Song s = songList.getSelectionModel().getSelectedItem();
        if(s != null){
            if(!songText.getText().isEmpty() && !artistText.getText().isEmpty()){
                int check = 0;
                //make sure edited song and artist text fields aren't going to lead to duplicate song
                for(Song s1: obsList){
                    if(s1.getSong().equals(songText.getText()) && s1.getArtist().equals(artistText.getText())){
                        check = 1;
                    }
                }
                if(check == 1){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("Song already exists");
                }
                else{
                    if(!yearText.getText().isEmpty()){
                        if(Integer.parseInt(yearText.getText())>=0){
                            Alert alert = new Alert(AlertType.CONFIRMATION);
                            alert.setTitle("Confirmation Dialog");
                            alert.setHeaderText("Edit Item");
                            alert.setContentText("Are you sure you want to edit the following song?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if(result.get() == ButtonType.OK){
                                s.setSongName(songText.getText());
                                s.setArtist(artistText.getText());
                                s.setYear(Integer.parseInt(yearText.getText()));
                                if(albumText.getText() != ""){
                                    s.setAlbum(albumText.getText());
                                }
                                else{
                                    s.setAlbum("");
                                }
                                Collections.sort(obsList);
                                songDisplay.setText(displayText(s));
                                songText.clear();
                                albumText.clear();
                                yearText.clear();
                                artistText.clear();
                                saveSongs();
                            }


                        }
                        else{
                            Alert alert = new Alert(AlertType.INFORMATION);
                            alert.setTitle("Information");
                            alert.setContentText("Year must be positive");
                        }
                    }
                    else {
                        Alert alert = new Alert(AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation Dialog");
                        alert.setHeaderText("Edit Item");
                        alert.setContentText("Are you sure you want to edit the following song?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if(result.get() == ButtonType.OK){
                            s.setSongName(songText.getText());
                            s.setArtist(artistText.getText());
                            s.setYear(-1);
                            if(albumText.getText() != ""){
                                s.setAlbum(albumText.getText());
                            }
                            else{
                                s.setAlbum("");
                            }
                            Collections.sort(obsList);
                            songDisplay.setText(displayText(s));
                            songText.clear();
                            albumText.clear();
                            yearText.clear();
                            artistText.clear();
                            saveSongs();
                        }

                    }
                }
            }
            else{
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("Song Name and Artist are required");
            }
        }


    }
    @FXML
    private void deleteFunction(ActionEvent e) throws IOException{
        Song s = songList.getSelectionModel().getSelectedItem();
        int index=0;
        if(s != null){
            for(int i=0; i<obsList.size(); i++){
                if(obsList.get(i).getSong().equals(s.getSong()) && obsList.get(i).getArtist().equals(s.getArtist())){
                    index=i;
                }
            }
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Dialog");
            alert.setHeaderText("Delete Item");
            alert.setContentText("Are you sure you want to delete the following song?");

            Optional<ButtonType> result = alert.showAndWait();
            if(result.get() == ButtonType.OK){
                obsList.remove(index);
                if(obsList.size()==0){
                    songDisplay.clear();
                }
                else if(index>=obsList.size()){
                    songDisplay.setText(displayText(obsList.get(index-1)));
                    songList.getSelectionModel().select(index-1);
                }
                else{
                    songDisplay.setText(displayText(obsList.get(index)));
                    songList.getSelectionModel().select(index);
                }
                saveSongs();
            }

        }
    }
    @FXML
    private void handleSelection(MouseEvent e) throws IOException{
        Song s = songList.getSelectionModel().getSelectedItem();
        if(s!=null){
            songDisplay.setText(displayText(s));
        }
    }

}
