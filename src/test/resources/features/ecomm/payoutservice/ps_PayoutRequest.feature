Feature:
  As an Ops Manager
  I want a player to be able to request a payout through the new Payouts Service
  So that we can commence using the new Payout Console and Detail pages

@ecomm @payouts
Scenario: Full payout request flow using Payout Service

Given an existing player
And an existing withdrawal instrument
And an existing payout request for 100.00 in the payout service
When the user navigates to the Payout Console Page with query string country=us
Then the user is redirected to the authentication page
When the users signs in to the payouts service
Then the user is redirected to the Payout Console Page
When the user navigates to the Payout Details page
Then the Payout Details page is displayed
