/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package comp.web.entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Pojo class. Category of product
 * @author Ivan
 */
@Entity
@Table(name = "CAT")
@Data
@NoArgsConstructor
public class Category implements Serializable{
    @Id
    @GeneratedValue
    private int id;

    private String name;

    public Category(String name) {
        this.name = name;
    }
}
