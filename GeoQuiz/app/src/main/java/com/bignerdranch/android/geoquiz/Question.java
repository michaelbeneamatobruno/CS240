package com.bignerdranch.android.geoquiz;

public class Question {
    private int mTextResID;
    private boolean mAnswerTrue;

    public Question(int testResId, boolean answerTrue) {
        mTextResID = testResId;
        mAnswerTrue = answerTrue;
    }

    public int getTextResID() {
        return mTextResID;
    }
    public void setTextResID(int textResID) {
        mTextResID = textResID;
    }
    public boolean isAnswerTrue() {
        return mAnswerTrue;
    }
    public void setAnswerTrue(boolean answerTrue) {
        mAnswerTrue = answerTrue;
    }
}
