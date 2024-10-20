Feature: Sign-up a client
  Scenario: Correctly sign-up a client
    Given A person with DNI 123456789, name "Carlos", surname "Castillo", birthdate "10/09/2001" and address "Paseo Colón 950"
    When I sign-up the person as a client
    Then The client DNI should be 123456789
    And The client name should be "Carlos"
    And The client surname should be "Castillo"
    And The client birthdate should be "10/09/2001"
    And The client address should be "Paseo Colón 950"

  Scenario: Cannot sign-up a client with an already-taken DNI
    Given A client with DNI 123456789, name "Carlos" and surname "Castillo"
    And A person who wants to become a client and has DNI 123456789, name "Eduardo" and surname "Pereira"
    When I try to sign-up the second person as a client
    Then The client sign-up operation should be denied
