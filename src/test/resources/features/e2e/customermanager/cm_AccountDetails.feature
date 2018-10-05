Feature:
  As a customer manager
  I want to see the player's account Details
  So that I can provide him support

@production @ecomm @player @wallet-bonus @wallet @customer-manager
Scenario: See player account details

Given an existing player
And the player's balance is zero
When a customer manager navigates to the player account details
Then the player details are displayed
And the player balance information is displayed on the page
And the player's PPC is displayed
