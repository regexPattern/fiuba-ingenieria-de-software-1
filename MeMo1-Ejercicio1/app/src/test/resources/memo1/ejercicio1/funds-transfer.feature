Feature: Transferring funds
  Scenario: Successfully transfer funds into an account
    Given An account with CBU 123 and a balance of 1000.0
    And An account with CBU 456 and a balance of 0.0
    When I deposit 200.0 from the first account into the second one
    Then The second account balance should be 200.0
    And The first account balance should be 800.0

  Scenario: Cannot transfer a negative amount 
    Given An account with CBU 123 and a balance of 1000.0
    And An account with CBU 456 and a balance of 0.0
    When I transfer -200.0 from the first account into the second one
    Then The operation should be denied
    And The first account balance should remain 1000.0
    And The second account balance should remain 0.0

  Scenario: Cannot transfer funds from an account without enough funds
    Given An account with CBU 123 and a balance of 1000.0
    And An account with CBU 456 and a balance of 0.0
    When I transfer 2000.0 from the first account into the second one
    Then The operation should be denied
    And The first account balance should remain 1000.0
    And The second account balance should remain 0.0

  Scenario: An account cannot transfer funds to itself
    Given An account with CBU 123 and a balance of 1000.0
    And The same account with CBU 123
    When I transfer 2000.0 from the first account into itself
    Then The operation should be denied
    And The account balance should remain 1000.0
