package br.com.b2w.jparaujo.starwarsgame.dto;

import br.com.b2w.jparaujo.starwarsgame.entity.Planet;

import java.util.List;

public class PlanetsDTO {

    private Long count;
    private String next;
    private List<Planet> results;

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public List<Planet> getResults() {
        return results;
    }

    public void setResults(List<Planet> results) {
        this.results = results;
    }
}
