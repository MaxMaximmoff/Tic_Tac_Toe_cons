package com.maximoff.gameplay;

/*
    Класс обертка для представления файла Json
 */

import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "Gameplay"
})
@Generated("me")


public class Json {

    @JsonProperty("Gameplay")
    private Gameplay gameplay;

    public Json() {}

    /**
     * @param gameplay
     */
    public Json(Gameplay gameplay) {
        super();
        this.gameplay = gameplay;
    }

    @JsonProperty("Gameplay")
    public Gameplay getGameplay() {
        return gameplay;
    }

    @JsonProperty("Gameplay")
    public void setGameplay(Gameplay gameplay) {
        this.gameplay = gameplay;
    }

}
