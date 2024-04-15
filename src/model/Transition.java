package model;

import enums.TransitionType;

import java.util.Date;

public class Transition {
    private TransitionType transitionType ;

    public TransitionType getTransitionType() {
        return transitionType;
    }

    public void setTransitionType(TransitionType transitionType) {
        this.transitionType = transitionType;
    }

    public Transition() {
    }
}