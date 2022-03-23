package com.maximoff.gameplay;

/*
    Класс для хранения шагов игры
 */

import java.util.List;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Step"
})
@Generated("me")


public class Game {

    @JsonProperty("Step")
    private List<Step> steps = null;

    public Game() {}

    /**
     * @param steps
     */
    public Game(List<Step> steps) {
        super();
        this.steps = steps;
    }

    @JsonProperty("Step")
    public List<Step> getSteps() {
        return steps;
    }

    @JsonProperty("Step")
    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }


}
