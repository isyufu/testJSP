/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.web.core;

import comp.web.entity.Product;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author Ivan
 */
public class DataUtil {

    private static EntityManagerFactory emf;

    private static final Logger logger = Logger.getLogger("DataUtil");

    public static void setEmf(EntityManagerFactory emf) {
        DataUtil.emf = emf;
    }

    public EntityManager createEM() {
        logger.finer("creating Entity Manager");
        return emf.createEntityManager();
    }


    public List<Product> getProds(String cat, String prod, String from, String to) {
        logger.log(Level.FINER, "get prods with filter {0} {1} {2} {3}",
                new Object[]{cat, prod, from, to});

        if (StringUtils.isBlank(cat) && StringUtils.isBlank(prod)
                && StringUtils.isBlank(from) && StringUtils.isBlank(to)) {
            return Collections.emptyList();
        }

        String cat1 = StringUtils.stripToEmpty(cat)+"%";
        String prod1 = StringUtils.stripToEmpty(prod)+"%";
        double from1 = StringUtils.isNumeric(from) ?
                Double.parseDouble(from) : Double.MIN_VALUE;
        double to1 = StringUtils.isNumeric(to) ?
                Double.parseDouble(to) : Double.MAX_VALUE;

        EntityManager em = createEM();
//        EntityTransaction tx = em.getTransaction();
//        tx.begin();

        List<Product> products = em
                .createNamedQuery("priceList", Product.class)
                .setParameter("cat", cat1)
                .setParameter("prod", prod1)
                .setParameter("from", from1)
                .setParameter("to", to1)
                .getResultList();

//        tx.commit();
        em.close();
        logger.log(Level.FINER, "get prods result size {0}", products.size());
        return products;
    }
}
