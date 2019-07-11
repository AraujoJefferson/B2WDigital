package br.com.ame.jparaujo.starwarsgame.constant;

public class ApplicationConstants {


    /**
     * Entity
     */
    public static final String TABLE_NAME_PLANET = "PLANET";
    public static final String FIELD_NAME_ID = "id";
    public static final String FIELD_NAME_NOME = "name";
    public static final String FIELD_NAME_CLIMA = "climate";
    public static final String FIELD_NAME_TERRENO = "terrain";
    public static final String FIELD_NAME_APARICOES = "aparicoes";

    /**
     * EndPoint
     */
    public static final String CONTENT_TYPE = "application/json";
    public static final String HEAD_VALUE = "SWAPI/StarWarsGame";
    public static final String URL_SWAPI_SEARCH_PLANET = "https://swapi.co/api/planets?search=";

    /**
     * EndPoint>Message
     */
    public static final String MESSAGE_FORBIDDEN = "Acesso Negado";
    public static final String MESSAGE_SERVER_ERRO = "Erro Interno no Servidor";
    public static final String MESSAGE_ENDPOINT_PLANET_SEARCH_SWAPI = "Erro ao pesquisar planeta no SWAPI";
    public static final String MESSAGE_ENDPOINT_PLANET_NOTEXIST = "Planeta não existe";
    public static final String MESSAGE_ENDPOINT_PLANET_EXIST = "Planeta existe";
    public static final String MESSAGE_ENDPOINT_PLANET_MANY_EXIST = "Existem muitos planetas com esse parametro";
    public static final String MESSAGE_ENDPOINT_PLANET_NULL = "Não é possível adcionar este planeta, planeta passado é nulo";
    public static final String MESSAGE_ENDPOINT_PLANET_PARAMETER_INVALID = "Planeta passado com parametros inválido, valide as informações passadas e tente novamente";

    /**
     * EndPoint>Entity>Message
     */
    public static final String EVENT_ENDPOINT_ADD_PLANET = "Adicionando planeta";
    public static final String EVENT_ENDPOINT_LIST_PLANET = "Listando todos os planetas";
    public static final String EVENT_ENDPOINT_FIND_PLANET = "Buscando planeta";
    public static final String EVENT_ENDPOINT_FIND_ID_PLANET = "Buscando planeta por " + FIELD_NAME_ID;
    public static final String EVENT_ENDPOINT_FIND_NAME_PLANET = "Buscando planeta por " + FIELD_NAME_NOME;
    public static final String EVENT_ENDPOINT_DELETE_ID_PLANET = "Planeta sendo apagado por " + FIELD_NAME_ID;
    public static final String EVENT_ENDPOINT_DELETED_PLANET = "Planeta apagado";

    /**
     * Message
     */
    public static final String MESSAGE_CONSTRUTOR = "Construtor instanciado ";
}


