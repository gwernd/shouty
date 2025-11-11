Feature: Premium account

  Rules:
* Mention the word "buy" and you lose 5 credits
* Over-long messages cost 2 credits

  Background:
    Given the range is 100
    And Sean is located at 0
    And Lucy is located at 100

  Scenario: Test premium account features
    Given Sean has bought 30 credits
    When Sean shouts 2 over-long messages
    And Sean shouts 3 messages containing the word "buy"
    Then Sean should have 11 credits left