package controller;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.google.api.services.calendar.model.Events;
import model.Cours;
import model.Sceance;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Year;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/* class to demonstrate use of Calendar events list API */
public class CalendarController {
    /**
     * Application name.
     */
    private static final String APPLICATION_NAME = "Simulateur Horaire Udem";
    /**
     * Global instance of the JSON factory.
     */
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    /**
     * Directory to store authorization tokens for this application.
     */
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES =
            Collections.singletonList(CalendarScopes.CALENDAR_EVENTS);
    private static final String CREDENTIALS_FILE_PATH = "/client_secret_1047240827751-024u23elbhneeauq43ofor2g7er2sjif.apps.googleusercontent.com.json";

    private static final String CALENDAR_ID = "9cb342014c484ba4ee9271c512085851eb34bd2a44349d8faccc1eb02c5b0a39@group.calendar.google.com";


    private static final HashMap<String, DayOfWeek> WEEK_DAYS_HASHTABLE = new HashMap<>()
    {{put("Lundi", DayOfWeek.MONDAY);
        put("Mardi", DayOfWeek.TUESDAY);
        put("Mercredi", DayOfWeek.WEDNESDAY);
        put("Jeudi", DayOfWeek.THURSDAY);
        put("Vendredi", DayOfWeek.FRIDAY);
        put("Samedi", DayOfWeek.SATURDAY);
        put("Dimanche", DayOfWeek.SUNDAY);}};

    /**
     * Creates an authorized Credential object.
     *
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT)
            throws IOException, Exception{
        // Load client secrets.
        InputStream in = CalendarController.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets =
                GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
    }


    public Calendar getService(){

        Calendar service = null;

        try {
            // Build a new authorized API client service.
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            service =
                    new Calendar.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                            .setApplicationName(APPLICATION_NAME)
                            .build();
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

        return service;
    }


    public void listnEvents(int n) {


        Calendar service = getService();

        // List the next 10 events from the primary calendar.
        DateTime now = new DateTime(System.currentTimeMillis());
        Events events = null;
        try {


            events = service.events().list(CALENDAR_ID)
                    .setMaxResults(n)
                    .setTimeMin(now)
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
        }
        catch (IOException e) {
            return;
        }
        List<Event> items = events.getItems();
        if (items.isEmpty()) {
            System.out.println("No upcoming events found.");
        } else {
            System.out.println("Upcoming events");
            for (Event event : items) {
                DateTime start = event.getStart().getDateTime();
                if (start == null) {
                    start = event.getStart().getDate();
                }
                System.out.printf("%s (%s)\n", event.getSummary(), start);
            }
        }

    }

    public ArrayList<Event> getAllEvent(){
        ArrayList<Event> allEvents = new ArrayList<>();

        String pageToken = null;
        try {
            do {
                Events events = getService().events().list(CALENDAR_ID).setPageToken(pageToken).execute();
                List<Event> items = events.getItems();
                for (Event event : items) {
                    allEvents.add(event);
                }
                pageToken = events.getNextPageToken();
            } while (pageToken != null);
        } catch (IOException e){
            return allEvents;
        }

        return allEvents;
    }

    public void clearEvents(){
        for (Event event : getAllEvent()) {
            try {
                getService().events().delete(CALENDAR_ID, event.getId()).execute();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    public boolean coursAlreadyThere(String coursName){
        boolean coursThere = false;

        for(Event event : getAllEvent()){

            if (event.getSummary().equals(coursName)){
                    coursThere = true;
                    break;
            }
        }

        return coursThere;
    }

    public ArrayList<String> getSelectedCoursName(){

        ArrayList<String> coursNames = new ArrayList<>();

        for(Event event : getAllEvent()) {

            if (!(coursNames.contains(event.getSummary()))){
                coursNames.add(event.getSummary());
            }

        }

        return coursNames;

    }

    public void removeCoursEvent(String coursName){

        for(Event event : getAllEvent()){

            if (event.getSummary().equals(coursName)){
                try {
                    getService().events().delete(CALENDAR_ID, event.getId()).execute();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Ajout d'une sceance au Google calendar
     *
     * @param cours Cours duquel ajouter une sceance
     * @param sceance sceance contenant date et heures
     */
    public void addSceance(Cours cours, Sceance sceance){

        Event event = new Event()
                .setSummary(cours.getName())
                .setLocation("")
                .setDescription(cours.getModeEnseignement());

        // Start time
        java.util.Calendar calendar = java.util.Calendar.getInstance();

        int year = Year.now().getValue();
        LocalDate now = LocalDate.now();
        LocalDate firstOfMonth = now.with(TemporalAdjusters.firstInMonth(WEEK_DAYS_HASHTABLE.get(sceance.getJour())));


        calendar.set(year,
                java.util.Calendar.SEPTEMBER,
                firstOfMonth.getDayOfMonth(),
                Integer.valueOf(sceance.getHeureDebut().split("h")[0].strip()),
                Integer.valueOf(sceance.getHeureDebut().split("h")[1].strip()));


        DateTime startDateTime = new DateTime(calendar.getTime());
        EventDateTime start = new EventDateTime()
                .setDateTime(startDateTime)
                .setTimeZone("America/Toronto");
        event.setStart(start);

        // End time

        java.util.Calendar endCalendar = java.util.Calendar.getInstance();

        endCalendar.set(year,
                java.util.Calendar.SEPTEMBER,
                firstOfMonth.getDayOfMonth(),
                Integer.valueOf(sceance.getHeureFin().split("h")[0].strip()),
                Integer.valueOf(sceance.getHeureFin().split("h")[1].strip()));

        DateTime endDateTime = new DateTime(endCalendar.getTime());
        EventDateTime end = new EventDateTime()
                .setDateTime(endDateTime)
                .setTimeZone("America/Toronto");
        event.setEnd(end);

        String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;COUNT=17"};
        event.setRecurrence(Arrays.asList(recurrence));


        // adding event here
        try {
            event.setColorId("9");
            event = getService().events().insert(CALENDAR_ID, event).execute();
            System.out.printf("Event created: %s\n", event.getHtmlLink());
        } catch (IOException e){
            e.printStackTrace();
        }
    }



