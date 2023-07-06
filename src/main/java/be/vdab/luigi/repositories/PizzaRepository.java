package be.vdab.luigi.repositories;

import be.vdab.luigi.domain.Pizza;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

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
    private final RowMapper<Pizza> pizzaMapper = (result, rowNum) ->
            new Pizza(result.getLong("id"), result.getString("naam"),
                    result.getBigDecimal("prijs"), result.getBigDecimal("winst"));

    public Optional<Pizza> findById(long id) {
        try {
            var sql = """
                    select id, naam, prijs, winst
                    from pizzas
                    where id = ?
                    """;
            return Optional.of(jdbcTemplate.queryForObject(sql, pizzaMapper, id));
        } catch (IncorrectResultSizeDataAccessException ex) {
            return Optional.empty();
        }
    }
    public List<Pizza> findAll(){
        var sql = """
                select id, naam, prijs, winst
                from pizzas
                order by naam;
                """;
        return jdbcTemplate.query(sql, pizzaMapper);
    }
    public List<Pizza> findByNaamBevat (String woord){
        var sql = """
                select id, naam, prijs, winst
                from pizzas
                where naam like ?
                order by naam
                """;
        return jdbcTemplate.query(sql, pizzaMapper, "%" + woord + "%");
    }
    public List<Pizza> findByPrijsTussen(BigDecimal van, BigDecimal tot){
        var sql = """
                select id, naam, prijs, winst
                from pizzas
                where prijs between ? and ?
                order by prijs
                """;
        return jdbcTemplate.query(sql, pizzaMapper, van, tot);
    }
    public void delete(long id){
        var sql = """
                delete from pizzas
                where id = ?
                """;
        jdbcTemplate.update(sql, id);
    }
}
