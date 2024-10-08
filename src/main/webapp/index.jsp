<%--@elvariable id="cardColor" type=""--%>
<%--@elvariable id="cardTextColor" type=""--%>
<%@ page import="model.Cours" %>
<%@ page import="model.CoursRepository" %>
<%@ page import="model.Programme" %>
<%@ page import="model.ProgrammeRepository" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="controller.CalendarController" %>

<html>


<head>
    <%------------------- Data -------------------%>
<%!
    Programme programme = ProgrammeRepository.getInstance().getProgrammeByName("BioInformatique");
    CoursRepository coursRepository = programme.getCoursRepository();
%>

<%
    CalendarController calendarController = new CalendarController();
    ArrayList<String> selectedCours = calendarController.getSelectedCoursName();
    System.out.println(selectedCours); %>

    <%------------------- Styling -------------------%>
    <style type="text/css">
        .button-session{
            font-size: 22px;
            color: #59629e;
            text-align: center;
            background-color: white;
            row-gap: 0;
            border: 0px white;
        }
        .button-session:hover{
            cursor: pointer;
        }
        .button-session-choisi{
            font-size: 22px;
            color: white;
            text-align: center;
            background-color: #59629e;
            row-gap: 0;
            border: 0px white;
        }
        .button-session-choisi:hover{
            cursor: pointer;
        }
        .clearEvents{
            font-size: 22px;
            color: white;
            text-align: center;
            background-color: #c74e4e;
            row-gap: 0;
            width: 100px;
            height: 35px;
            display: block;
            margin-top: 10px;
            margin-left: auto;
            margin-right: auto;
        }
        .clearEvents:hover{
            border: 3px solid #e66b6b;
            cursor: pointer;
        }
        .calendrier{
            padding: 25px;
        } .horaire{
            width: 800px;
                  }
        .center-button{
            margin-top: 20px;
            margin-bottom: 10px;
            position: relative;
            left: 38%;
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
        .scrollable-cours {
            display: grid;
            grid-template-columns: repeat(4, 1fr); /* 5 columns */
            grid-auto-rows: 200px; /* Each item has a height of 100px */
            gap: 10px;
            width: 1015px;
            height: 800px; /* Set a fixed height for the grid */
            overflow-y: auto; /* Enable vertical scrolling */
            padding: 10px;
            box-sizing: border-box;
            background-color: #f8f8f8;
        } .section{
          display: block;
            margin-left: 10px;
                  }
        .page-container {
            display: flex;
            flex-direction: row;
            width: 100%;
            margin: 0 auto;
        }
    </style>
</head>

<%------------------- HTML -------------------%>

<body>
 <div class="page-container">




 <%------------------- JavaScript -------------------%>


 <script>

     class CoursCard extends HTMLElement{

         constructor(coursName, coursCredit, coursCampus, coursPeriode) {
             super();
             this._coursName = coursName;
             this._coursCredit = coursCredit;
             this._coursCampus = coursCampus;
             this._coursPeriode = coursPeriode;
             this.shadow = this.attachShadow({ mode: "open" });
             this.render();
         }


         get coursName(){
             return this._coursName;
         }

         get coursCredit() {
             return this._coursCredit;
         }

         get coursCampus() {
             return this._coursCampus;
         }

         get coursPeriode(){
             return this._coursPeriode;
         }


         render(){
             this.shadow.innerHTML = `
            <style>
            .cours-card{
                color: ${cardTextColor};
                display: flex;
                margin: 5px;
                border-radius: 5px;
                border: 1px solid #e4e8fe;
                background-color: ${cardColor};
                width: 225px;
                height: 200px;
            }
            .cours-card:hover{
            border: 3px solid #e4e8fe;
            cursor: pointer;
                width: 221px;
                height: 197px;
                animation: 4s linear;
            }
            .infos{
                margin: 10px;
                }
            .cours-title{
                font-weight: bold;
                font-size: 15px;
            } .cours-infos{
                font-size: 10px;
                }

            </style>

            <div class="cours-card" id="frame">
                        <div class="infos">
                        <p></p>
                        <p class="cours-title" id="cours-name"><slot name="name"/></p>
                        <p class="cours-infos" id="cours-credit">Credits: <slot name="credits"/></p>
                        <p class="cours-infos" id="cours-campus">Campus: <slot name="campus"/></p>
                        <p class="cours-infos" id="cours-periode">Periode: <slot name="periode"/></p>
                </div>
            </div>
        `;
         }
     }

     customElements.define("cours-card", CoursCard);

     function handleForm(event) { event.preventDefault(); }

     function clickOnCours(element){

        element.style.color = "red";

         myForm = document.forms["myForm"];

         action = document.createElement('input');
         action.type = 'hidden';
         action.name = "action";
         action.value = "addCours";

         coursName = document.createElement('input');
         coursName.type = 'hidden';
         coursName.name = "coursName";
         coursName.value = element.id;

         myForm.append(action);
         myForm.append(coursName);

         myForm.submit();
     }

 </script>




 <script>
     const grid = document.getElementsByClassName("scrollable-cours").item(0);
     const AllCoursCards = [];
 </script>


     <script>

         function clearEvents(){

             myForm = document.forms["myForm"];

             action = document.createElement('input');
             action.type = 'hidden';
             action.name = "action";
             action.value = "clearAllCours";

             myForm.append(action);
             myForm.submit();
         }

     </script>

 <%------------------- Adding cours loop -------------------%>


     <%------------------- Selection de cours -------------------%>


     <div class="section">

         <form action="SimulateurHoraireServlet" method="post" name="myForm"></form>

         <div class="zone-cours">
             <div class="scrollable-cours">

 <% for (Cours cours : coursRepository.getCours()){ %>
     <%
         String coursName = cours.getName();
         String credit = cours.getCredits();
         String campus = cours.getCampus();
         String periode = cours.getPeriode();
     %>

                 <script>
                     cardColor = "white";
                     cardTextColor = "black";
                      <% if (selectedCours.contains(coursName)){%>
                        cardColor = "#5667a2";
                        cardTextColor = "white";
                     <%}%>

              </script>
              <cours-card id="<%=coursName%>" onclick="clickOnCours(this)">
                     <span slot="name"><%=coursName%></span>
                     <span slot="credits"><%=credit%></span>
                     <span slot="campus"><%=campus%></span>
                     <span slot="periode"><%=periode%></span>
                 </cours-card>

<%
    }
%>

             </div>
         </div>
         <button id="clearEvents" class="clearEvents" onclick="clearEvents()">Clear</button>

     </div>



     <div class="section">
         <div id="Buttons-Sessions" class="center-button">
             <button id="Selection-Automne" class="button-session-choisi">Automne</button>
             <button id="Selection-Hiver" class="button-session">Hiver</button>
             <button id="Selection-Ete" class="button-session">Ete</button>
         </div>
         <iframe src="https://calendar.google.com/calendar/embed?height=750&wkst=1&ctz=America%2FToronto&bgcolor=%23ffffff&mode=WEEK&showTitle=0&showDate=0&showCalendars=0&showTz=0&showTabs=0&showPrint=0&src=OWNiMzQyMDE0YzQ4NGJhNGVlOTI3MWM1MTIwODU4NTFlYjM0YmQyYTQ0MzQ5ZDhmYWNjYzFlYjAyYzViMGEzOUBncm91cC5jYWxlbmRhci5nb29nbGUuY29t&color=%233F51B5" style="border:solid 1px #777" width="800" height="750" frameborder="0" scrolling="no"></iframe>

     </div>

 </div>





</body>
</html>