    //    /**
//     * Ajout d'une sceance au Google calendar
//     *
//     * @param cours Cours duquel ajouter une sceance
//     * @param sceance sceance contenant date et heures
//     */
//    public void addSceanceConformSiteUdem(Cours cours, Sceance sceance){
//
//        Event event = new Event()
//                .setSummary(cours.getName())
//                .setLocation("")
//                .setDescription(cours.getModeEnseignement());
//
//        // Start time
//        java.util.Calendar calendar = java.util.Calendar.getInstance();
//
//        int year = Year.now().getValue();
//        LocalDate now = LocalDate.now();
//        LocalDate firstOfMonth = now.with(TemporalAdjusters.firstInMonth(WEEK_DAYS_HASHTABLE.get(sceance.getJour())));
//
//        String[] dateSplitted = sceance.getDateDebut().split("/");
//
//
//        calendar.set(year,
//                Integer.valueOf(dateSplitted[1]),
//                Integer.valueOf(dateSplitted[0]),
//                Integer.valueOf(sceance.getHeureDebut().split("h")[0].strip()),
//                Integer.valueOf(sceance.getHeureDebut().split("h")[1].strip()));
//
//
//        DateTime startDateTime = new DateTime(calendar.getTime());
//        EventDateTime start = new EventDateTime()
//                .setDateTime(startDateTime)
//                .setTimeZone("America/Toronto");
//        event.setStart(start);
//
//        // End time
//
//        java.util.Calendar endCalendar = java.util.Calendar.getInstance();
//
//        endCalendar.set(year,
//                Integer.valueOf(dateSplitted[1]),
//                Integer.valueOf(dateSplitted[0]),
//                Integer.valueOf(sceance.getHeureFin().split("h")[0].strip()),
//                Integer.valueOf(sceance.getHeureFin().split("h")[1].strip()));
//
//        DateTime endDateTime = new DateTime(endCalendar.getTime());
//        EventDateTime end = new EventDateTime()
//                .setDateTime(endDateTime)
//                .setTimeZone("America/Toronto");
//        event.setEnd(end);
//
//        String[] dateFinSplitted = sceance.getDateFin().split("/");
//        String untilDate = dateFinSplitted[2] + dateFinSplitted[1] + dateFinSplitted[0];
//
//        String[] recurrence = new String[] {"RRULE:FREQ=WEEKLY;UNTIL=" + untilDate + "T170000Z"};
//        event.setRecurrence(Arrays.asList(recurrence));
//
//        // adding event here
//        try {
//            event = getService().events().insert(CALENDAR_ID, event).execute();
//            System.out.printf("Event created: %s\n", event.getHtmlLink());
//        } catch (IOException e){
//            e.printStackTrace();
//        }
//    }

}