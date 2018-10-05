Feature:
  As a customer manager
  I want to do a manual account entry for a player
  So that the player's balance is adjusted

@wallet-bonus @transaction @wallet @customer-manager
Scenario: Manual account entry for a player

Given an existing player
When a customer manager performs a manual account entry
Then the player balance displayed in customer manager is greater than zero
And a transaction with the manual account entry reference is displayed on the transactions list
