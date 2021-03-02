#language: es
@Cucumber
Característica: Test backend


  @CP1
  Esquema del escenario: Prueba Swagger Pet Store
    Dado que agrego una mascota de nombre <NAME>, estado <STATUS> mediante la URL <URL_POST>
    Cuando obtengo los datos de la mascota  mediante la URL <URL_GET>
    Entonces modifico el nombre de la mascota por <NEWNAME> y el estado por <NEWSTATUS> mediante la URL <URL_PUT>

    Ejemplos:
      | URL_POST                            | NAME | STATUS    | URL_GET                            |   NEWNAME    |    NEWSTATUS     |   URL_PUT                             |
      | https://petstore.swagger.io/v2/pet  | UMA  | Available | https://petstore.swagger.io/v2/pet |   Magui      |    Available     |   https://petstore.swagger.io/v2/pet  |


