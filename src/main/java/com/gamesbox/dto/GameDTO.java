package com.gamesbox.dto;

import jakarta.validation.constraints.NotNull;

public class GameDTO {

    private String name;
    private String slug;
    private String company;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }
}
