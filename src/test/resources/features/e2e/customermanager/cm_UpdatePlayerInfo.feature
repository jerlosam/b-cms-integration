Feature:
  As a customer manager
  I want to update the player details
  So that the player details are up to date

@production @player @customer-manager
Scenario: Update player details

Given an existing player
When a customer manager updates the player details
Then the player details are updated