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
    private String childrenIds;
    private Boolean isChild = false;
    private Long parentId;
    private Long bartenderId;
    private String thumbnails;
    //TODO: a field for "classic" or no bartenderID = classic?

    // empty constructor
    CocktailDB() {}

    // constructor with all items


    public CocktailDB(Long id, String name, String tools, int difficulty, String instructions, String tags, String glassType, String iceType, Boolean isParent, String childrenIds, Boolean isChild, Long parentId, Long bartenderId, String thumbnails) {
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

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
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

    public String getThumbnails() {
        return thumbnails;
    }

    public void setThumbnails(String thumbnails) {
        this.thumbnails = thumbnails;
    }
}
