Feature:
  UserAgent=Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Mobile Safari/537.36
  As a playerSignup
  I want to open a game of Casino HTML5 Games
  So that can play

Scenario: Open Fruit Frenzy for Real

Given an existing player with 100 balance forward
When the player navigates to FRUIT_FRENZY game
And visual game is displaying
Then the casino game is loaded
And check that UX is correct without env differentiation
