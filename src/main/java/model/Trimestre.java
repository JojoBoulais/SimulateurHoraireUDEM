package model;

import java.util.ArrayList;

public class Trimestre {

    private String periode;
    private String saison;
    private String annee;
    private ArrayList<SectionTrimestre> sections = new ArrayList<>();

    public Trimestre(String periode){
        this.periode = periode;
        String[] split = periode.split(" ");
        this.saison = split[0];
        this.annee = split[1];
    }

    public void addSection(SectionTrimestre section){
        this.sections.add(section);
    }

    @Override
    public String toString(){

        String string = this.periode + "\n";

        String separator = "\n---------------\n";

        for(SectionTrimestre section : this.sections){

            string += section.toString();
            string += separator;
        }

        return string;

    }


}
