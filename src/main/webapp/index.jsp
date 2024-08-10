<%@ page import="model.Cours" %>
<%@ page import="model.CoursRepository" %>
<%@ page import="model.Programme" %>
<%@ page import="model.ProgrammeRepository" %>

<html>


<head>
    <%------------------- Data -------------------%>
<%!
    Programme programme = ProgrammeRepository.getInstance().getProgrammeByName("BioInformatique");
    CoursRepository coursRepository = programme.getCoursRepository();
%>

    <%------------------- Styling -------------------%>
    <style type="text/css">
        .button-session{
            font-size: 22px;
            color: #0057AC;
            text-align: center;
            background-color: white;
            row-gap: 0;
            border: 0px white;
        }
        .button-session-choisi{
            font-size: 22px;
            color: white;
            text-align: center;
            background-color: #0057AC;
            row-gap: 0;
            border: 0px white;
        }
        .titre-foncer{
            font-size: 35px;
            color: white;
            background-color: #0B113A;
            text-align: center;
            box-shadow: #0B113A;
            height: 100px;
        }
        .calendrier{
            padding: 25px;
        } .horaire{
            width: 800px;
                  }
        .center-button{
            display: block;
            margin-left: auto;
            margin-right: auto;
        }
        table, th, td {
            border-collapse: collapse;
        }
        th {
            border: 1px solid black;
            padding: 15px;
        }
        table{
            border: 1px solid black;
            table-layout: fixed ;
            width: 100% ;
        }
        td {
            padding: 5px;
            text-align: center;
            border: 1px dashed black;
        }
        .time{
            background-color: #e5f0f8;
            color: #0B113A;
        }
        .scrollable {
            max-height: 350px; /* Ajustez la hauteur si nécessaire */
            overflow-y: auto;
            width: 102%;
        }
        .scrollable-cours {
            display: grid;
            grid-template-columns: repeat(4, 1fr); /* 5 columns */
            grid-auto-rows: 200px; /* Each item has a height of 100px */
            gap: 10px;
            width: 1010px;
            height: 400px; /* Set a fixed height for the grid */
            overflow-y: auto; /* Enable vertical scrolling */
            padding: 10px;
            box-sizing: border-box;
            background-color: #f8f8f8;
        } .section{
          display: block;
          margin-left: auto;
          margin-right: auto;
                  }
        .page-container {
            display: flex;
            flex-direction: column;
            width: 100%;
            margin: 0 auto;
        }
    </style>
</head>

<%------------------- HTML -------------------%>

<body>
 <h2 class="titre-foncer">Simulateur Horaire <%=programme.getName()%></h2><br><br>


 <div class="page-container">

 <div class="section">
     <div id="Buttons-Sessions" class="center-button">
         <button id="Selection-Automne" class="button-session-choisi">Automne</button>
         <button id="Selection-Hiver" class="button-session">Hiver</button>
         <button id="Selection-Ete" class="button-session">Ete</button>
     </div><br>
 </div>

 <div class="section">

<div id="Horaire" class="horaire">


    <div class="calendrier">

        <table>
            <thead>
            <tr>
                <th class="time">Heure</th>
                <th>Dimanche</th>
                <th>Lundi</th>
                <th>Mardi</th>
                <th>Mercredi</th>
                <th>Jeudi</th>
                <th>Vendredi</th>
                <th>Samedi</th>
            </tr>
            </thead>
        </table>
        <div class="scrollable">
            <table>
                <tbody>
                <!-- Générer des lignes pour chaque créneau de 30 minutes de 7:30 à 20:30 -->
                <tr><td class="time">07:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">08:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">08:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">09:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">09:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">10:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">10:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">11:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">11:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">12:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">12:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">13:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">13:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">14:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">14:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">15:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">15:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">16:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">16:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">17:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">17:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">18:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">18:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">19:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">19:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">20:00</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                <tr><td class="time">20:30</td><td></td><td></td><td></td><td></td><td></td><td></td><td></td></tr>
                </tbody>
            </table>
</div>
    </div>

</div>

 </div>

 <%------------------- Selection de cours -------------------%>

     
 <div class="section">
     <div class="zone-cours">
             <div class="scrollable-cours">

             </div>
     </div>
 </div>
</div>
 <%------------------- JavaScript -------------------%>


 <script>
     class CoursCard extends HTMLElement{

         constructor() {
             super();
             this.shadow = this.attachShadow({ mode: "open" });
         }


         connectedCallback(){
             this.render();
         }

         render(){

             this.shadow.innerHTML = `
            <style>
            .cours-card{
                display: flex;
                margin: 5px;
                border-radius: 5px;
                border-color: black;
                background-color: white;
                width: 225px;
                height: 200px;
            } .infos{
                margin: 5px;
                }
            .cours-title{
                font-weight: bold;
                font-size: 15px;
            } .cours-infos{
                font-size: 10px;
                }

            </style>

            <div class="cours-card">
                        <div class="infos">
                        <p></p>
                        <p class="cours-title" id="cours-name"><slot name="name"/></p>
                        <p class="cours-infos" id="cours-credit">Credits: <slot name="credits"/></p>
                        <p class="cours-infos" id="cours-campus">Campus: <slot name="campus"/></p>
                        <p class="cours-infos" id="cours-periode">Periode: <slot name="periode"/></p>
                </div>
            </div>
        `
         }
     }

     customElements.define("cours-card", CoursCard)

 </script>

 <script>
     const grid = document.getElementsByClassName("scrollable-cours").item(0);
 </script>

 <%------------------- Adding cours loop -------------------%>

 <% for (Cours cours : coursRepository.getCours()){ %>
     <%
         String coursName = cours.getName();
         String credit = cours.getCredits();
         String campus = cours.getCampus();
         String periode = cours.getPeriode();
         //System.out.println(coursName);
     %>


     <script>
         newItem = document.createElement("cours-card");

         infos = newItem.getElementsByTagName("p");

         //infos.item(0).innerText = "dasdasdas";

         grid.innerHTML += newItem.outerHTML;
     </script>

<%
    }
%>



</body>
</html>
