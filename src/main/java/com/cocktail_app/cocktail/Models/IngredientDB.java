package com.cocktail_app.cocktail.Models;

import javax.persistence.*;

@Entity(name = "IngredientDB")
@Table(name = "INGREDIENTS")
public class IngredientDB {

    @Id
    @SequenceGenerator(
            name = "cocktail_sequence",
            sequenceName = "cocktail_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "cocktail_sequence"
    )
    private Long id;
    private String name;
    private int type;

    // empty constructor
    IngredientDB() {}

    // constructor with all items
    public IngredientDB(Long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    // constructor with all items except ID
    public IngredientDB(String name, int type) {
        this.name = name;
        this.type = type;
    }

    // constructor with only non-null / required items
    public IngredientDB(String name) {
        this.name = name;
    }

    public Long getId() { return id; }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "IngredientDB{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
