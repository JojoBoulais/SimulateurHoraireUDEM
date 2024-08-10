package model;

import java.util.ArrayList;

public class Cours {

    private String name;
    private String campus;
    private String credits;
    private String modeEnseignement;
    private String periode;
    private ArrayList<Trimestre> trimestres = new ArrayList<>();

    public Cours(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }

    public String getCredits() {
        return credits;
    }

    public void setCredits(String credits) {
        this.credits = credits;
    }

    public String getModeEnseignement() {
        return modeEnseignement;
    }

    public void setModeEnseignement(String modeEnseignement) {
        this.modeEnseignement = modeEnseignement;
    }

    public String getPeriode() {
        return periode;
    }

    public void setPeriode(String periode) {
        this.periode = periode;
    }


    public void addTrimestre(Trimestre trimestre){
        this.trimestres.add(trimestre);
    }

    public ArrayList<Trimestre> getTrimestres() {
        return trimestres;
    }

    public void setTrimestres(ArrayList<Trimestre> trimestres) {
        this.trimestres = trimestres;
    }

    @Override
    public String toString(){

        String trimestres = "";

        String trimestreSeperator = "######################\n";

        for (Trimestre t : this.trimestres){
            trimestres += trimestreSeperator;
            trimestres += t.toString();
        }

        String coursCeparator = "********************************************\n" +
                                "********************************************\n";

        return coursCeparator + this.name + " | Crédits: " + this.credits + "\n\n" + trimestres + coursCeparator;

    }

}
