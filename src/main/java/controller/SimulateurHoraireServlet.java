package controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.CoursRepository;
import model.Programme;
import model.ProgrammeRepository;
import model.Cours;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Scanner;



@WebServlet(name="SimulateurHoraireServlet", urlPatterns = "/")
public class SimulateurHoraireServlet  extends HttpServlet {

    Programme programme = ProgrammeRepository.getInstance().getProgrammeByName("BioInformatique");
    CoursRepository coursRepository = programme.getCoursRepository();
    ArrayList<Cours> selectedCours = new ArrayList<>();

    // Enregistrement des cours selectionners (pourrais etre remplacer simplement par les events du calendrier)
    String selectedCoursFile = "C:\\Users\\Jordan\\IdeaProjects\\SimulateurHoraireUDEM\\src\\main\\resources\\selectedCours.txt";
    String credentialsFile = "C:\\Users\\Jordan\\IdeaProjects\\SimulateurHoraireUDEM\\src\\main\\resources\\udemsimulateurhoraire-c651de8cdfde.json";
    String CALENDAR_ID ="9cb342014c484ba4ee9271c512085851eb34bd2a44349d8faccc1eb02c5b0a39@group.calendar.google.com";

    private void writeSelectedCours(String coursName){
        try {
            FileWriter writer = new FileWriter(this.selectedCoursFile, true);
            writer.write(coursName + ";");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }


    protected boolean coursAlreadySelected(String coursName){

        boolean alreadyInSelected = false;

        for (Cours cours : selectedCours){

            if (cours.getName().equals(coursName)){
                alreadyInSelected = true;
                break;
            }
        }

        return alreadyInSelected;
    }

    private void removeSelectedCours(String coursName){

        ArrayList<Cours> toKeep = new ArrayList<>();
        for(Cours cours : selectedCours){

            if (!cours.getName().equals(coursName)){
                toKeep.add(cours);
            }

        }

        // Clearing file content.
        try {
            FileWriter writer = new FileWriter(this.selectedCoursFile, false);
            writer.write("");
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }


        for(Cours cours : toKeep){
            writeSelectedCours(cours.getName());
        }


    }

    private void readSelectedCours(){

        String line = "";

        try (Scanner scanner = new Scanner(new File(this.selectedCoursFile))) {
            line = scanner.nextLine();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        String[] coursNames = line.split(";");

        Cours cours = null;
        for(String coursName : coursNames){

            cours = coursRepository.getCoursByName(coursName);
            selectedCours.add(cours);
        }
    }

    // ------------------------------------------------------------------------------------------

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        System.out.println("fafafasfa");
        String coursName = request.getParameter("age");
        System.out.println(coursName);

        if (coursAlreadySelected(coursName)){
            removeSelectedCours(coursName);
            return;
        }

        writeSelectedCours(coursName);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        selectedCours = new ArrayList<>();
        readSelectedCours();

        // Adding to google calendar here

    }




}
