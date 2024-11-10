# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/7
Feature: Closing a branch
  Scenario: Succesfully close a branch without accounts
    Given A branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA" that has no accounts
    When I close the branch
    Then The branch should be closed

  Scenario: Cannot close a branch with accounts
    Given An open branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    And An account with CBU 123456789, alias "account1" and owner with DNI 96113425, linked to the branch
    When I try to close the branch
    Then The operation should be denied
    And The branch should be open

  Scenario: Succesfully reopen a branch
    Given A closed branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    When I reopen the branch
    Then The branch should be open
  
  Scenario: Cannot close a branch that has not been registered
    Given I try to close a branch with code 984 that has not been registered
    Then The operation should be denied

  Scenario: Cannot reopen a branch that has not been registered
    Given I try to reopen a branch with code 984 that has not been registered
    Then The operation should be denied
