//import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.es.Cuando;
import cucumber.api.java.es.Dado;
import cucumber.api.java.es.Entonces;
import cucumber.api.java.es.Y;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.junit.Assert;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.RestAssured.given;

public class Steps {
    static String SEPARADOR="\n**************************************************\n";
    Response res;
    Pet pet;

    @Before
    public void doBefore(Scenario scenario) {

        System.out.println("+-----------------------------------------------------------------------------------------------+");
        System.out.println("|                                    INICIO DE LA PRUEBA                                        |");
        System.out.println("+-----------------------------------------------------------------------------------------------+");
        System.out.println("\n\tStarting - " + scenario.getName());

    }

    //---------- SE EJECUTA DESPUES DE EJECUTAR LAS PRUEBAS ------------------------------------------------------------
    @After
    public void doSomethingAfter(Scenario scenario) {
        System.out.println("\n\t"+scenario.getName() + " Status - " + scenario.getStatus());
        System.out.println("+-----------------------------------------------------------------------------------------------+");
        System.out.println("|                                       FIN DE LA PRUEBA                                        |");
        System.out.println("+-----------------------------------------------------------------------------------------------+");
    }

    @Dado("que agrego una mascota de nombre (.*), estado (.*) mediante la URL (.*)")
    public void postMascota(String nombre, String estado, String URL) {

        System.out.println(SEPARADOR+"\t\tPASO 1 - Agregado de una mascota"+SEPARADOR);
        System.out.println(" -----> Nombre: "+nombre+"; Estado: "+estado);

        JSONObject requestParams = new JSONObject();
        requestParams.put("name", nombre);
        requestParams.put("status", estado);

        Response response = given()
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Session-Id", "Sesión1")
                .when()
                .body(requestParams.toString())
                .post(URL)
                .then()
                .extract()
                .response();

        System.out.println("Body del response");
        String jsonResponse = response.getBody().print();
        System.out.println("Final del body");

        Gson gson = new Gson();
        pet = gson.fromJson(response.asString(), Pet.class);

        boolean res= true;
        if(response.getStatusCode()!=200){
            res = false;
        }

        Assert.assertTrue("Hubo un error agregando la mascota", res);

        System.out.println("\n ------------> AGREGADO DE MASCOTA OK\n");

    }

    @Cuando("obtengo los datos de la mascota  mediante la URL (.*)")
    public void getMascota(String URL) {
        boolean res = true;
        System.out.println(SEPARADOR+"\t\tPASO 2 - Obtener datos de mascota"+SEPARADOR);

        Response response = given()
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Session-Id", "Sesión1")
                .when()
                .get(URL+"/"+pet.getId())
                .then()
                .extract()
                .response();

        System.out.println("Body del response");
        String jsonResponse = response.getBody().print();
        System.out.println("Final del body");

        if(response.getStatusCode()!=200){
            res = false;
        }

        Assert.assertTrue("Hubo un error consultando los datos de la mascota", res);


        System.out.println("\n ------------> OBTENCION DE MASCOTA OK\n");

    }

    @Entonces("modifico el nombre de la mascota por (.*) y el estado por (.*) mediante la URL (.*)")
    public void putMascota(String nombre, String estado, String URL) {
        boolean res = true;
        System.out.println(SEPARADOR+"\t\tPASO 3 - Modificación de datos de la mascota"+SEPARADOR);


        System.out.println(" -----> Nombre: "+nombre+"; Estado: "+estado+"; URL: "+URL);

        JSONObject requestParams = new JSONObject(); JSONObject paymentExtraParams = new JSONObject();
        requestParams.put("name", nombre);
        requestParams.put("id", String.valueOf(pet.getId()));
        requestParams.put("status", estado);

        Response response = given()
                .header("Content-Type", "application/json;charset=utf-8")
                .header("Session-Id", "Sesión1")
                .when()
                .body(requestParams.toString())
                .put(URL)
                .then()
                .extract()
                .response();

        Gson gson = new Gson();
        pet = gson.fromJson(response.asString(), Pet.class);

        if(response.getStatusCode()!=200){
            res= false;
        }

        System.out.println("Body del response");
        String jsonResponse = response.getBody().print();
        System.out.println("Final del body");

        Assert.assertTrue("Se produjo un error modificando los datos de la mascota", res);


        System.out.println("\n ------------> MODIFICACION DE MASCOTA OK\n");

    }
}

