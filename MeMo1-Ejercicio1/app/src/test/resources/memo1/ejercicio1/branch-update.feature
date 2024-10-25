Feature: Branch update
  Scenario: Successfully update a branch name and address
    Given A branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    When I update the branch with code 984 name to "Suc. Recoleta"
    And I update the branch with code 984 address to "Santa Fe 2000 CABA"
    Then The branch code should be 984
    And The branch name should be "Suc. Recoleta"
    And The branch address should be "Santa Fe 2000 CABA"

  Scenario: Successfully set a branch address as the same as another branch address
    Given A branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    And Another branch with code 100, name "Suc. Recoleta" and address "Santa Fe 2000 CABA"
    When I update the branch with code 984 address to "Santa Fe 2000 CABA"
    Then The branch update operation should not fail
    And The branch with code 984 address should remain "Santa Fe 2000 CABA"
    And The branch with code 100 address should remain "Santa Fe 2000 CABA"

  Scenario: Cannot set a branch name as the same as another branch name
    Given A branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    And Another branch with code 100, name "Suc. Recoleta" and address "Santa Fe 2000 CABA"
    When I update the branch with code 984 name to "Suc. Recoleta"
    Then The branch update operation should fail
    And The branch with code 984 name should remain "Suc. Belgrano"
    And The branch with code 100 name should remain "Suc. Recoleta"
