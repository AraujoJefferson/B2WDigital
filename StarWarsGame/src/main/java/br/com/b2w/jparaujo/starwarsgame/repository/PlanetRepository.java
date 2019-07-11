package br.com.b2w.jparaujo.starwarsgame.repository;

import br.com.b2w.jparaujo.starwarsgame.entity.Planet;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PlanetRepository extends CrudRepository<Planet, Long> {
    @Override
    Optional<Planet> findById(Long aLong);

    Optional<Planet> findByName(String name);

    @Override
    Iterable<Planet> findAll();
}
