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

  Scenario: Cannot create a branch with a repeated name
    Given A branch with code 984, name "Suc. Belgrano" and address "Cabildo 3456 CABA" 
    When I try to register another branch with code 600, name "Suc. Belgrano" and address "Echeverria 1000 CABA"
    Then The branch register operation should be denied
    And The second branch should not be registered

  Scenario: Successfully create a branch a repeated address
    Given A branch with code 984, name "Suc. Recoleta" and address "Santa Fe 2000 CABA"
    When I try to register another branch with code 600, name "Suc. Barrio Norte" and address "Santa Fe 2000 CABA"
    Then The second branch should be registered
    And Both branches should be located at the same address
