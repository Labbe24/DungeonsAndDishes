package com.dungeonsanddishes.game;

import com.badlogic.gdx.scenes.scene2d.Stage;

import java.util.ArrayList;

import Framework.BaseActor;

public class Recipe {
    protected float x;
    protected float y;
    private enum State {FOUND, NOT_FOUND}

    private int MAX_CHILI_COUNT = 1;
    private int chiliCount = 0;

    private int MAX_RICE_COUNT = 1;
    private int riceCount = 0;

    private int MAX_FISH_COUNT = 1;
    private int fishCount = 0;

    private ArrayList<ChiliContainer> chiliContainers;
    private ArrayList<RiceContainer> riceContainers;
    private ArrayList<FishContainer> fishContainers;

    public Recipe() {
        this.chiliContainers = new ArrayList<ChiliContainer>();
        this.riceContainers = new ArrayList<RiceContainer>();
        this.fishContainers = new ArrayList<FishContainer>();
    }

    public boolean Finsihed() {
        return this.fishCount == MAX_FISH_COUNT && this.riceCount == MAX_RICE_COUNT && this.chiliCount == MAX_CHILI_COUNT;
    }

    public void incrementChili() {
        if ( this.chiliCount < this.MAX_CHILI_COUNT) {
            this.chiliContainers.get(chiliCount).setState(State.FOUND);
            this.chiliCount += 1;
        }
    }

    public void incrementRice() {
        if (this.riceCount < this.MAX_RICE_COUNT) {
            this.riceContainers.get(riceCount).setState(State.FOUND);
            this.riceCount += 1;
        }
    }

    public void incrementFish() {
        if (this.fishCount < this.MAX_FISH_COUNT) {
            this.fishContainers.get(fishCount).setState(State.FOUND);
            this.fishCount += 1;
        }
    }

    public void display(Stage ui_stage, float x, float y) {
        this.x = x;
        this.y = y;

        for (int i = 0; i < MAX_CHILI_COUNT; i++) {
            this.chiliContainers.add(new ChiliContainer(x+i*40, y));

            if (ui_stage != this.chiliContainers.get(i).getStage()) {
                ui_stage.addActor(this.chiliContainers.get(i));
            }
        }

        for (int i = 0; i < MAX_RICE_COUNT; i++) {
            this.riceContainers.add(new RiceContainer(x + 50 + MAX_CHILI_COUNT*40 + i*40, y));

            if (ui_stage != this.riceContainers.get(i).getStage()) {
                ui_stage.addActor(this.riceContainers.get(i));
            }
        }

        for (int i = 0; i < MAX_FISH_COUNT; i++) {
            this.fishContainers.add(new FishContainer(x + 100 + MAX_RICE_COUNT*40 + MAX_CHILI_COUNT*40 + i*40, y));

            if (ui_stage != this.fishContainers.get(i).getStage()) {
                ui_stage.addActor(this.fishContainers.get(i));
            }
        }
    }

    class ChiliContainer extends BaseActor {
        private State state;

        public void act(float dt) {
            switch (state) {
                case FOUND:
                    this.setTexture("ChiliRoom/chili.png");
                    break;
                case NOT_FOUND:
                    this.setTexture("ChiliRoom/chili_grey.png");
                    break;
            }
        }

        public ChiliContainer(float x, float y) {
            super(x, y);
            setState(State.NOT_FOUND);
        }

        public void setState(State state) {
            this.state = state;
        }
    }

    class RiceContainer extends BaseActor {
        private State state;

        public RiceContainer(float x, float y) {
            super(x, y);
            setState(State.NOT_FOUND);

        }

        public void act(float dt) {
            switch (state) {
                case FOUND:
                    this.setTexture("RiceRoom/rice_bowl.png");
                    break;
                case NOT_FOUND:
                    this.setTexture("RiceRoom/rice_bowl_grey.png");
                    break;
            }
        }

        public void setState(State state) {
            this.state = state;
        }
    }

    class FishContainer extends BaseActor {
        private State state;

        public FishContainer(float x, float y) {
            super(x, y);
            setState(State.NOT_FOUND);
        }

        public void act(float dt) {
            switch (state) {
                case FOUND:
                    this.setTexture("FishRoom/fish.png");
                    break;
                case NOT_FOUND:
                    this.setTexture("FishRoom/fish_grey.png");
                    break;
            }
        }

        public void setState(State state) {
            this.state = state;
        }
    }
}
