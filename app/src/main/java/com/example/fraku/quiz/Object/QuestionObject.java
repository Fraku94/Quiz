package com.example.fraku.quiz.Object;

public class QuestionObject {

    private String CatId;
    private String Pytanie;
    private String Odpowiedz;
    private String WynikOpd;
    private String ImageUrl;
    private String NumerPyt;
    private String NumerOdp;




    public QuestionObject(String CatId, String Pytanie, String Odpowiedz, String WynikOpd, String ImageUrl, String NumerPyt, String NumerOdp){
        this.CatId = CatId;
        this.Pytanie = Pytanie;
        this.Odpowiedz = Odpowiedz;
        this.WynikOpd = WynikOpd;
        this.ImageUrl = ImageUrl;
        this.NumerPyt = NumerPyt;
        this.NumerOdp = NumerOdp;
    }

    public String getCatId() {
        return CatId;
    }
    public void setCatId(String CatId) {
        CatId = CatId;
    }

    public String getPytanie() {
        return Pytanie;
    }
    public void setPytanie(String Pytanie) {
        Pytanie = Pytanie;
    }

    public String getOdpowiedz() {
        return Odpowiedz;
    }
    public void setOdpowiedz(String Questions) {
        Questions = Questions;
    }

    public String getWynikOpd() {
        return WynikOpd;
    }
    public void setWynikOpd(String WynikOpd) {
        WynikOpd = WynikOpd;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getNumerPyt() {
        return NumerPyt;
    }
    public void setNumerPyt(String NumerPyt) {
        NumerPyt = NumerPyt;
    }

    public String getNumerOdp() {
        return NumerOdp;
    }
    public void setNumerOdp(String NumerOdp) {
        NumerOdp = NumerOdp;
    }


}
