package model;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {


        ProgrammeRepository programmeRepository = ProgrammeRepository.getInstance();

        Programme BioInformatique = programmeRepository.getProgrammeByName("BioInformatique");
        BioInformatique.getProgrammeScrapper().scrap();

    }

}