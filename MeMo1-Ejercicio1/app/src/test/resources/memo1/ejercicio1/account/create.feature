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
    Given An account with CBU 123456789 and alias "account1"
    When I try to create an account with CBU 123456789 and alias "account2"
    Then The operation should be denied
    And The second account should not be registered

  Scenario: Cannot register an account with an already-used alias
    Given An account with CBU 123456789 and alias "account"
    When I try to create an account with CBU 987654321 and alias "account"
    Then The operation should be denied
    And The second account should not be registered

  Scenario: Cannot register an account in a closed branch
    Given An account with 
