package com.example.imeibulk.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceCharacteristics {
    @JsonProperty("GENERATION.4G")
    public String generation4G = "false";
    @JsonProperty("GENERATION.3G")
    public String generation3G = "false";
    @JsonProperty("GENERATION.2G")
    public String generation2G = "false";

    public String getGeneration4G() {
        return generation4G;
    }

    public void setGeneration4G(String generation4G) {
        this.generation4G = generation4G;
    }

    public String getGeneration3G() {
        return generation3G;
    }

    public void setGeneration3G(String generation3G) {
        this.generation3G = generation3G;
    }

    public String getGeneration2G() {
        return generation2G;
    }

    public void setGeneration2G(String generation2G) {
        this.generation2G = generation2G;
    }
}

