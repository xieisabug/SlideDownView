package com.xjy.slidedownview;

public class Bounce {

    public static Bounce OUT = new Bounce() {
        @Override
        public float getValue(float currentTime, float beginValue, float changeValue, float duringTime) {
            if ((currentTime/=duringTime) < (1/2.75)) {
                return changeValue*(7.5625f*currentTime*currentTime) + beginValue;
            } else if (currentTime < (2/2.75)) {
                return changeValue*(7.5625f*(currentTime-=(1.5/2.75))*currentTime + .75f) + beginValue;
            } else if (currentTime < (2.5/2.75)) {
                return changeValue*(7.5625f*(currentTime-=(2.25/2.75))*currentTime + .9375f) + beginValue;
            } else {
                return changeValue*(7.5625f*(currentTime-=(2.625/2.75))*currentTime + .984375f) + beginValue;
            }
        }
    };

    public float getValue(float currentTime, float beginValue, float changeValue, float duringTime){
        return 0;
    }
}
