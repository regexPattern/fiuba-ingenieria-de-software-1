# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/26
Feature: Removing account owners
 Scenario: Successfully remove a co-owner from an account
    Given An account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    And A client with DNI 78612311, name "Eduardo" and surname "Pereira" who is one of the account co-owners
    When I remove the client with DNI 78612311 from the account co-owners
    Then The client with DNI 78612311 should not be one of the account co-owners
    And The client with DNI 96113425 should still be the account owner

  Scenario: Cannot remove a client who is not a co-owner
    Given An account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    When I try to remove a client with DNI 78612311 who is not a co-owner
    Then The operation should be denied
    And The account co-owners should remain the same
