package br.com.ame.jparaujo.starwarsgame;

import br.com.ame.jparaujo.starwarsgame.business.PlanetBusiness;
import br.com.ame.jparaujo.starwarsgame.constant.ApplicationConstants;
import br.com.ame.jparaujo.starwarsgame.entity.Planet;
import br.com.ame.jparaujo.starwarsgame.exception.PlanetDoesNotExistOnDBException;
import br.com.ame.jparaujo.starwarsgame.exception.PlanetExistOnDBException;
import br.com.ame.jparaujo.starwarsgame.exception.PlanetNullException;
import br.com.ame.jparaujo.starwarsgame.exception.PlanetParameterNullEmptyException;
import br.com.ame.jparaujo.starwarsgame.repository.PlanetRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
public class ApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PlanetRepository repository;

    private PlanetBusiness business;

    @Before
    public void deleteAllBeforeTests() throws Exception {
        repository.deleteAll();
        business = new PlanetBusiness(repository);
    }

    @Test(expected = PlanetNullException.class)
    public void adcionarPlanetaNuloException() throws Exception{
       business.addPlanet(null);
    }

    @Test(expected = PlanetParameterNullEmptyException.class)
    public void adcionarPlanetaValoresInvalidoException() throws Exception{
        Planet planet = new Planet();
        business.addPlanet(planet);
    }

    @Test(expected = PlanetExistOnDBException.class)
    public void adcionarPlanetaExistenteBancoDadosException() throws Exception{
        repository.deleteAll();
        Planet planet = new Planet();
        planet.setTerrain("Dessert");
        planet.setName("Tatooine");
        planet.setClimate("Arid");
        planet.setAparicoes(5L);

        repository.save(planet);

        business.addPlanet(planet);
    }

    @Test(expected = PlanetParameterNullEmptyException.class)
    public void listarPlanetasPassandoParametroNuloException(){
        business.findPlanet(null);
    }

    @Test(expected = PlanetParameterNullEmptyException.class)
    public void listarPlanetasPassandoParametroEspacoException(){
        business.findPlanet("   ");
    }

    @Test(expected = PlanetDoesNotExistOnDBException.class)
    public void listarPlanetasPassandoParametroNomePlanetaNaoExistenteException(){
        business.findPlanet("  Tatooine ");
    }

    @Test(expected = PlanetDoesNotExistOnDBException.class)
    public void listarPlanetasPassandoParametroIdPlanetaNaoExistenteException(){
        business.findPlanet("  15 ");
    }

    @Test
    public void adcionarPlanetaExisteSWAPI() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findByName("Tatooine"));

        mockMvc.perform(post("/planet/add")
                .contentType(ApplicationConstants.CONTENT_TYPE)
                .content(getPlanetSWAPI()))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "        \"name\": \"Tatooine\",\n" +
                        "        \"climate\": \"Arid\",\n" +
                        "        \"terrain\": \"Dessert\",\n" +
                        "        \"aparicoes\": 5\n" +
                        "    }"));

        Optional<Planet> optional = repository.findByName("Tatooine");
        Assert.assertEquals("Tatooine", optional.orElse(new Planet()).getName());
        Assert.assertEquals("Arid", optional.orElse(new Planet()).getClimate());
        Assert.assertEquals("Dessert", optional.orElse(new Planet()).getTerrain());
        Assert.assertEquals(Optional.of(5L), Optional.ofNullable(optional.get().getAparicoes()));
        Assert.assertNull(optional.get().getFilms());
    }

    @Test
    public void adcionarPlanetaNaoExisteSWAPI() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findByName("Terra"));

        mockMvc.perform(post("/planet/add")
                .contentType(ApplicationConstants.CONTENT_TYPE)
                .content(getPlanetNotInSWAPI()))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\n" +
                        "        \"name\": \"Terra\",\n" +
                        "        \"climate\": \"temperate\",\n" +
                        "        \"terrain\": \"grasslands, mountains\",\n" +
                        "        \"aparicoes\": 0\n" +
                        "    }"));

        Optional<Planet> optional = repository.findByName("Terra");
        Assert.assertEquals("Terra", optional.orElse(new Planet()).getName());
        Assert.assertEquals("temperate", optional.orElse(new Planet()).getClimate());
        Assert.assertEquals("grasslands, mountains", optional.orElse(new Planet()).getTerrain());
        Assert.assertEquals(Optional.of(0L), Optional.ofNullable(optional.get().getAparicoes()));
        Assert.assertNull(optional.get().getFilms());
    }

    @Test
    public void existePlanetaSWAPI() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findByName("Tatooine"));

        Planet planet = new Planet();
        planet.setTerrain("Dessert");
        planet.setName("Tatooine");
        planet.setClimate("Arid");

        repository.save(planet);

        mockMvc.perform(post("/planet/add")
                .contentType(ApplicationConstants.CONTENT_TYPE)
                .content(getPlanetSWAPI()))
                .andExpect(status().isNotModified());

        Optional<Planet> optional = repository.findById(planet.getId());
        Assert.assertEquals(planet.getName(), optional.orElse(new Planet()).getName());
        Assert.assertEquals(planet.getClimate(), optional.orElse(new Planet()).getClimate());
        Assert.assertEquals(planet.getTerrain(), optional.orElse(new Planet()).getTerrain());
        Assert.assertEquals(planet.getAparicoes(), optional.orElse(new Planet()).getAparicoes());
        Assert.assertEquals(planet.getFilms(), optional.get().getFilms());
    }

    @Test
    public void listarPlanetasNoBanco() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findByName("Tatooine"));

        Planet planet = new Planet();
        planet.setTerrain("Dessert");
        planet.setName("Tatooine");
        planet.setClimate("Arid");
        planet.setAparicoes(5L);

        repository.save(planet);

        mockMvc.perform(get("/planet/list")
                .contentType(ApplicationConstants.CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().json("[\n" +
                        "    [\n" +
                        "        {\n" +
                        "            \"name\": \"Tatooine\",\n" +
                        "            \"climate\": \"Arid\",\n" +
                        "            \"terrain\": \"Dessert\",\n" +
                        "            \"aparicoes\": 5,\n" +
                        "            \"films\": null\n" +
                        "        }\n" +
                        "    ]\n" +
                        "]"));


        Optional<Planet> optional = repository.findById(planet.getId());
        Assert.assertEquals(planet.getName(), optional.orElse(new Planet()).getName());
        Assert.assertEquals(planet.getClimate(), optional.orElse(new Planet()).getClimate());
        Assert.assertEquals(planet.getTerrain(), optional.orElse(new Planet()).getTerrain());
        Assert.assertEquals(planet.getAparicoes(), optional.orElse(new Planet()).getAparicoes());
        Assert.assertEquals(planet.getFilms(), optional.get().getFilms());
    }

    @Test
    public void encontrarPlanetaExistentePorNomeNoBanco() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findByName("Tatooine"));

        Planet planet = new Planet();
        planet.setTerrain("Dessert");
        planet.setName("Tatooine");
        planet.setClimate("Arid");
        planet.setAparicoes(5L);

        repository.save(planet);

        mockMvc.perform(get("/planet/find/" + planet.getName())
                .contentType(ApplicationConstants.CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Tatooine\",\"climate\":\"Arid\",\"terrain\":\"Dessert\",\"aparicoes\":5,\"films\":null}]"));


        Optional<Planet> optional = repository.findById(planet.getId());
        Assert.assertEquals(planet.getName(), optional.orElse(new Planet()).getName());
        Assert.assertEquals(planet.getClimate(), optional.orElse(new Planet()).getClimate());
        Assert.assertEquals(planet.getTerrain(), optional.orElse(new Planet()).getTerrain());
        Assert.assertEquals(planet.getAparicoes(), optional.orElse(new Planet()).getAparicoes());
        Assert.assertEquals(planet.getFilms(), optional.get().getFilms());
    }

    @Test
    public void encontrarPlanetaExistentePorIdNoBanco() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findByName("Tatooine"));

        Planet planet = new Planet();
        planet.setTerrain("Dessert");
        planet.setName("Tatooine");
        planet.setClimate("Arid");
        planet.setAparicoes(4L);

        repository.save(planet);

        mockMvc.perform(get("/planet/find/" + planet.getId())
                .contentType(ApplicationConstants.CONTENT_TYPE))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"name\":\"Tatooine\",\"climate\":\"Arid\",\"terrain\":\"Dessert\",\"aparicoes\":4,\"films\":null}]"));

        Optional<Planet> optional = repository.findById(planet.getId());
        Assert.assertEquals(planet.getName(), optional.orElse(new Planet()).getName());
        Assert.assertEquals(planet.getClimate(), optional.orElse(new Planet()).getClimate());
        Assert.assertEquals(planet.getTerrain(), optional.orElse(new Planet()).getTerrain());
        Assert.assertEquals(planet.getAparicoes(), optional.orElse(new Planet()).getAparicoes());
        Assert.assertEquals(planet.getFilms(), optional.get().getFilms());

    }

    @Test
    public void apagarPlanetaExistentePorIdNoBanco() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findByName("Tatooine"));

        Planet planet = new Planet();
        planet.setTerrain("Dessert");
        planet.setName("Tatooine");
        planet.setClimate("Arid");
        planet.setAparicoes(4L);

        repository.save(planet);

        mockMvc.perform(delete("/planet/delete/" + planet.getId())
                .contentType(ApplicationConstants.CONTENT_TYPE))
                .andExpect(status().isGone());

        Assert.assertEquals(Optional.empty(),repository.findById(planet.getId()));
    }

    @Test
    public void encontrarPlanetaNaoExistentePorNomeNoBanco() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findByName("Terra"));

        mockMvc.perform(get("/planet/find/Terra")
                .contentType(ApplicationConstants.CONTENT_TYPE))
                .andExpect(status().isNoContent());

        Assert.assertEquals(Optional.empty(),repository.findByName("Terra"));
    }

    @Test
    public void encontrarPlanetaNaoExistentePorIdNoBanco() throws Exception {
        repository.deleteAll();

        Assert.assertEquals(Optional.empty(), repository.findById(0L));

        mockMvc.perform(get("/planet/find/0")
                .contentType(ApplicationConstants.CONTENT_TYPE))
                .andExpect(status().isNoContent());

        Assert.assertEquals(Optional.empty(),repository.findById(0L));
    }

    @Test
    public void apagarPlanetaNaoExistentePorIdNoBanco() throws Exception {
        repository.deleteAll();

        Planet planet = new Planet();
        planet.setTerrain("Dessert");
        planet.setName("Tatooine");
        planet.setClimate("Arid");
        planet.setAparicoes(4L);
        planet.setId(1L);

        Assert.assertEquals(Optional.empty(), repository.findByName(planet.getName()));

        mockMvc.perform(delete("/planet/delete/" + planet.getId())
                .contentType(ApplicationConstants.CONTENT_TYPE))
                .andExpect(status().isNoContent());
    }

    private String getPlanetSWAPI() {
        String retorno = new String("{\n" +
                "  \"climate\": \"Arid\",\n" +
                "  \"name\": \"Tatooine\",\n" +
                "  \"terrain\": \"Dessert\"\n" +
                "}");
        return retorno;
    }

    private String getPlanetNotInSWAPI() {
        String retorno = new String("{\n" +
                "  \"climate\": \"temperate\",\n" +
                "  \"name\": \"Terra\",\n" +
                "  \"terrain\": \"grasslands, mountains\"\n" +
                "}");
        return retorno;
    }
}

