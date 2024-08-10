package model;

import java.util.ArrayList;

public class SectionTrimestre {

private String name;

private ArrayList<Sceance> sceances = new ArrayList<>();


public SectionTrimestre(String name){
    this.name = name;
}

public void addSceance(Sceance sceance){
    this.sceances.add(sceance);
}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Sceance> getSceances() {
        return sceances;
    }

    public void setJours(ArrayList<Sceance> sceances) {
        this.sceances = sceances;
    }


    @Override
    public String toString(){


    String string = this.name + "\n";

    for (Sceance sceance : this.sceances){
        string += sceance.toString();
    }

    return string;

    }


}
