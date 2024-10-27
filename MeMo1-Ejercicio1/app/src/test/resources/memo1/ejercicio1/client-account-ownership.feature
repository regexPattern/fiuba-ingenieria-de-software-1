Feature: Client account ownership
  Scenario: Successfully assign a client as the owner of an account
    Given A registered account with CBU 123456789 and alias "account"
    And A registered client with DNI 987654321, name "Carlos" and surname "Castillo"
    When I assign the client as the account owner
    Then The account owner should be the client

  Scenario: Successfully assign a client as a co-owner of an account
    Given A registered account with CBU 123456789 and alias "account"
    And A registered client with DNI 987654321, name "Carlos" and surname "Castillo"
    When I assign the client as one of the account co-owner
    Then One of the account co-owners should be the client

  Scenario: Cannot assign a client as the owner and co-owner
    Given A registered account with CBU 123456789 and alias "account"
    And A registered client with DNI 987654321, name "Carlos" and surname "Castillo" who is the owner of the account
    When I assign the client as one of the account co-owner
    Then The ownership assignment operation should be denied
    And The client with DNI 987654321 should remain as the account owner
    And The account co-owners should remain the same

  Scenario: Cannot assign multiple owners
    Given A registered account with CBU 123456789 and alias "account"
    And A registered client with DNI 987654321, name "Carlos" and surname "Castillo" who is the owner of the account
    And A registered client with DNI 456789012, name "Eduardo" and surname "Pereira"
    When I assign the client as the account owner
    Then The ownership assignment operation should be denied
    And The client with DNI 987654321 should remain as the account owner
    And The account co-owners should remain the same

  # Scenario: Successfully label a co-owner who is married to the owner
  #   Given An account with CBU 123456789 and alias "account"
  #   And A client with DNI 987654321, name "Carlos" and surname "Castillo" who is the owner of the account
  #   And A client with DNI 456789012, name "Lea" and surname "Seydoux" who is the wife of the account owner
  #   When I assign the client with DNI 456789012 as one of the account co-owner
