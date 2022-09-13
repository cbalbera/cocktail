package com.cocktail_app.cocktail.Models;

import javax.persistence.*;

@Entity(name = "CocktailDB")
@Table(name = "COCKTAILS")
public class CocktailDB {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cocktail_id")
    private Long id;
    private String name;
    private String tools;
    private int difficulty;
    private String instructions;
    private String tags;
    private String glassType;
    private String iceType;
    private Boolean isParent = false;
    private String childrenIDs;
    private Boolean isChild = false;
    private Long parentID;
    //TODO add bartenderID
    //TODO: a field for "classic" or no bartenderID = classic?
    //TODO: a field of List<String> for thumbnailIDs

    // empty constructor
    CocktailDB() {}

    // constructor with all items
    public CocktailDB(Long id, String name, String tools, int difficulty, String instructions, String tags, String glassType, String iceType, Boolean isParent, String childrenIDs, Boolean isChild, Long parentID) {
        this.id = id;
        this.name = name;
        this.tools = tools;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.tags = tags;
        this.glassType = glassType;
        this.iceType = iceType;
        this.isParent = isParent;
        this.childrenIDs = childrenIDs;
        this.isChild = isChild;
        this.parentID = parentID;
    }

    // constructor with all items except for ID
    public CocktailDB(String name, String tools, int difficulty, String instructions, String tags, String glassType, String iceType, Boolean isParent, String childrenIDs, Boolean isChild, Long parentID) {
        this.name = name;
        this.tools = tools;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.tags = tags;
        this.glassType = glassType;
        this.iceType = iceType;
        this.isParent = isParent;
        this.childrenIDs = childrenIDs;
        this.isChild = isChild;
        this.parentID = parentID;
    }

    // constructor with only non-null / required items
    public CocktailDB(String name, String instructions) {
        this.name = name;
        this.instructions = instructions;
    }

    public Long getId() { return id; }

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

    public Boolean getIsParent() {
        return isParent;
    }

    public String getChildrenIDs() {
        return childrenIDs;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public Long getParentID() {
        return parentID;
    }

    public void setId(Long id) {
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

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }

    public void setChildrenIDs(String childrenIDs) {
        this.childrenIDs = childrenIDs;
    }

    public void setIsChild(Boolean child) {
        isChild = child;
    }

    public void setParentID(Long parentID) {
        this.parentID = parentID;
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
