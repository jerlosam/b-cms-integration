Feature:
  As a Reward Manager
  I want to access Loyaly section
  So that we can create and/or list loyalty programs

@reward @loyalty
Scenario: Accesing Loyalty section in Rewards Manager

Given a user logged in Rewards Manager inside Loyalty section
Then loyalty header is displayed