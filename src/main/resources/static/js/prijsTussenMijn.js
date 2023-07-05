"use strict"
import {toon, verberg, byId, verwijderChildElementenVan, setText} from "./util.js";
byId("zoek").onclick = async function (){
    verbergPizzasEnFouten;
    const inputVanPrijs = byId("vanPrijs");
    let vanPrijs;
    let totPrijs;
    if (inputVanPrijs.checkValidity()){
         vanPrijs = inputVanPrijs.value;
    } else {
        toon("vanFout");
        inputVanPrijs.focus();
    }
    const inputTotPrijs = byId("totPrijs");
    if (inputTotPrijs.checkValidity()){
        totPrijs = inputTotPrijs.value;
    } else{
        toon("totFout");
        inputTotPrijs.focus();
    }
    // toon("pizzasTable");
    findByTussenPrijs(vanPrijs, totPrijs);
}
function verbergPizzasEnFouten(){
    verberg("vanFout");
    verberg("totFout");
    verberg("pizzasTable");
    verberg("storing");
}
// function validatie(element){
//
// }

async function findByTussenPrijs(van, tot){
    const response = await fetch(`pizzas?vanPrijs=${van}&totPrijs=${tot}`);
    if (response.ok){
        const pizzas = await response.json();
        toon("pizzasTable");
        const pizzasBody = byId("pizzasBody");
        verwijderChildElementenVan(pizzasBody);
        for (const pizza of pizzas){
        const tr = pizzasBody.insertRow();
        tr.insertCell().innerText = pizza.id;
        tr.insertCell().innerText = pizza.naam;
        tr.insertCell().innerText = pizza.prijs;
        }
    }else{
        toon("storing");
    }

}