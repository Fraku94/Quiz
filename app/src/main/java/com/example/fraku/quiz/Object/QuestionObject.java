package com.example.fraku.quiz.Object;

public class QuestionObject {

    private String IdQue;
    private String TitleQue;
    private String ImageUrl;
    private String QuestionNum;



    public QuestionObject(String IdQue, String TitleQue, String ImageUrl, String QuestionNum){
        this.IdQue = IdQue;
        this.TitleQue = TitleQue;
        this.ImageUrl = ImageUrl;
        this.QuestionNum = QuestionNum;
    }

    public String getIdQue() {
        return IdQue;
    }
    public void setIdQue(String IdQue) {
        IdQue = IdQue;
    }

    public String getTitleQue() {
        return TitleQue;
    }
    public void setTitleQue(String TitleQue) {
        TitleQue = TitleQue;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getQuestionNum() {
        return QuestionNum;
    }
    public void setQuestionNum(String QuestionNum) {
        QuestionNum = QuestionNum;
    }

}
