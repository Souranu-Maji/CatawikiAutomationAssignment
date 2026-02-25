Feature: feature to search for an item on the Catawiki website

  Scenario Outline: Search for an auction item
    Given User is on the Catawiki homepage
    When User searches for "<product_name>"
    And Verify the designated "<product_name>" page details
    Then Click on "<nth>" lot in search results page and verify
    And Validate lot details with spcified attributes

    Examples:
      | product_name | nth  |
      | train        |  2   |
      | magazine     |  5   |
      | XYZ@!@#      |  1   |