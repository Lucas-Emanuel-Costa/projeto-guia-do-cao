/*
  Professora !!!

  Refatorei o projeto achando que teria que usar Spring Boot completo, mas depois da aula de quinta, descobri 
  que era só para seguir a estrutura de pastas :) . Então reorganizei o projeto e aproveitei para 
  acrescentar algumas funcionalidades extras.

  A ideia do sistema é minha, porque crio cachorros desde pequeno, então quis desenvolver algo
  relacionado ao que tenho experiência.

  Sobre algumas pastas diferentes: criei a pasta "view" para separar a parte visual (menus do
  console), seguindo um modelo parecido com MVC. A interface "SistemaView" está lá porque representa
  a “tela” do sistema, e a classe SistemaClinicaConsole é a implementação dela.

  Sei que alguns comentários não deveriam ficar no código, mas como não haverá apresentação antes
  da correção, deixei para facilitar o entendimento das minhas escolhas. 
*/

import view.SistemaClinicaConsole;
import view.SistemaView;

public class Main {
    public static void main(String[] args) {
        SistemaView sistema = new SistemaClinicaConsole();
        sistema.executar();
    }
}