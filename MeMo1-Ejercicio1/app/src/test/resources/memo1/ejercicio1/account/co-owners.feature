# HDU: https://github.com/regexPattern/2024_2C_108535_Ejercicio1/issues/12
Feature: Adding account co-owners
  Scenario: Successfully add co-owners to an account
    Given A client with DNI 96113425, name "Carlos" and surname "Castillo"
    And An account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    When I add client with DNI 78612311, name "Eduardo" and surname "Pereira" as one of the account co-owners
    Then The client with DNI 78612311 should be one of the account co-owners
    And The client with DNI 96113425 should still be the account owner

  Scenario: Cannot set the account owner as a co-owner
    Given An account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    When I try to set the account owner as one of the account co-owners
    Then The operation should be denied
    And The client with DNI 96113425 should still be the account owner
    And The account co-owners should remain the same

  Scenario: Cannot add the same co-owner more than once
    Given An account with CBU 123456789, alias "account1", branch with code 1 and owner with DNI 96113425
    And A client with DNI 78612311, name "Eduardo" and surname "Pereira" who is one of the account co-owners
    When I try to set the client with DNI 78612311 as one of the account co-owners
    Then The operation should be denied
    And The account co-owners should remain the same
