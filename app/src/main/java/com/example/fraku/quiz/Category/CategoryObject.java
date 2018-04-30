package com.example.fraku.quiz.Category;

public class CategoryObject {

    private String Id;
    private String Title;
    private String ImageUrl;
    private String Questions;



    public CategoryObject (String Id, String Title, String ImageUrl, String Questions){
        this.Id = Id;
        this.Title = Title;
        this.ImageUrl = ImageUrl;
        this.ImageUrl = Questions;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }

    public String getTitle() {
        return Title;
    }
    public void setTitle(String title) {
        Title = title;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getQuestions() {
        return Questions;
    }
    public void setQuestions(String questions) {
        Questions = questions;
    }

}
