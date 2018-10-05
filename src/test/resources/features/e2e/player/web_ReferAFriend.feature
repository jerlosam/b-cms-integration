Feature:
  As a player
  I want to join through a referrer link
  So that I can check the relationship between referrer and referee

@player @brands
Scenario: Create relationship between referrer and referee

Given an existing player
When the player navigates to refer page
Then the player can see the RAF button
When the player login through RAF
Then the player can see the sharing links
And the player copy the referrer link
When the referee signs up
Then the relationship is created




