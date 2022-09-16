package com.cocktail_app.cocktail.Models;

import java.util.List;
import java.util.Map;

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
    private Boolean isParent = false;
    private String childrenIds;
    private Boolean isChild  = false;
    private Long parentId;
    private Long bartenderId;
    private List<String> thumbnails;

    private Map<IngredientDTO, Boolean> ingredients;

    private int numIngredientsInBar = 0;

    // empty constructor
    CocktailDTO() {}

    // constructor with all items


    public CocktailDTO(Long id, String name, String tools, Difficulty difficulty, List<String> instructions, String tags, String glassType, String iceType, Boolean isParent, String childrenIds, Boolean isChild, Long parentId, Long bartenderId, List<String> thumbnails) {
        this.id = id;
        this.name = name;
        this.tools = tools;
        this.difficulty = difficulty;
        this.instructions = instructions;
        this.tags = tags;
        this.glassType = glassType;
        this.iceType = iceType;
        this.isParent = isParent;
        this.childrenIds = childrenIds;
        this.isChild = isChild;
        this.parentId = parentId;
        this.bartenderId = bartenderId;
        this.thumbnails = thumbnails;
        this.numIngredientsInBar = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTools() {
        return tools;
    }

    public void setTools(String tools) {
        this.tools = tools;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public List<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(List<String> instructions) {
        this.instructions = instructions;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getGlassType() {
        return glassType;
    }

    public void setGlassType(String glassType) {
        this.glassType = glassType;
    }

    public String getIceType() {
        return iceType;
    }

    public void setIceType(String iceType) {
        this.iceType = iceType;
    }

    public Boolean getIsParent() {
        return isParent;
    }

    public void setIsParent(Boolean parent) {
        isParent = parent;
    }

    public String getChildrenIds() {
        return childrenIds;
    }

    public void setChildrenIds(String childrenIds) {
        this.childrenIds = childrenIds;
    }

    public Boolean getIsChild() {
        return isChild;
    }

    public void setIsChild(Boolean child) {
        isChild = child;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getBartenderId() {
        return bartenderId;
    }

    public void setBartenderId(Long bartenderId) {
        this.bartenderId = bartenderId;
    }

    public List<String> getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(List<String> thumbnails) {
        this.thumbnails = thumbnails;
    }

    public void setIngredients(Map<IngredientDTO, Boolean> ingredients) { this.ingredients = ingredients; }

    public Map<IngredientDTO, Boolean> getIngredients() {return ingredients;}

    public void setNumIngredientsInBar(int numIngredientsInBar) {this.numIngredientsInBar = numIngredientsInBar; }

    public int getNumIngredientsInBar() {return numIngredientsInBar; }

    public void incrementNumIngredientsInBar() {
        numIngredientsInBar +=1;
    }

    @Override
    public String toString() {
        return "CocktailDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", tools='" + tools + '\'' +
                ", difficulty=" + difficulty +
                ", instructions=" + instructions +
                ", tags='" + tags + '\'' +
                ", glassType='" + glassType + '\'' +
                ", iceType='" + iceType + '\'' +
                ", isParent=" + isParent +
                ", childrenIds='" + childrenIds + '\'' +
                ", isChild=" + isChild +
                ", parentId=" + parentId +
                ", bartenderId=" + bartenderId +
                ", thumbnails=" + thumbnails +
                '}';
    }
}
