# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/6
Feature: Client creation
  Scenario: Correctly create a client
    Given I create a client with DNI 123456789, name "Carlos", surname "Castillo", birth date "10/09/2001" and address "Paseo Colón 950"
    Then The client DNI should be 123456789
    And The client name should be "Carlos"
    And The client surname should be "Castillo"
    And The client birth date should be "10/09/2001"
    And The client address should be "Paseo Colón 950"

  Scenario: Cannot create a client with an already-taken DNI
    Given A client with DNI 123456789, name "Carlos" and surname "Castillo"
    When I try to register another client with DNI 123456789, name "Eduardo" and surname "Pereira"
    Then The client register operation should be denied
