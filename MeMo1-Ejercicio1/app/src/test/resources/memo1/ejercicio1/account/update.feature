# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/24
Feature: Account update
  Scenario: Successfully update an account alias
    Given An account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    When I update the account with CBU 123456789 alias to be "account2"
    Then The account alias should be "account2"

  Scenario: Successfully update an account branch
    Given A branch with code 1, name "Branch 1" and address "Paseo Colón 950"
    And An account with CBU 123456789 and branch with code 9
    When I update the account branch to branch with code 1
    And The account branch should be the branch with code 1, name "Branch 1" and address "Paseo Colón 950"

  Scenario: Cannot update an account alias to an already-used alias
    Given An account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    And An account with CBU 784123124, alias "account2", branch with code 1 and owner with DNI 78461231
    When I try to update the account with CBU 123456789 alias to be "account2"
    Then The operation should be denied
    And The first account alias should remain "account1"
    And The second account alias should remain "account2"

  Scenario: Cannot update an account branch to a closed branch
    Given A branch with code 1, name "Branch 1" and address "Paseo Colón 950" which is closed
    And An account with CBU 123456789 and branch with code 9
    When I update the account branch to branch with code 1
    Then The operation should be denied
    And The account branch should remain the branch with code 9
