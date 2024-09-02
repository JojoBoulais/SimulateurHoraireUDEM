package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

public class ProgrammeScrapper {

    public static String admissionLink = "https://admission.umontreal.ca/";
    public static String structure = "structure-du-programme/";
    public static String coursFilter = "cours-et-horaires/cours/";
    private ArrayList<Cours> coursScrapped = new ArrayList<>();
    private Programme programme;
    public ProgrammeScrapper(Programme programme) {
        this.programme = programme;
    }

    public void scrap() {

    try {
        // fetching the target website
        Document doc = Jsoup.connect(this.programme.getLink() + ProgrammeScrapper.structure).get();
        Elements productElements = doc.getElementsByAttribute("href");

        // for all href
        for (Element elem : productElements){
            Attribute href = elem.attribute("href");
            String coursLink = href.getValue();
            // if consider as a Cours, Scrap it
            if(coursLink.contains(this.coursFilter)){

                String coursFullLink = admissionLink + coursLink;
                CoursScrapper coursScrapper = new CoursScrapper(coursFullLink);


                Cours cours = coursScrapper.scrap();
                System.out.println(cours.getName());
                this.coursScrapped.add(cours);
            }
        }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        this.programme.getCoursRepository().writeCours(this.coursScrapped);

    }

    public String getAdmissionLink() {
        return admissionLink;
    }

    public void setAdmissionLink(String admissionLink) {
        this.admissionLink = admissionLink;
    }

    public String getCoursFilter() {
        return coursFilter;
    }

    public void setCoursFilter(String coursFilter) {
        this.coursFilter = coursFilter;
    }

}

