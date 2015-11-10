/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.web.persist;

import comp.web.core.DataUtil;
import comp.web.entity.Category;
import comp.web.entity.Product;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import org.apache.commons.lang3.StringUtils;
import org.fluttercode.datafactory.impl.DataFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

/**
 * Test core without run tomcat
 * @author Ivan
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PriсeListTest {

    static EntityManagerFactory emf;
    static EntityManager em ;
    static DataUtil du;

    @BeforeClass
    public static void init() throws IOException {
        emf = Persistence.createEntityManagerFactory("testJPA");  //webJPA testJPA
        DataUtil.setEmf(emf);
        em = emf.createEntityManager();
        du = new DataUtil();
    }

    @Test
    public void test1SaveAndRemove() {

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Category c = new Category("Принтеры");
        Product p = new Product(c, "Принтер Xerox", 5000.99);

        em.persist(p);
        tx.commit();

        EntityTransaction readTx = em.getTransaction();
        readTx.begin();

        Product p2 = em.find(Product.class, p.getId());
        assertNotNull(p2);
        assertEquals("Принтеры", p2.getCat().getName());
        assertEquals("Принтер Xerox", p2.getName());
        assertEquals(5000.99, p2.getPrice(), 0.1);
        readTx.commit();

        EntityTransaction delTx = em.getTransaction();
        delTx.begin();

        em.remove(p2);
        em.remove(p2.getCat());
        delTx.commit();
    }

    @Test
    public void test2InsertManyProducts() {
        DataFactory df = new DataFactory();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        Category cp = new Category("Принтеры");
        Category cr = new Category("Картриджы");
        Category cw = new Category("Бумага");

        em.persist(cp);
        em.persist(cr);
        em.persist(cw);

        for(int i = 0; i <= 100; i++){

            Category c = df.getItem(Arrays.asList(cp, cr, cw));

            List<String> l = Arrays.asList("Xerox", "HP", "Epson", "Panasonic");
            List<String> lw = Arrays.asList("Xerox", "Снегурочка", "Фотобумага");

            String name = "";
            double price = 0;
            switch(c.getName()) {
                case "Принтеры": name = "Принтер "+df.getItem(l)+" "+df.getRandomWord();
                    price = df.getNumberBetween(3000, 210_000);
                    break;
                case "Картриджы": name = "Картридж для "+df.getItem(l)+" "+df.getRandomWord();
                    price = df.getNumberBetween(500, 10_000);
                    break;
                case "Бумага": name = "Бумага "+df.getItem(lw)+" "+df.getRandomWord();
                    price = df.getNumberBetween(100, 2_000);
                    break;
                default:
            }

            if(df.chance(30)) {
                price +=0.99;
            }

            if(df.chance(20)) {
                if(df.chance(50)) {
                    name = name.toLowerCase();
                } else {
                    name = name.toUpperCase();
                }
            }
            Product p = new Product(c, name, price);
            System.out.println(p.toString());
            em.persist(p);
        }

        tx.commit();
    }

    @Test
    public void test3PriceList() throws InterruptedException {

        assertEquals(1, testFilter("Принт", null, null, null));
        assertEquals(1, testFilter("принт", "", "", ""));
        assertEquals(1, testFilter("Принт", "принт", "", ""));
        assertEquals(1, testFilter("Принт", "принт", "100000", ""));
        assertEquals(1, testFilter("Принт", "", "", "100000"));
        assertEquals(1, testFilter("Принт", "принтер", "50000", "150000"));

        //empty result
        assertEquals(0, testFilter("", "", "", ""));
        assertEquals(0, testFilter("Принт", "Карт", "", ""));

    }

    /**
     * @param cat category name
     * @param prod product name
     * @param from price from
     * @param to price to
     * @return 1- result equals filer, 0 - result is empty, -1 - result not equals filer
     */
    public int testFilter(String cat, String prod, String from, String to) {

        List<Product> prods = du.getProds(cat, prod, from, to);

        if (prods.isEmpty())
            return 0;

        boolean r1 = StringUtils.isBlank(cat)
                || prods.stream()
                .map(x -> x.getCat().getName()) // get cell width category
                .allMatch(x -> StringUtils.startsWithIgnoreCase(x, cat));

        boolean r2 = StringUtils.isBlank(prod)
                || prods.stream()
                .map(x -> x.getName())
                .allMatch(x -> StringUtils.startsWithIgnoreCase(x, prod));

        boolean r3 = StringUtils.isBlank(from)
                || prods.stream()
                .map(x -> x.getPrice())
                .allMatch(x -> x > Double.parseDouble(from));

        boolean r4 = StringUtils.isBlank(to)
                || prods.stream()
                .map(x -> x.getPrice())
                .allMatch(x -> x < Double.parseDouble(to));

        return (r1 && r2 && r3 && r4) ? 1 : -1;
    }
}
