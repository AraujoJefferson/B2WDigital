package br.com.b2w.jparaujo.starwarsgame.endpoints;

import br.com.b2w.jparaujo.starwarsgame.constant.ApplicationConstants;
import br.com.b2w.jparaujo.starwarsgame.business.PlanetBusiness;
import br.com.b2w.jparaujo.starwarsgame.entity.Planet;
import br.com.b2w.jparaujo.starwarsgame.repository.PlanetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@Validated
public class PlanetsEndpoint {
    private static final Logger LOGGER = LogManager.getLogger(PlanetsEndpoint.class);
    private final PlanetBusiness business;

    @Autowired
    public PlanetsEndpoint(PlanetRepository repository){
        LOGGER.info(ApplicationConstants.MESSAGE_CONSTRUTOR + this.toString());
        this.business = new PlanetBusiness(repository);
    }

    /**
     * Criar planeta no banco de dados
     * @param planet
     * @return
     */
    @RequestMapping(method = RequestMethod.POST, path = "/planet/add", consumes = ApplicationConstants.CONTENT_TYPE)
    public ResponseEntity addPlanet(@RequestBody Planet planet){
        return new ResponseEntity(business.addPlanet(planet), HttpStatus.CREATED);
    }

    /**
     * Lista todos os planetas na base de dados
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/planet/list", produces = ApplicationConstants.CONTENT_TYPE)
    public ResponseEntity<List<Planet>> listPlanet(){
        return new ResponseEntity(business.listPlanet(), HttpStatus.OK);
    }

    /**
     * Busca o planeta com o id/nome correspondente
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, path = "/planet/find/{"+ ApplicationConstants.FIELD_NAME_ID+"}", produces = ApplicationConstants.CONTENT_TYPE)
    public ResponseEntity<List<Planet>> findPlanet(@PathVariable(ApplicationConstants.FIELD_NAME_ID) @NotNull String id){
        return new ResponseEntity(business.findPlanet(id), HttpStatus.OK);
    }

    /**
     * Apaga o planeta da base de dados
     * @param id
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, path = "/planet/delete/{"+ ApplicationConstants.FIELD_NAME_ID+"}")
    public ResponseEntity deleteById(@PathVariable(ApplicationConstants.FIELD_NAME_ID) @NotNull String id){
        business.delete(id);
        return new ResponseEntity(ApplicationConstants.EVENT_ENDPOINT_DELETED_PLANET, HttpStatus.GONE);
    }
}

