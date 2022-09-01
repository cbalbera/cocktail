package com.cocktail_app.cocktail.Models;

import java.util.List;

public class CocktailDTO {

    public enum Difficulty{
        VERY_EASY,
        EASY,
        MODERATE,
        DIFFICULT
    }

    private Long id;
    private String name;
    private String tools;
    private Difficulty difficulty;
    private List<String> instructions;
    private String tags;
    private String glassType;
    private String iceType;
    private Boolean isParent;
    private String childrenIDs;
    private Boolean isChild;
    private Long parentID;

    // empty constructor
    CocktailDTO() {}

    // constructor with all items
    public CocktailDTO(Long id, String name, String tools, Difficulty difficulty, List<String> instructions, String tags, String glassType, String iceType, Boolean isParent, String childrenIDs, Boolean isChild, Long parentID) {
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

    // constructor with only non-null / required items
    public CocktailDTO(String name, List<String> instructions) {
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

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public List<String> getInstructions() {
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

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public void setInstructions(List<String> instructions) {
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
