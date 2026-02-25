Feature: feature to search for an item on the Catawiki website

  Scenario Outline: Search for an auction item
    Given User is on the Catawiki homepage
    When User searches for "<product_name>"
    And Verify the designated "<product_name>" page details
    Then Click on Lot "<nth>" in search results page and verify
    And Validate Lot details with spcified attributes
    And Check for Age restricted content warning with "<Permission>"

    Examples:
      | product_name | nth  | Permission |
      | train        |  2   | Yes        |
      | car          |  3   | No         |
      | magazine     |  5   | Yes        |
      | magazine     |  3   | No         |