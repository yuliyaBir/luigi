package be.vdab.luigi.repositories;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PizzaRepository {
    private final JdbcTemplate jdbcTemplate;

    public PizzaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public long findAantal(){
        var sql = """
                select count(*) as aantalPizzas
                from pizzas
                """;
        return jdbcTemplate.queryForObject(sql, Long.class);
    }
}
