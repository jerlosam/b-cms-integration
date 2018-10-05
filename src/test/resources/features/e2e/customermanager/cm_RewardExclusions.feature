Feature:
  As a customer manager
  I want to update the player reward exclusions
  So that I can hide some type of rewards to the player

@reward @player @customer-manager
Scenario: Manage player reward exclusions

Given an existing player
When a customer manager navigates to the player reward exclusions page
Then the player reward exclusions are displayed
When a customer manager excludes all bonuses
Then no bonuses are returned as available
