Feature:
  As a CSR
  I want to see the transactions when a player places a bet
  So that I can review them

@transaction @wallet @customer-manager
Scenario: Single bet place sent to the Wallet system

Given an existing player
When the player places a single bet
Then the statement is displayed in customer manager
