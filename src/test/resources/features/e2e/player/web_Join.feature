Feature:
  As a visitor
  I want to signup on the site
  So that I can start playing

@player @brands
Scenario: Visitor successfully signs up

When a new player signs up
Then the player is redirected to the correct page
And player is logged in