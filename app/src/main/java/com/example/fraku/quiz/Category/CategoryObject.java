package com.example.fraku.quiz.Category;

public class CategoryObject {

    private String Id;
    private String Name;
    private String ImageUrl;



    public CategoryObject (String Id, String Name, String ImageUrl){
        this.Id = Id;
        this.Name = Name;
        this.ImageUrl = ImageUrl;
    }

    public String getId() {
        return Id;
    }
    public void setId(String id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }
    public void setName(String name) {
        Name = name;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

}
