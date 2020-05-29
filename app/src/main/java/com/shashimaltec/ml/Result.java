package com.shashimaltec.ml;

import java.text.DecimalFormat;

public class Result {

    private final int mNumber;
    private final float mProbability;
    private final long mTimeCost;
    private final float catprob;
    private final float dogprob;
    private final DecimalFormat df = new DecimalFormat("0.0000");

    public Result(float[] probs, long timeCost) {
        catprob =probs[1];
        dogprob = probs[0];
        mNumber = argmax(probs);
        mProbability = probs[mNumber];
        mTimeCost = timeCost;
    }

    public int getNumber() {
        return mNumber;
    }

    public float getProbability() {
        return mProbability;
    }

    public long getTimeCost() {
        return mTimeCost;
    }

    public String getCatprob() {
        return df.format(catprob);
    }

    public String getDogprob() {
        return df.format(dogprob);
    }

    private static int argmax(float[] probs) {
        int maxIdx = -1;
        float maxProb = 0.0f;
        for (int i = 0; i < probs.length; i++) {
            if (probs[i] > maxProb) {
                maxProb = probs[i];
                maxIdx = i;
            }
        }
        return maxIdx;
    }
}