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
            margin-top: 20px;
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
 <div class="page-container">

 <div class="section">
     <div id="Buttons-Sessions" class="center-button">
         <button id="Selection-Automne" class="button-session-choisi">Automne</button>
         <button id="Selection-Hiver" class="button-session">Hiver</button>
         <button id="Selection-Ete" class="button-session">Ete</button>
     </div><br>
 </div>

 <div class="section"><iframe src="https://calendar.google.com/calendar/embed?height=500&wkst=1&ctz=America%2FToronto&bgcolor=%23ffffff&showNav=0&showDate=0&showPrint=0&showCalendars=0&mode=WEEK&showTz=0&showTabs=0&title&showTitle=0&src=OWNiMzQyMDE0YzQ4NGJhNGVlOTI3MWM1MTIwODU4NTFlYjM0YmQyYTQ0MzQ5ZDhmYWNjYzFlYjAyYzViMGEzOUBncm91cC5jYWxlbmRhci5nb29nbGUuY29t&color=%23A79B8E" style="border-width:0" width="800" height="500" frameborder="0" scrolling="no"></iframe></div>

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
                display: flex;
                margin: 5px;
                border-radius: 5px;
                border-color: black;
                background-color: white;
                width: 225px;
                height: 200px;
            } .infos{
                margin: 10px;
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
                        <p class="cours-title" id="cours-name">#Name#</p>
                        <p class="cours-infos" id="cours-credit">Credits: <slot name="credits"/>50</p>
                        <p class="cours-infos" id="cours-campus">Campus: <slot name="campus"/></p>
                        <p class="cours-infos" id="cours-periode">Periode: <slot name="periode"/></p>
                </div>
            </div>
        `;
         }
     }

     customElements.define("cours-card", CoursCard)

 </script>

 <script>
     const grid = document.getElementsByClassName("scrollable-cours").item(0);
     const AllCoursCards = [];
 </script>

 <%------------------- Adding cours loop -------------------%>

 <% for (Cours cours : coursRepository.getCours()){ %>
     <%
         String coursName = cours.getName();
         String credit = cours.getCredits();
         String campus = cours.getCampus();
         String periode = cours.getPeriode();
     %>


     <script>
         newItem = new CoursCard("<%=coursName%>", "<%=credit%>", "<%=campus%>", "<%=periode%>");

         newItem.slot.replace("credits", "<%=credit%>");

         AllCoursCards.push(newItem);

         grid.innerHTML += newItem.outerHTML;
     </script>

<%
    }
%>



</body>
</html>
