package com.edu.muzlab;

import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AudioParser {

    public static ArrayList<File> listFiles = new ArrayList<File>();

    public static ArrayList<Mp3File> listMp3 = new ArrayList<Mp3File>();
    public static List<Mp3File> sortListMp3 = new ArrayList<Mp3File>();

    public static ArrayList<String> listArtists = new ArrayList<String>();
    public static List<String> sortListArtists = new ArrayList<String>();

    public static ArrayList<String> listAlboms = new ArrayList<String>();
    public static List<String> sortListAlboms = new ArrayList<String>();


    public void searchMuz(String sDir) {

        File myFile = new File(sDir);
        for (File file : myFile.listFiles()) {
            if (file.isDirectory()) {
                searchMuz(file.getAbsolutePath());
            }
            else {
                listFiles.add(file);
            }
        }
    }

    public void openMuzFile(){
        try {
            for (File f : listFiles){
                listMp3.add(new Mp3File(f.getPath()));
            }

        } catch (InvalidDataException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (UnsupportedTagException e) {
            e.printStackTrace();
        }
    }

    public void Func(){

        int i = 1;

        for (Mp3File m : listMp3) {
            if (m.hasId3v1Tag()) {
                listArtists.add(m.getId3v1Tag().getArtist());
                listAlboms.add(m.getId3v1Tag().getAlbum());
            }
            else if (m.hasId3v2Tag()){
                listArtists.add(m.getId3v2Tag().getArtist());
                listAlboms.add(m.getId3v2Tag().getAlbum());
            }
        }


        sortListArtists = listArtists.stream()
                .distinct()
                .collect(Collectors.toList());

        sortListAlboms = listAlboms.stream()
                .distinct()
                .collect(Collectors.toList());

        sortListMp3 = listMp3.stream()
                .distinct()
                .collect(Collectors.toList());


        for (Mp3File mp3File : listMp3){

            System.out.println("Файл "+i);

            if (mp3File.hasId3v1Tag()){
                System.out.println("Артист: " + mp3File.getId3v1Tag().getArtist());
                System.out.println("Альбом: " + mp3File.getId3v1Tag().getAlbum());
                System.out.println("Название: " + mp3File.getId3v1Tag().getTitle());
                System.out.println("Продолжительность:" + (mp3File.getLengthInSeconds()/60) + "мин"
                        + (mp3File.getLengthInSeconds()%60)+"сек");
            }
            else if (mp3File.hasId3v2Tag()){
                System.out.println("Артист: " + mp3File.getId3v2Tag().getArtist());
                System.out.println("Альбом: " + mp3File.getId3v2Tag().getAlbum());
                System.out.println("Название: " + mp3File.getId3v2Tag().getTitle());
                System.out.println("Продолжительность: " + (mp3File.getLengthInSeconds()/60) + "мин"
                        + (mp3File.getLengthInSeconds()%60)+"сек");
            }

            i++;
            System.out.println();

        }

    }

    public static void main(String[] args){

        AudioParser audioParser = new AudioParser();
        audioParser.searchMuz("D:/MuzFolder");
        audioParser.openMuzFile();
        audioParser.Func();
        
    }
}