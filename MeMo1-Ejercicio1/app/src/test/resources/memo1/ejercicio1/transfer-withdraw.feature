Feature: Withdrawing money
  
  Scenario: Successfully withdraw money when the balance is sufficient
    Given An account with CBU 123456789, alias "account" and a balance of 1000.0
    When I withdraw 300.0 from the account
    Then The account balance should be 700.0

  Scenario: Cannot withdraw more money than available balance
    Given An account with CBU 123456789, alias "account" and a balance of 1000.0
    When I try to withdraw 1100.0 from the account
    Then The operation should be denied due to insufficient funds
    And The account balance should remain 1000.0
