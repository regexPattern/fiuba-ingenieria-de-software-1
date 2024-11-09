# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/25
Feature: Client deletion
  Scenario: Successfully delete a client without accounts
    Given A client with DNI 123456789, name "Carlos", surname "Castillo" that has no accounts
    When I delete the client
    Then The client should be deleted

  Scenario: Cannot delete a client who owns an account
    Given A client with DNI 123456789, name "Carlos", surname "Castillo"
    And An account with CBU 987654321, alias "account" where the client is the owner
    When I try to delete the client
    Then The operation should be denied
    And The client should remain registered

  Scenario: Cannot delete a client who is co-owner of an account
    Given A client with DNI 123456789, name "Carlos", surname "Castillo"
    And An account with CBU 987654321, alias "account" where the client is the co-owner
    When I try to delete the client
    Then The operation should be denied
    And The client should remain registered

  Scenario: Cannot delete a client that has not been registered
    Given I try to delete a client with DNI 123456789 that has not been registered
    Then The operation should be denied
