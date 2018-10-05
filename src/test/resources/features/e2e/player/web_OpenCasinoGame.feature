Feature:
  As a playerSignup
  I want to open a casino game
  So that can play

@production @casino @brands
Scenario: Open a Casino Game for Real

Given an existing player
When the player navigates to a casino game
Then a casino game is launched
