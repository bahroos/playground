Feature:
  @sanity @positive @regression
  Scenario Outline:
    Given that I am a weather broadcaster
    When I query for a given "<city>"
    Then I would like to see the temparature of the city.

    Examples:
    | city|
    | Utrecht|
    | Mumbai |