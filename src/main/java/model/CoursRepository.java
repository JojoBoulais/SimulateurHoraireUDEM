package model;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.LinkedList;


public class CoursRepository{

    // Quasi Singleton (one instance per Programme)
    public static HashMap<String, CoursRepository> instances = new HashMap<>();

    private static String jsonFile;

    private ArrayList<Cours> cours = null;

    public CoursRepository(String file){
        this.jsonFile = "C:\\Users\\Jordan\\IdeaProjects\\SimulateurHoraireUDEM\\src\\main\\resources\\" + file;
        CoursRepository.instances.put(file, this);
    }

    public void readCours(){

        Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

        try {
            String content = Files.readString(Paths.get(this.jsonFile));

            Type foundType = new TypeToken<ArrayList<Cours>>(){}.getType();

            this.cours = gson.fromJson(content, foundType);
        } catch (IOException e){
            System.out.println(e);
        }

    }

    public void writeCours(ArrayList<Cours> cours) {

        try {
            FileWriter file = new FileWriter(CoursRepository.jsonFile, true);
            Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
            gson.toJson(cours, file);
            file.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public String getJsonFile() {
        return jsonFile;
    }


    public ArrayList<Cours> getCours() {

        if (this.cours == null){
            this.readCours();
        }
        return cours;
    }

    public Cours getCoursByName(String name){

        Cours foundCours = null;

        for(Cours c : this.getCours()){
            if (c.getName().equals(name)){
                foundCours = c;
                break;
            }
        }

        return foundCours;

    }

}



