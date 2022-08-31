package com.cocktail_app.cocktail.Models;

import javax.persistence.*;

@Entity(name = "CocktailDB")
@Table(name = "Cocktails")
public class CocktailDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cocktail_id")
    private int id;
    private String name;
    private String tools;

    private int difficulty;
    private String instructions;
    private String tags;
    private String glassType;
    private String iceType;

    // empty constructor
    CocktailDB() {}

    // constructor with all items
    public CocktailDB(String name, String tools, int difficulty, String instructions, String tags) {
        this.name = name;
        this.tools = tools;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.tags = tags;
    }

    // constructor with only non-null / required items
    public CocktailDB(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getTools() {
        return tools;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public String getTags() {
        return tags;
    }

    public String getGlassType() {
        return glassType;
    }

    public String getIceType() {
        return iceType;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setGlassType(String glassType) {
        this.glassType = glassType;
    }

    public void setIceType(String iceType) {
        this.iceType = iceType;
    }

    @Override
    public String toString() {
        return "Cocktail{" +
                "name='" + name + '\'' +
                ", tools='" + tools + '\'' +
                ", difficulty=" + difficulty +
                ", instructions='" + instructions + '\'' +
                ", tags='" + tags + '\'' +
                ", glassType='" + glassType + '\'' +
                ", iceType='" + iceType + '\'' +
                '}';
    }
}
