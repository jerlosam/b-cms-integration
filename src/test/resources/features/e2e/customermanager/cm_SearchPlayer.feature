@player
Feature:
  As a customer manager
  I want to search an existing player
  So that I can provide him support

@production @customer-manager
Scenario: Search player by email

Given two existing players with similar email (1 character different)
When a customer manager searches a player by email
Then the player information is displayed on the results

Scenario: Search player by accountNumber
Given an existing player
When a customer manager navigates to the player account details
And a customer manager writes down the player account number
And a customer manager searches player by account number
Then the player details are displayed