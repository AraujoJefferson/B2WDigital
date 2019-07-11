package br.com.b2w.jparaujo.starwarsgame.entity;

import br.com.b2w.jparaujo.starwarsgame.constant.ApplicationConstants;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = ApplicationConstants.TABLE_NAME_PLANET)
public class Planet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ApplicationConstants.FIELD_NAME_ID)
    private Long id;
    @Column(name = ApplicationConstants.FIELD_NAME_NOME)
    private String name;
    @Column(name = ApplicationConstants.FIELD_NAME_CLIMA)
    private String climate;
    @Column(name = ApplicationConstants.FIELD_NAME_TERRENO)
    private String terrain;
    @Column(name = ApplicationConstants.FIELD_NAME_APARICOES)
    private Long aparicoes;

    @Transient
    private List<String> films;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClimate() {
        return climate;
    }

    public void setClimate(String climate) {
        this.climate = climate;
    }

    public String getTerrain() {
        return terrain;
    }

    public void setTerrain(String terrain) {
        this.terrain = terrain;
    }

    public Long getAparicoes() {
        return this.aparicoes;
    }

    public void setAparicoes(Long aparicoes) {
        this.aparicoes = aparicoes;
    }

    public List<String> getFilms() {
        return films;
    }

    public void setFilms(List<String> films) {
        this.films = films;
    }
}
