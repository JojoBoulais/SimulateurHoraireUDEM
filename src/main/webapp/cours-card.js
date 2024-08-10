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
                box-shadow: 0 1px 6px black;
            }
    
            </style>
        
            <div class="cours-card">
                        <div class="infos">
                        <h2>name</h2>
                        <p>Credits: </p>
                        <p>campus:</p>
                        <p>Mode Enseignement: </p>
                        <p>periode: </p>
                </div>
            </div>
        `
    }



}


customElements.define("cours-card", CoursCard)
