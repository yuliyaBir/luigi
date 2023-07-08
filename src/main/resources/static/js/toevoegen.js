"use strict"
import {byId, verberg, toon, setText, verwijderChildElementenVan} from "./util.js";

byId("toevoegen").onclick = async function (){
    verbergPizzaEnFouten();
    var inputNaam = byId("naam");
    var inputPrijs = byId("prijs");
    if (! inputNaam.checkValidity()){
        toon("naamFout");
        inputNaam.focus();
    }
    if (!inputPrijs.checkValidity()){
        toon("prijsFout");
        inputPrijs.focus();
    }
    const pizza = {
        naam: inputNaam.value,
        prijs: inputPrijs.value
    };
    voegToe(pizza);
}

function verbergPizzaEnFouten (){
    verberg("pizzaTable");
    verberg("naamFout");
    verberg("prijsFout");
    verberg("storing");
}
async function voegToe(pizza){
    var response = await fetch("pizzas",
        {method: "POST",
            headers: {'Content-Type': "application/json"},
            body: JSON.stringify(pizza)
        });
    if (response.ok){
        window.location = "allepizzas.html";
    } else{
        toon("storing");
    }
}