Feature:
  As a playerSignup
  I want to open a game of Casino Static
  So that can play

Scenario: Open Caesars Empire for Real

Given an existing player with 100 balance forward
When the player navigates to CAESARS_EMPIRE game
And visual game is displaying
Then the casino game is loaded
And check that UX is correct without env differentiation
