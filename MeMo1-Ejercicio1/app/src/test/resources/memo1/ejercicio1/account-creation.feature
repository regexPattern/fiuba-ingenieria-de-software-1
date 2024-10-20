Feature: Account creation
  Scenario: Successfully create an account with default balance
    Given I create an account with CBU 123456789 and alias "carlosecp"
    Then The account CBU should be 123456789
    And The account alias should be "carlosecp"
    And The account balance should be 0.0

  Scenario: Successfully create an account with an initial balance
    Given I create an account with CBU 123456789, alias "carlosecp" and a balance of 500.0
    Then The account CBU should be 123456789
    And The account alias should be "carlosecp"
    And The account balance should be 500.0
