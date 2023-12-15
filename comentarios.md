# Correção

  - carga de teste inicial muda quando seleciona o estacionamento
  - seleção de estacionamento só no segundo menu 
  - confusão nos relatórios pelo acima
  - menu bagunçado: cada hora o sair é um número, algumas vezes no meio do menu
  - app, no geral, poderia ser mais organizado. cases enormes, verificações repetidas etc
  - o cliente não consegue estacionar 2 carros, aparentemente. 
  - exceção para o mesmo plano parece exagero: se ignorar a operação o resultado é o mesmo.
  - já para sair da vaga, é o contrário: se não tem o tempo mínimo, não pode ignorar
  - uso horista dentro do turno
  - relatório veículos: por valor ou por data

## Nota Base 18

## Colaborações
  - David Ho
    - app
    - robustez
  - Gabriel Lourenço
    - factory
    - documentacao
  - João Vitor Alves
    - app
  - Larissa Pedrosa
    - relatório veículos
  - Pedro Maximo
    - strategy
    - documentacao
  - Rodrigo Diniz
    - javadoc
    - carga
  
## Requisitos:
  - Gerais: 5/6 pontos
    - cliente mudar de plano ✔️
    - estacionar e cálculo de preço 😐
    - relatórios estacionamento ✔️
    - relatórios cliente e carro ✔️😐
  - Padrões de projeto: 6/6 pontos
    - Factory ✔️
    - Stragegy ✔️
  - App: 4/5 pontos
    - funcional 😐
    - carga de teste 😐
    - robustez ✔️
  - Documentação: 3/3 pontos
    - diagrama ✔️
    - javadoc ✔️