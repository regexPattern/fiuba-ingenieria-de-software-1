Feature: Sign-up a client
  Scenario: Correctly sign-up a client
    Given A person with DNI 123456789, name "Carlos", surname "Castillo", birthdate "10/09/2001" and address "Paseo Colón 950"
    When I sign-up the person as a client
    Then The client DNI should be 123456789

  # Scenario: Cannot sign-up a client with an already-taken DNI
  #   Given a client with DNI 123456789
  #   And a person who want to become a client and has DNI 123456789
  #   When I try to sign-up the person as a client
  #   Then the operation should fail
  #   And the person should not be signed-up as a client
