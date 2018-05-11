package com.example.fraku.quiz.Object;

public class CategoryObject {

    private String IdCat;
    private String TytulCat;
    private String ImageUrl;
    private String LiczbaPyt;



    public CategoryObject (String IdCat, String TytulCat, String ImageUrl, String LiczbaPyt){
        this.IdCat = IdCat;
        this.TytulCat = TytulCat;
        this.ImageUrl = ImageUrl;
        this.LiczbaPyt = LiczbaPyt;
    }

    public String getIdCat() {
        return IdCat;
    }
    public void setIdCat(String IdCat) {
        IdCat = IdCat;
    }

    public String getTytulCat() {
        return TytulCat;
    }
    public void setTytulCat(String TytulCat) {
        TytulCat = TytulCat;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getLiczbaPyt() {
        return LiczbaPyt;
    }
    public void setLiczbaPyt(String LiczbaPyt) {
        LiczbaPyt = LiczbaPyt;
    }

}
