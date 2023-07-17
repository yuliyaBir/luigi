package be.vdab.luigi.controllers;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.domain.PizzaPrijs;
import be.vdab.luigi.dto.NieuwePizza;
import be.vdab.luigi.exceptions.PizzaNietGevondenException;
import be.vdab.luigi.services.PizzaService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.stream.Stream;

@RestController
@RequestMapping("pizzas")
public class PizzaController {
    private final PizzaService pizzaService;

    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }
    private record IdNaamPrijs(long id, String naam, BigDecimal prijs) {
        IdNaamPrijs(Pizza pizza) {
            this(pizza.getId(), pizza.getNaam(), pizza.getPrijs());
        }
    }
    private record PrijsWijziging(@NotNull @PositiveOrZero BigDecimal prijs){}

    @GetMapping("aantal")
    long findAantal() {
        return pizzaService.findAantal();
    }

    @GetMapping("{id}")
    IdNaamPrijs findById(@PathVariable long id) {
        return pizzaService.findById(id)
                .map(pizza -> new IdNaamPrijs(pizza))
                .orElseThrow(() ->
                new PizzaNietGevondenException(id));
    }
    @GetMapping
    Stream<IdNaamPrijs> findAll(){
        return pizzaService.findAll()
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }
    @GetMapping(params = "naamBevat")
    Stream<IdNaamPrijs> findByNaamBevat(String naamBevat) {
        return pizzaService.findByNaamBevat(naamBevat)
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }

    @GetMapping(params = {"vanPrijs", "totPrijs"})
    Stream<IdNaamPrijs> findByPrijsTussen(BigDecimal vanPrijs, BigDecimal totPrijs) {
        return pizzaService.findByPrijsTussen(vanPrijs, totPrijs)
                .stream()
                .map(pizza -> new IdNaamPrijs(pizza));
    }

    @DeleteMapping("{id}")
    void delete(@PathVariable long id) {
        pizzaService.delete(id);
    }

    @PostMapping
    long create(@RequestBody @Valid NieuwePizza nieuwePizza) {
        return pizzaService.create(nieuwePizza);
    }

    @PatchMapping("{id}/prijs")
    void updatePrijs(@PathVariable long id, @RequestBody @Valid PrijsWijziging wijziging){
        var pizzaPrijs = new PizzaPrijs(wijziging.prijs,id);
        pizzaService.updatePrijs(pizzaPrijs);
    }

    private record PrijsVanaf (BigDecimal prijs, LocalDateTime vanaf){
        private PrijsVanaf (PizzaPrijs pizzaPrijs){
            this(pizzaPrijs.getPrijs(), pizzaPrijs.getVanaf());
        }
    }
    @GetMapping("{id}/prijzen")
    Stream<PrijsVanaf> findPrijzen(@PathVariable long id){
        return pizzaService.findPrijzen(id)
                .stream()
                .map(pizzaPrijs -> new PrijsVanaf(pizzaPrijs));
    }



    //    @GetMapping("pizzas/verkoop/{jaar}/{maand}/{dag}")
//    BigDecimal verkoop(
//            @PathVariable int jaar,
//            @PathVariable int maand,
//            @PathVariable int dag) {
//    }



}
