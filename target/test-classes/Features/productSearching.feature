Feature: feature to search for an item on the Catawiki website

  @Smoke_Test
  Scenario Outline: Search for an auction item with valid product name and verify the details
    Given User is on the Catawiki homepage
    When User searches for "<product_name>"
    And Verify the designated "<product_name>" page details
    Then Click on Lot "<nth>" in search results page and verify
    And Validate Lot details with spcified attributes
    And Check for Age restricted content warning with "<Permission>"

    @Positive_Test
    Examples:
      | product_name | nth  | Permission |
      | train        |  2   | Yes        |

    @Edge_Case_Test
    Examples:
      | product_name | nth  | Permission |
      | car          |  3   | No         |  # During testing, the favorite counter is "zero" for the 3rd lot.
      | Tintin       |  1   | No         |  

    @Exploratory_Test
    Examples:
      | product_name | nth  | Permission |
      | magazine     |  5   | Yes        |
      | magazine     |  3   | No         |

  @Smoke_Test
  Scenario Outline: Search for an auction item with Invalid product name and verify outcome
    Given User is on the Catawiki homepage
    When User searches for "<product_name>"
    And Verify the designated "<product_name>" page details
    Then Validate expected outcome and verify error message 

    @Negative_Test_Search
    Examples:
      | product_name |
      | &%^$**$      |
      | 9999abcd     |