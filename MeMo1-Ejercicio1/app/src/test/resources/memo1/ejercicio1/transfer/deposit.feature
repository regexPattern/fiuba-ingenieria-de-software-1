# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/9
Feature: Depositing funds
  Scenario: Successfully deposit money into an account
    Given An account with CBU 123456789, alias "account" and a balance of 1000.0
    When I deposit 200.0 into the account
    Then The account balance should be 1200.0
    And The deposit should generate a transaction
    And The transaction should have a correlative code
    And The transaction should have a date
    And The transaction should have a time
    And The transaction type should be "deposit"
    And The transaction amount should be 200.0
    And The transaction account should be account with CBU 123456789

  Scenario: Cannot deposit a negative amount
    Given An account with CBU 123456789, alias "account" and a balance of 1000.0
    When I try to deposit -100.0 into the account
    Then The operation should be denied
    And The account balance should be 1000.0
    And The operation should not generate a transaction
