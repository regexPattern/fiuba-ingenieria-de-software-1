Feature: Closing a branch
  Scenario: Succesfully close a branch
    Given An opened branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    When I close the branch
    Then The branch should be closed
    # TODO: I cannot add accounts to this branch???

  Scenario: Succesfully reopen a branch
    Given A closed branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    When I reopen the branch
    Then The branch should be reopened
  
  Scenario: Cannot close a branch that has not been registered
    Given I try to close a branch with code 984 that has not been registered
    Then The branch closing operation should be denied

  Scenario: Cannot reopen a branch that has not been registered
    Given I try to reopen a branch with code 984 that has not been registered
    Then The branch reopening operation should be denied
