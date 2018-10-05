Feature:
  As a player
  I want to update my player settings
  So that the player settings are up to date

@player @brands
Scenario: Update player details

Given an existing player
When a player updates his settings
Then the player settings are updated