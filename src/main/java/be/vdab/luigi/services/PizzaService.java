package be.vdab.luigi.services;

import be.vdab.luigi.domain.Pizza;
import be.vdab.luigi.domain.PizzaPrijs;
import be.vdab.luigi.dto.NieuwePizza;
import be.vdab.luigi.exceptions.PizzaBestaatAlException;
import be.vdab.luigi.repositories.PizzaPrijsRepository;
import be.vdab.luigi.repositories.PizzaRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaPrijsRepository pizzaPrijsRepository;

    public PizzaService(PizzaRepository pizzaRepository, PizzaPrijsRepository pizzaPrijsRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPrijsRepository = pizzaPrijsRepository;
    }
    public long findAantal(){
        return pizzaRepository.findAantal();
    }
    public Optional<Pizza> findById(long id) {
        return pizzaRepository.findById(id);
    }
    public List<Pizza> findAll(){
        return pizzaRepository.findAll();
    }
    public List<Pizza> findByNaamBevat(String woord){
        return pizzaRepository.findByNaamBevat(woord);
    }
    public List<Pizza> findByPrijsTussen(BigDecimal van, BigDecimal tot){
        return pizzaRepository.findByPrijsTussen(van, tot);
    }
    @Transactional
    public void delete(long id){
        pizzaRepository.delete(id);
    }
    @Transactional
    public long create(NieuwePizza nieuwePizza){
        try {
            var winst = nieuwePizza.prijs().multiply(BigDecimal.valueOf(0.1));
            var pizza = new Pizza(nieuwePizza.naam(), nieuwePizza.prijs(), winst);
            return pizzaRepository.create(pizza);
        } catch (DuplicateKeyException ex){
            throw new PizzaBestaatAlException(nieuwePizza.naam());
        }

    }
    @Transactional
    public void updatePrijs(PizzaPrijs pizzaPrijs){
        pizzaRepository.updatePrijs(pizzaPrijs.getPizzasId(), pizzaPrijs.getPrijs());
        pizzaPrijsRepository.create(pizzaPrijs);
    }
}
