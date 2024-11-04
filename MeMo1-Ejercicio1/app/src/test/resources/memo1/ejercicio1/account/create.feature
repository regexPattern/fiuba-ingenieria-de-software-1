# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/3
Feature: Account creation
  Scenario: Successfully create an account with default balance
    Given A branch with code 1, name "Branch 1" and address "Paseo Colón 950"
    And A client with DNI 96113425, name "Carlos" and surname "Castillo"
    When I create an account with CBU 123456789 and alias "account" in the given branch and owned by the given client
    Then The account CBU should be 123456789
    And The account alias should be "account"
    And The account branch should be the branch with code 1, name "Branch 1" and address "Paseo Colón 950"
    And The account owner should be the client with DNI 96113425, name "Carlos" and surname "Castillo"
    And The account balance should be 0.0

  Scenario: Successfully create an account with an initial balance
    Given A branch with code 1, name "Branch 1" and address "Paseo Colón 950"
    And A client with DNI 96113425, name "Carlos" and surname "Castillo"
    When I create an account with CBU 123456789, alias "account" and balance of 500.0 in the given branch and owned by the given client
    Then The account CBU should be 123456789
    And The account alias should be "account"
    And The account branch should be the branch with code 1, name "Branch 1" and address "Paseo Colón 950"
    And The account owner should be the client with DNI 96113425, name "Carlos" and surname "Castillo"
    And The account balance should be 500.0

  Scenario: Cannot register an account with an already-used CBU
    Given An account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    When I try to register an account with CBU 123456789, alias "account2", branch with code 2 and owner with DNI 3324512
    Then The operation should be denied
    And The second account should not be registered

  Scenario: Cannot register an account with an already-used alias
    Given An account with CBU 123456789, alias "account", branch with code 1 and owner with DNI 96113425
    When I try to register an account with CBU 756123121, alias "account", branch with code 2 and owner with DNI 3324512
    Then The operation should be denied
    And The second account should not be registered

  Scenario: Successfully register two accounts with the same owner
    Given I register an account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    When I register another account with CBU 751623128, alias "account2", branch with code 2 and owner with DNI 96113425
    Then The first account should be registered
    And The second account should be registered

  Scenario: Successfully register two accounts with the same branch
    Given I register an account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    When I register another account with CBU 751623128, alias "account2", branch with code 1 and owner with DNI 3324512
    Then The first account should be registered
    And The second account should be registered

  Scenario: Cannot create an account with a closed branch
    Given A branch with code 1, name "Branch 1" and address "Paseo Colón 950" which is closed
    When I try to create an account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    Then The operation should be denied
    And The account should not be created
