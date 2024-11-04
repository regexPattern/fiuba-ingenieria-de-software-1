# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/1
Feature: Transferring funds
  Scenario: Successfully transfer funds into an account
    Given A sender account with CBU 123456789, alias "sender" and a balance of 1000.0
    And A receiver account with CBU 987654321, alias "receiver" and a balance of 0.0
    When I transfer 200.0 from the sender account into the receiver account
    Then The sender account balance should be 800.0
    And The receiver account balance should be 200.0

  Scenario: Cannot transfer a negative amount 
    Given A sender account with CBU 123456789, alias "sender" and a balance of 1000.0
    And A receiver account with CBU 987654321, alias "receiver" and a balance of 0.0
    When I transfer -200.0 from the sender account into the receiver account
    Then The transfer should be denied
    And The sender account balance should remain 1000.0
    And The receiver account balance should remain 0.0
    And The transfer should not be logged

  Scenario: Cannot transfer funds from an account without enough funds
    Given A sender account with CBU 123456789, alias "sender" and a balance of 1000.0
    And A receiver account with CBU 987654321, alias "receiver" and a balance of 0.0
    When I transfer 2000.0 from the sender account into the receiver account
    Then The transfer should be denied
    And The sender account balance should remain 1000.0
    And The receiver account balance should remain 0.0
    And The transfer should not be logged

  Scenario: Cannot transfer funds to a non-existing account
    Given A sender account with CBU 123456789, alias "account" and a balance of 1000.0
    And No receiver account
    When I transfer 2000.0 from the sender account into the receiver account
    Then The transfer should be denied
    And The sender account balance should remain 1000.0
    And The transfer should not be logged

  Scenario: An account cannot transfer funds to itself
    Given A sender account with CBU 123456789, alias "account" and a balance of 1000.0
    And The same account with CBU 123456789 as the receiver
    When I transfer 2000.0 from the sender account into the receiver account
    Then The transfer should be denied
    And The sender account balance should remain 1000.0
    And The transfer should not be logged
