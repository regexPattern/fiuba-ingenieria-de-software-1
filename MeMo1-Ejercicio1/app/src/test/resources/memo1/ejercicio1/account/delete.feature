# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/13
Feature: Account deletion
  Scenario: Successfully delete an account
    Given An account with CBU 123456789, alias "account1", branch with code 1, owner with DNI 96113425 and a balance of 0.0
    When I delete the account
    Then The operation should be successful

  Scenario: Cannot delete an account that still has funds
    Given An account with CBU 123456789, alias "account1", branch with code 1, owner with DNI 96113425 and a balance of 500.0
    When I try to delete the account
    Then The operation should be denied
    And The registered accounts should remain the same

  Scenario: Cannot delete an account that has not been registered
    When I try to delete an account with CBU 875612312 that has not been registered
    Then The operation should be denied
    And The registered accounts should remain the same
