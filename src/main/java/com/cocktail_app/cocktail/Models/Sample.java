package com.cocktail_app.cocktail.Models;



import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "Sample")
@Table(name="SAMPLE")
public class Sample {
    private @Id @GeneratedValue int id;
    private String name;


    Sample() {}

    public Sample(String name) {
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}
