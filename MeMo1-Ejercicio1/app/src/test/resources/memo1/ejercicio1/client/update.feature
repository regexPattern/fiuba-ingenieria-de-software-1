# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/14
Feature: Client update
  Scenario: Successfully update client information
    Given A client with DNI 123456789, name "Carlos", surname "Castillo", birth date "10/09/2001" and address "Paseo Colón 950"
    When I update the client information with name "Carlos Alberto", surname "Castillo Ruiz", birth date "10/09/1980" and address "Av. Las Heras 2214"
    Then The client name should be "Carlos Alberto"
    And The client surname should be "Castillo Ruiz"
    And The client birth date should be "10/09/1980"
    And The client address should be "Av. Las Heras 2214"
    And The client DNI should be 123456789

  Scenario: Cannot update client with future birth date
    Given A client with DNI 123456789, name "Carlos", surname "Castillo", birth date "10/09/2001" and address "Paseo Colón 950"
    When I try to update the client birth date to "10/09/2025"
    Then The operation should be denied
    And The client birth date should remain "10/09/2001"
