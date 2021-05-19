package ru.job4j.hibernate.integrationtest;

import org.apache.commons.dbcp2.BasicDataSource;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class OrdersStoreTest {
    private final BasicDataSource pool = new BasicDataSource();

    @Before
    public void setUp() throws SQLException {
        pool.setDriverClassName("org.hsqldb.jdbcDriver");
        pool.setUrl("jdbc:hsqldb:mem:tests;sql.syntax_pgs=true");
        pool.setUsername("sa");
        pool.setPassword("");
        pool.setMaxTotal(2);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("./db/update_001.sql")))
        ) {
            br.lines().forEach(line -> builder.append(line).append(System.lineSeparator()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        pool.getConnection().prepareStatement(builder.toString()).executeUpdate();
    }

    @After
    public void drop() throws SQLException {
        pool.getConnection().prepareStatement("DROP TABLE orders").executeUpdate();
    }

    @Test
    public void whenSaveOrderAndFindAllOneRowWithDescription() {
        OrdersStore store = new OrdersStore(pool);
        store.save(Order.of("name1", "description1"));
        List<Order> all = (List<Order>) store.findAll();
        assertThat(all.size(), is(1));
        assertThat(all.get(0).getDescription(), is("description1"));
        assertThat(all.get(0).getId(), is(1));
    }

    @Test
    public void whenSaveOrderAndUpdateIt() {
        OrdersStore store = new OrdersStore(pool);
        Order save = store.save(Order.of("name1", "description1"));
        store.update(save.getId(), Order.of("name2", "description2"));
        assertThat(store.findById(save.getId()).getName(), is("name2"));
        assertThat(store.findById(save.getId()).getDescription(), is("description2"));
    }

    @Test
    public void whenSaveOrderAndFindById() {
        OrdersStore store = new OrdersStore(pool);
        Order save = store.save(Order.of("name1", "description1"));
        Order order =  store.findById(save.getId());
        assertThat(order.getDescription(), is("description1"));
    }

    @Test
    public void whenNotSaveOrderAndFindByIdThenResultNull() {
        OrdersStore store = new OrdersStore(pool);
        Order order =  store.findById(1);
        assertNull(order);
    }

    @Test
    public void whenSaveOrderAndFindByName() {
        OrdersStore store = new OrdersStore(pool);
        Order save = store.save(Order.of("name1", "description1"));
        Order order =  store.findByName(save.getName());
        assertThat(order.getDescription(), is("description1"));
    }

    @Test
    public void whenNotSaveOrderAndFindByNameThenResultNull() {
        OrdersStore store = new OrdersStore(pool);
        Order order =  store.findByName("name1");
        assertNull(order);
    }
}
