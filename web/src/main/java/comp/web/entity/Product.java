/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.web.entity;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pojo class.
 * @author Ivan
 */
@Entity
@Table(name = "PROD")
@NamedQuery(name = "priceList",
        query = "from Product p "
                + " where lower(p.cat.name) like lower(:cat)"
                + " and lower(p.name) like lower(:prod)"
                + " and p.price between :from and :to")
@Data
@NoArgsConstructor
public class Product implements Serializable{
    @Id
    @GeneratedValue
    private int id;

    @ManyToOne( cascade = {CascadeType.PERSIST, CascadeType.MERGE} )
    @JoinColumn(name="CAT_ID")
    private Category cat;

    private String name;

    @Column(precision = 16, scale = 2)
    private Double price;

    public Product(Category cat, String name, double price) {
        this.cat = cat;
        this.name = name;
        this.price = price;
    }
}
