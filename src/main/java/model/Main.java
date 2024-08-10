package model;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        ProgrammeRepository programmeRepository = ProgrammeRepository.getInstance();

        Programme BioInformatique = programmeRepository.getProgrammeByName("BioInformatique");
        CoursRepository coursRepositoryBioInfo = BioInformatique.getCoursRepository();

        for (Cours cours : coursRepositoryBioInfo.getCours()) {
            System.out.println(cours);
        }

//        String bioinfo = "https://admission.umontreal.ca/programmes/baccalaureat-en-bio-informatique/";
//
//        Programme bioInfoProgramme = new Programme("BioInformatique", bioinfo);
//
//        ArrayList<Programme> programmes = new ArrayList<>();
//        programmes.add(bioInfoProgramme);
//
//        programmeRepository.writeJson(programmes);

        //bioInfoProgramme.scrap();
//        CoursRepository coursRepository= bioInfoProgramme.getCoursRepository();
//        ArrayList<Cours> cours = coursRepository.getCours();

//
//
//        for (Cours c : cours){
//            System.out.println(c);
//        }



    }

}