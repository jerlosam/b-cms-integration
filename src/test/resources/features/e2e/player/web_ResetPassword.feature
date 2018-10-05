Feature:
  As a player
  I want to reset my password
  So that I can log in if I forgot it

@player @brands
Scenario: Player reset his password

Given an existing player
When a player reset his password
Then the player can log in with his new password
