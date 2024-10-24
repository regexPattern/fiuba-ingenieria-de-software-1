Feature: Branch creation
  Scenario: Successfully create a branch
    Given I create a branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    Then The branch code should be 984
    And The branch name should be "Suc. Belgrano"
    And The branch address should be "Cabildo 3456 CABA"

  Scenario: Cannot create a branch with repeated code
    Given A branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA"
    When I try to register another branch with code 984, name "Suc. Recoleta" and address "Santa Fe 2031 CABA"
    Then The branch register operation should be denied
