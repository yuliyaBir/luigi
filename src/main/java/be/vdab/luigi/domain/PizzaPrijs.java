package be.vdab.luigi.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class PizzaPrijs {
    private final BigDecimal prijs;
    private final LocalDateTime vanaf;
    private final long pizzasId;

    public PizzaPrijs(BigDecimal prijs, LocalDateTime vanaf, long pizzasId) {
        this.prijs = prijs;
        this.vanaf = vanaf;
        this.pizzasId = pizzasId;
    }

    public PizzaPrijs(BigDecimal prijs, long pizzasId) {
        this(prijs, LocalDateTime.now(), pizzasId);
    }

    public BigDecimal getPrijs() {
        return prijs;
    }

    public LocalDateTime getVanaf() {
        return vanaf;
    }

    public long getPizzasId() {
        return pizzasId;
    }
}
