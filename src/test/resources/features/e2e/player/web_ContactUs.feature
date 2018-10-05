Feature:
  As a player
  I want to be able to access to contact us
  So that I can see the contact us page

@player @brands
Scenario: Go to contact us page

Given an existing player
When the player navigates to contact us page
Then the player can see the contact us page
