# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/10
Feature: Withdrawing funds
  Scenario: Successfully withdraw money when the balance is sufficient
    Given An account with CBU 123456789, alias "account" and a balance of 1000.0
    When I withdraw 300.0 from the account
    Then The account balance should be 700.0
    And The withdrawal should generate a transaction
    And The transaction should have a correlative code
    And The transaction should have a date
    And The transaction should have a time
    And The transaction type should be "withdrawal"
    And The transaction amount should be 300.0
    And The transaction account should be account with CBU 123456789

  Scenario: Cannot withdraw more money than available balance
    Given An account with CBU 123456789, alias "account" and a balance of 1000.0
    When I try to withdraw 1100.0 from the account
    Then The operation should be denied
    And The account balance should be 1000.0
    And The operation should not generate a transaction
