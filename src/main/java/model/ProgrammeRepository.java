package model;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileWriter;
import java.nio.file.Files;
import java.io.IOException;
import java.lang.reflect.Type;

import java.nio.file.Paths;
import java.util.ArrayList;

public class ProgrammeRepository {

    // ------------- Singleton -------------

    private static ProgrammeRepository _instance;

    public static ProgrammeRepository getInstance(){
        if (ProgrammeRepository._instance == null){
            ProgrammeRepository._instance = new ProgrammeRepository();
        }

        return ProgrammeRepository._instance;

    }

    private ProgrammeRepository(){

    }

    // ------------- Attributes -------------

    static String jsonFile = "C:\\Users\\Jordan\\IdeaProjects\\SimulateurHoraireUDEM\\src\\main\\resources\\ProgrammeRepository.json";
    private ArrayList<Programme> programmes;

    // ------------- GETTER/SETTER -------------

    ArrayList<Programme> getProgrammes(){

        if (this.programmes == null){
            this.readJson();
        }

        return this.programmes;

    }

    public Programme getProgrammeByName(String name){

        ArrayList<Programme> ps = getProgrammes();

        Programme foundP = null;

        for(Programme programme : ps){
            if (programme.getName().equals(name)){
                foundP = programme;
                break;
            }
        }

        return foundP;

    }


    // ------------- METHODES -------------

    public void readJson(){

        Gson gson = new Gson().newBuilder().setPrettyPrinting().create();

        try {
            String content = Files.readString(Paths.get(this.jsonFile));

            Type foundType = new TypeToken<ArrayList<Programme>>(){}.getType();

            this.programmes = gson.fromJson(content, foundType);
        } catch (IOException e){
            System.out.println(e);
        }

    }

    public void writeJson(ArrayList<Programme> programmes) {

        try {
            FileWriter file = new FileWriter(ProgrammeRepository.jsonFile, true);
            Gson gson = new Gson().newBuilder().setPrettyPrinting().create();
            System.out.println(programmes);
            gson.toJson(programmes, file);
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
