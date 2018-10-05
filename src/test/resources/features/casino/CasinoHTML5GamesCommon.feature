Feature:
  As a playerSignup
  I want to open a game of Casino HTML5 Games Common
  So that can play

Scenario: Open Blackjack Zappit for Real

Given an existing player with 100 balance forward
When the player navigates to BLACKJACK_ZAPPIT game
Then the casino game is loaded
