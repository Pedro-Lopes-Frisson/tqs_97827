Feature: Get Basic Cache Statistics

  Scenario: No requests were made
    When I go over the page 'localhost:3000/cache'
    Then some statistics should appear like 'Requests', 'Misses','Hits'
    And all of them should have the values 0
    And Close Browser

  Scenario: Two requests were made
    When I go over the page 'localhost:3000/citiesList'
    And Type in 'Autauga'
    And Select Option Two Days Ago
    And Click On Search Button
    And Check That Confirmed cases is not zero in 'Autauga'
    And Type in ''
    And Select Option Two Days Ago
    And Click On Search Button
    And I go over the page 'localhost:3000/cache'
    Then some statistics should appear like 'Requests', 'Misses','Hits'
    And there should be 2 requests and misses 1, and hits 1
    And Close Browser
