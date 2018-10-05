Feature:
  As a player
  I want to see teh reset password overlay
  So that I can reset my password

@brands @player
Scenario: Check Reset Password UX-UI

Given an existing player
When a player covers main flows of reset password
Then check that UX is correct
