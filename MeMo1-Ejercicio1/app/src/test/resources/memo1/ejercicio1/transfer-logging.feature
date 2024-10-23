Feature: Successful transfers are logged
  Scenario: Successfully log a transfer between two accounts
    Given A sender account with CBU 123456789, alias "sender" and a balance of 1000.0
    And A receiver account with CBU 987654321, alias "receiver" and a balance of 250.0
    When I transfer 200.0 from the sender account into the receiver account
    Then The transfer should be logged
    And The transfer log should have a correlative code
    And The transfer log should have a date
    And The transfer log should have a time
    And The logged transfer type should be "transfer"
    And The logged transfered amount should be 200.0
    And The transfer log should be associated with the given accounts

  Scenario: Successfully log a deposit to an account
    Given An account with CBU 123456789, alias "account" and a balance of 1000.0
    When I deposit 200.0 into the account
    Then The deposit should be logged
    And The log should have a correlative code
    And The log should have a date
    And The log should have a time
    And The logged type should be "deposit"
    And The logged amount should be 200.0
    And The log should be associated with the given account

  Scenario: Successfully log a withdrawal from an account
    Given An account with CBU 123456789, alias "account" and a balance of 1000.0
    When I withdraw 200.0 from the account
    Then The withdrawal should be logged
    And The log should have a correlative code
    And The log should have a date
    And The log should have a time
    And The logged type should be "withdrawal"
    And The logged amount should be 200.0
    And The log should be associated with the given account
