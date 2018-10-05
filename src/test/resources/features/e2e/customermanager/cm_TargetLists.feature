Feature:
  As a customer manager
  I want to add and remove a user as targeted in a bonus
  So that I can check bonus list functionality

@reward @player @customer-manager
Scenario: Add and remove a user as targeted in a bonus

Given an existing player
When a customer manager navigates to the player Target Lists page
When the customer manager adds the player as targeted of the first bonus
Then bonus is displayed in player lists
When the customer manager removes the player as targeted of the first bonus
Then bonus is displayed in available lists