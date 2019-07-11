package br.com.b2w.jparaujo.starwarsgame.business;

import br.com.b2w.jparaujo.starwarsgame.constant.ApplicationConstants;
import br.com.b2w.jparaujo.starwarsgame.dto.PlanetsDTO;
import br.com.b2w.jparaujo.starwarsgame.endpoints.PlanetsEndpoint;
import br.com.b2w.jparaujo.starwarsgame.exception.*;
import br.com.b2w.jparaujo.starwarsgame.entity.Planet;
import br.com.b2w.jparaujo.starwarsgame.exception.*;
import br.com.b2w.jparaujo.starwarsgame.repository.PlanetRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.*;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PlanetBusiness {

    private static final Logger LOGGER = LogManager.getLogger(PlanetsEndpoint.class);
    private final PlanetRepository repository;

    public PlanetBusiness(PlanetRepository repository){
        this.repository =repository;
    }

    public Planet addPlanet(@RequestBody final Planet planet){
        LOGGER.info(ApplicationConstants.EVENT_ENDPOINT_ADD_PLANET);

        //Verifica se o planeta está sendo passado
        if(planet == null){
            throw new PlanetNullException();
        }

        //Pega os valores e verifica as informações contidas
        String planetName = planet.getName();
        String planetClimate = planet.getClimate();
        String planetTerrain = planet.getTerrain();
        if(StringUtils.isEmpty(planetName) || StringUtils.isEmpty(planetName.trim())
                || StringUtils.isEmpty(planetClimate) || StringUtils.isEmpty(planetClimate.trim())
                || StringUtils.isEmpty(planetTerrain) || StringUtils.isEmpty(planetTerrain.trim())){
            throw new PlanetParameterNullEmptyException();
        }
        //Busca planeta antes de fazer a pesquisa no SWAPI
        repository.findByName(planetName).ifPresent(value -> {throw new PlanetExistOnDBException();});

        //Busca no SWAPI o planeta como o mesmo nome passado pelo usuário
        Planet planetSWAPI = buscarSWAPI(planet).getResults().stream()
                .filter(value -> planetName.equalsIgnoreCase(value.getName()))
                .findFirst().orElse(new Planet());

        planetSWAPI.setName(planetName);
        planetSWAPI.setClimate(planetClimate);
        planetSWAPI.setTerrain(planetTerrain);
        planetSWAPI.setAparicoes(Long.parseLong(Optional.ofNullable(planetSWAPI.getFilms()).map(List::size).orElse(0).toString()));

        //Salva na base local
        repository.save(planetSWAPI);

        return planetSWAPI;
    }

    private PlanetsDTO buscarSWAPI(Planet planet){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        headers.add("user-agent", ApplicationConstants.HEAD_VALUE);
        HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
        String url = ApplicationConstants.URL_SWAPI_SEARCH_PLANET + planet.getName();
        //PlanetsDTO body;

        try{
            return buscaPlanetasNoUniverso(restTemplate, entity, url);
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new SearchOnSWAPIException();
        }
    }


    private PlanetsDTO buscaPlanetasNoUniverso(RestTemplate restTemplate, HttpEntity<String> entity, String url){
        PlanetsDTO body = new PlanetsDTO();
        try{
            //Consome SWAPI
            ResponseEntity<PlanetsDTO> response = restTemplate.exchange(url, HttpMethod.GET,entity,PlanetsDTO.class);
            //Caso obtenha sucesso retorna body com o PlanetsDTO
            if(response.getStatusCode().is2xxSuccessful()){
                body = response.getBody();
                if(body != null && body.getNext() != null){
                    body.getResults().addAll(buscaPlanetasNoUniverso(restTemplate, entity, body.getNext()).getResults());
                }
            }
        }catch (Exception e){
            LOGGER.error(e.getMessage(), e);
            throw new SearchOnSWAPIException();
        }
        return body;
    }
    public List<Planet> listPlanet(){
        LOGGER.info(ApplicationConstants.EVENT_ENDPOINT_LIST_PLANET);
        List returnList = new ArrayList();
        //Lista todos os planetas
        returnList.add(repository.findAll());
        return returnList;
    }

    public List<Planet> findPlanet(String id){
        LOGGER.info(ApplicationConstants.EVENT_ENDPOINT_FIND_PLANET);
        //Busca por id/nome o planeta, caso não encontre retorna uma exceção tratada
        if(StringUtils.isEmpty(id) || StringUtils.isEmpty(id.trim())){
            throw new PlanetParameterNullEmptyException();
        }

        try{
            id = id.trim();
            return findById(Long.parseLong(id));
        } catch (NumberFormatException ne) {
            return findByName(id);
        }
    }

    private List<Planet> findById(Long id){
        LOGGER.info(ApplicationConstants.EVENT_ENDPOINT_FIND_ID_PLANET);
        List returnList = new ArrayList();
        //Busca por id o planeta, caso não encontre retorna uma exceção
        returnList.add(repository.findById(id).orElseThrow(PlanetDoesNotExistOnDBException:: new));
        return returnList;
    }

    private List<Planet> findByName(String name){
        LOGGER.info(ApplicationConstants.EVENT_ENDPOINT_FIND_NAME_PLANET);
        List returnList = new ArrayList();
        //Busca por nome o planeta, caso não encontre retorna uma exceção
        returnList.add(repository.findByName(name).orElseThrow(PlanetDoesNotExistOnDBException:: new));
        return returnList;
    }

    public void delete(String id){
        LOGGER.info(ApplicationConstants.EVENT_ENDPOINT_DELETE_ID_PLANET);
        List<Planet> listPlanet = findPlanet(id);

        //Remove por id o planeta, caso não encontre retorna uma exceção
        if(listPlanet == null || listPlanet.isEmpty()){
            throw new PlanetDoesNotExistOnDBException();
        } else if(listPlanet.size() > 1){
            throw new ExistManyPlanetsDBException();
        } else {
            repository.delete(listPlanet.get(0));
        }
    }
}
