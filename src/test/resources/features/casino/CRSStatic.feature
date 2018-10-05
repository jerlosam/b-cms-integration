Feature:
  As a playerSignup
  I want to open a game of CRS Static
  So that can play

Scenario: Open Arabian Tales for Real

Given an existing player with 100 balance forward
When the player navigates to ARABIAN_TALES game
And visual game is displaying
Then the casino game is loaded
And check that UX is correct
