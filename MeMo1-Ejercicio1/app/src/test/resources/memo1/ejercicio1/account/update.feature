Feature: Account update
  Scenario: Successfully update an account alias
    Given An account with CBU 123456789 and alias "account"
    When I change the account alias to "anotherAccount"
    Then The account alias should be "anotherAccount"
  
  Scenario: Cannot update an account alias to an already-used alias
    Given An account with CBU 123456789 and alias "account"
    And An account with CBU 987654321 and alias "anotherAccount"
    When I change the first account alias to "anotherAccount"
    Then The operation should be denied
    And The first account alias should remain "account"
    And The second account alias should remain "anotherAccount"

  Scenario: Successfully update an account owner
    Given An account with CBU 123456789
    And A client with DNI 987654321 who is the account owner
    And A client with DNI 456789124
    When I change the account owner to be the second client
    Then The second client should be the account owner
    And The first client should be an account co-owner
    And The second client should not be an account co-owner

  Scenario: Successfully remove an account co-owner
    Given An account with CBU 123456789 and alias "account"
    And A client with DNI 987654321 who is one of the account co-owners
    When I remove the client as an account co-owner
    Then The client is no longer an account co-owner

  Scenario: Cannot remove an account co-owner if it is not a co-owner
    Given An account with CBU 123456789 and alias "account" with no co-owners
    And A client with DNI 123456789 who is one of the account co-owners
    And A client with DNI 987654321 who is not one of the account co-owners
    When I remove the second client as an account co-owner
    Then The operation should be denied
    And The account co-owners should still remain the same

  # TODO
  Scenario: Successfully update an account branch
