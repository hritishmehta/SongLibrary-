package org.example;
public class Song implements Comparable<Song>{
    private String songName;
    private String artist;
    private int year;
    private String album;

    public Song(){
        songName = "";
        artist = "";
        year = -1;
        album = "";
    }
    public Song(String songName, String artist, int year, String album){
        this.songName = songName;
        this.artist = artist;
        this.year = year;
        this.album = album;
    }

    public String getSong(){
        if(songName == null){
            return "";
        }
        else{
            return songName;
        }
    }

    public String getArtist(){
        if(artist == null){
            return "";
        }
        else{
            return artist;
        }
    }


    public String getAlbum(){
        if(album == null){
            return "";
        }
        else{
            return album;
        }
    }

    public int getYear(){
        return year;
    }

    public void setSongName(String songName){
        this.songName = songName;
    }
    public void setArtist(String artist){
        this.artist = artist;
    }
    public void setAlbum(String album){
        this.album = album;
    }
    public void setYear(int year){
        if(year>0){
            this.year = year;
        }
    }

    public String toString(){
        return this.songName + "," + this.artist;
    }
    public String toDisplay(){
        if(this.year == -1 && this.album.equals("")){
            return this.songName + "," + this.artist;
        }
        else if(this.year!=-1 && this.album.equals("")){
            return this.songName + "," + this.artist + "," + this.year;
        }
        else if(this.year==-1 && !this.album.equals("")){
            return this.songName + "," + this.artist + "," + this.album;
        }
        else{
            return (this.songName + "," + this.artist + "," + this.year +  "," + this.album);
        }
    }

    @Override
    public int compareTo(Song s1){
        if(this.songName.toLowerCase().compareTo(s1.songName.toLowerCase())==0){
            return this.artist.toLowerCase().compareTo(s1.artist.toLowerCase());
        }
        else{
            return this.songName.toLowerCase().compareTo(s1.songName.toLowerCase());
        }
    }
}