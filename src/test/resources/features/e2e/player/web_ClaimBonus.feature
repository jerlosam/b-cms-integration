Feature:
  As a Player
  I want to claim an upfront bonus
  So that I increase my funds

@wallet-bonus @transaction @wallet @reward @brands
Scenario: Player claims a new upfront bonus

Given an existing player
When the player claims the first available upfront bonus
Then the player has positive balance
When the player forfeits the first active bonus
Then the player has Zero balance
And two bonus transactions are displayed to the player
