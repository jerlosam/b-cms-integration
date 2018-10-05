Feature:
  As a playerSignup
  I want to open a game of Casino Studio Games
  So that can play

Scenario: Open Blackjack for Real

Given an existing player with 100 balance forward
When the player navigates to BLACKJACKHTML5 game
Then the casino game is loaded
