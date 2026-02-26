Feature: feature to check language consistency in website while searching products

    @Smoke_Test
    Scenario Outline: Search for auction item after switching language
        Given User is on the Catawiki homepage
        When User change language to "<language>" and validate the language change
        Then User searches for "<product_name>"


        Examples:
        | product_name | language |
        | train        |  GERMAN  |
        | Tintin       |  ENGLISH |

        @Negative_Test
        Examples:
        | product_name | language |
        | car          | JAPANESE |