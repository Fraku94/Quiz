package com.example.fraku.quiz.Object;

public class AnswerObject {

    private String IdQue;
    private String Question;
    private String Answer;
    private String QuestionResult;
    private String ImageUrl;
    private String QuestionNum;
    private String AnswerNum;

    public AnswerObject(String IdQue, String Question, String Answer, String QuestionResult, String ImageUrl, String QuestionNum, String AnswerNum){
        this.IdQue = IdQue;
        this.Question = Question;
        this.Answer = Answer;
        this.QuestionResult = QuestionResult;
        this.ImageUrl = ImageUrl;
        this.QuestionNum = QuestionNum;
        this.AnswerNum = AnswerNum;
    }

    public String getIdQue() {
        return IdQue;
    }
    public void setIdQue(String IdQue) {
        IdQue = IdQue;
    }

    public String getQuestion() {
        return Question;
    }
    public void setQuestion(String Question) {
        Question = Question;
    }

    public String getAnswer() {
        return Answer;
    }
    public void setAnswer(String Answer) {
        Answer = Answer;
    }

    public String getQuestionResult() {
        return QuestionResult;
    }
    public void setQuestionResult(String QuestionResult) {
        QuestionResult = QuestionResult;
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

    public String getAnswerNum() {
        return AnswerNum;
    }
    public void setAnswerNum(String AnswerNum) {
        AnswerNum = AnswerNum;
    }


}
