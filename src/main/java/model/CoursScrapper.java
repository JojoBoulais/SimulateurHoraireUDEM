package model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;

import java.io.IOException;


public class CoursScrapper {

    private String link;

    public CoursScrapper(String link) {

        this.link = link;
    }

    public Cours scrap(){

        Cours cours;

        try {

            // fetching the target website
            Document doc = Jsoup.connect(this.link).get();

            // Cours name
            Element coursTitreElem = doc.getElementsByClass("cours-titre").get(0);
            String coursTitre = coursTitreElem.text();
            cours = new Cours(coursTitre);

            // Sommaire
            Element sommaire = doc.getElementsByClass("cours-sommaire").get(0);
            Elements sommaire_elems = sommaire.getElementsByTag("li");
            for (Element section : sommaire_elems){

                String sectionName = section.getElementsByTag("b").get(0).text();
                String sectionValue = section.getElementsByTag("p").get(0).text();
                 switch (sectionName){
                     case "Campus":
                         cours.setCampus(sectionValue);
                     case "Crédits":
                         cours.setCredits(sectionValue);
                     case "Modes d’enseignement":
                         cours.setModeEnseignement(sectionValue);
                     case "Période":
                         cours.setPeriode(sectionValue);
                }
            }

            // Aperçu des horaires
            Elements trimestres = doc.getElementsByClass("cours-horaires-trimestre");

            // Trimestres
            Trimestre trimestre;
            for (Element t : trimestres){
                String periode = t.getElementsByTag("h3").text();
                trimestre = new Trimestre(periode);

                // Sections
                SectionTrimestre sectionTrimestre;
                Element sectionsContainer = t.getElementsByTag("div").get(0);
                Elements sections = sectionsContainer.getElementsByTag("tbody");
                Elements sectionNames = sectionsContainer.getElementsByTag("h4");
                String sectionName = "";

                int names_idx = 0;
                for (Element section : sections){

                    sectionName = sectionNames.get(names_idx).text();
                    names_idx += 1;

                    sectionTrimestre = new SectionTrimestre(sectionName);

                    // Sceances
                    Elements sceances = section.getElementsByTag("tr");
                    Sceance sceance;

                    for (Element s : sceances){
                        // Sceance Data

                        try {
                            String jour = s.getElementsByClass("jour_long").get(0).text();
                            String heureDebut = s.getElementsByTag("td").get(1).getElementsByTag("span").get(1).text();
                            String heureFin = s.getElementsByTag("td").get(1).getElementsByTag("span").get(3).text();
                            String dateDebut = s.getElementsByTag("td").get(2).getElementsByTag("span").get(1).text();
                            String dateFin = s.getElementsByTag("td").get(2).getElementsByTag("span").get(3).text();
                            sceance = new Sceance(jour, heureDebut, heureFin, dateDebut, dateFin);

                            sectionTrimestre.addSceance(sceance);

                        } catch (IndexOutOfBoundsException indexerr){
                           continue;
                        }
                    }
                    trimestre.addSection(sectionTrimestre);
                }

                cours.addTrimestre(trimestre);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return cours;

    }


}
