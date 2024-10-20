Feature: Account creation
  Scenario: Successfully create an account with default balance
    Given I create an account with CBU 123456789 and alias "account"
    Then The account CBU should be 123456789
    And The account alias should be "account"
    And The account balance should be 0.0

  Scenario: Successfully create an account with an initial balance
    Given I create an account with CBU 123456789, alias "account" and a balance of 500.0
    Then The account CBU should be 123456789
    And The account alias should be "account"
    And The account balance should be 500.0

  Scenario: Cannot register and account with an already-used CBU
    Given An account with CBU 123456789 and alias "account1"
    When I try to create an account with CBU 123456789 and alias "account2"
    Then The operation should be denied
    And The second account should not be registered

  Scenario: Cannot register and account with an already-used alias
    Given An account with CBU 123456789 and alias "account"
    When I try to create an account with CBU 987654321 and alias "account"
    Then The operation should be denied
    And The second account should not be registered
