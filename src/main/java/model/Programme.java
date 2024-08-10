package model;

public class Programme{

    private String name;
    private String link;
    private String jsonFile;

    public Programme(String name, String link){
        this.name = name;
        this.link = link;
        this.jsonFile = name + ".json";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public CoursRepository getCoursRepository(){

        CoursRepository coursRepository = CoursRepository.instances.get(this.jsonFile);
        if (coursRepository == null) {
            coursRepository = new CoursRepository(this.jsonFile);
        }
        return  coursRepository;
    }

    public ProgrammeScrapper getProgrammeScrapper(){
        ProgrammeScrapper programmeScrapper = new ProgrammeScrapper(this);
        return programmeScrapper;
    }

}
