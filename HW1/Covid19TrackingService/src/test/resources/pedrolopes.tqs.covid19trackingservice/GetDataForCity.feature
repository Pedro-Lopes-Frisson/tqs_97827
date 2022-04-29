Feature: Get Country Covid Statistics
  Scenario: Get Data For A Valid Country for Two Days Ago
    When I go over the page 'localhost:3000/'
    And Type in 'Autauga'
    And Select Option Two Days Ago
    And Click On Search Button
    Then I get to see 'confirmed' cases and 'deaths' for 'Autauga'
    And Close Browser
