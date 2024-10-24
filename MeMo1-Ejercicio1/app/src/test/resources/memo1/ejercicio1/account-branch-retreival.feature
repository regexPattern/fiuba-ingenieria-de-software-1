Feature: Account branch retreival
  Scenario: Successfully retreive an account's branch information
    Given A branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    And An account with CBU 123456789 that was created in the given branch
    When I check which branch the account was created in
    Then The branch code should be 984
    And The branch name should be "Suc. Belgrano"
    And The branch address should be "Cabildo 3456 CABA"
