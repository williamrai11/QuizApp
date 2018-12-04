package com.example.quizapp.quizss.data.local.model;

public class ParentChildCategory {
    private boolean isParent;
    private String categoryName;
    private int id;

    public ParentChildCategory(boolean isParent, String categoryName,int id) {
        this.isParent = isParent;
        this.categoryName = categoryName;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isParent() {
        return isParent;
    }

    public void setParent(boolean parent) {
        isParent = parent;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
