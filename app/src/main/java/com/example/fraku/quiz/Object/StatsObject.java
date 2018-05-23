package com.example.fraku.quiz.Object;

public class StatsObject {

    private String IdQue;
    private int Progress;
    private int GoodAnswer;
    private int BadAnswer;
    private int Percent;

    public StatsObject(String CatId, int Progress, int GoodAnswer, int BadAnswer, int Percent){
        this.IdQue = CatId;
        this.Progress = Progress;
        this.GoodAnswer = GoodAnswer;
        this.BadAnswer = BadAnswer;
        this.Percent = Percent;
    }

    public String getCatId() {
        return IdQue;
    }
    public void setCatId(String IdQue) {
        IdQue = IdQue;
    }

    public int getProgress() {
        return Progress;
    }
    public void setProgress(int progress) {
        Progress = progress;
    }

    public int getGoodAnswer() {
        return GoodAnswer;
    }
    public void setGoodAnswer(int goodAnswer) {
        GoodAnswer = goodAnswer;
    }

    public int getBadAnswer() {
        return BadAnswer;
    }
    public void setBadAnswer(int badAnswer) {
        BadAnswer = badAnswer;
    }

    public int getPercent() {
        return Percent;
    }
    public void setPercent(int percent) {
        Percent = percent;
    }

}
