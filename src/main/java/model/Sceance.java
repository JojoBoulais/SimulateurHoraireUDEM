package model;

public class Sceance {

private String jour;
private String heureDebut;
private String heureFin;
private String dateDebut;
private String dateFin;


public Sceance(String jour,
               String heureDebut,
               String heureFin,
               String dateDebut,
               String dateFin){
    this.jour = jour;
    this.heureDebut = heureDebut;
    this.heureFin = heureFin;
    this.dateDebut = dateDebut;
    this.dateFin = dateFin;
}


    public String getJour() {
        return jour;
    }

    public void setJour(String jour) {
        this.jour = jour;
    }

    public String getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(String heureDebut) {
        this.heureDebut = heureDebut;
    }

    public String getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(String heureFin) {
        this.heureFin = heureFin;
    }

    public String getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(String dateDebut) {
        this.dateDebut = dateDebut;
    }

    public String getDateFin() {
        return dateFin;
    }

    public void setDateFin(String dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString(){

    String string = this.jour + " De " + this.heureDebut + " A " + this.heureFin + " Du " + this.dateDebut + " Au " + this.dateFin + "\n";
    return string;
    }

}
