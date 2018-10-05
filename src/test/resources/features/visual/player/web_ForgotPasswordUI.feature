Feature:
  As a player
  I want to see the forgot password overlay
  So that I can request a reset password link

@brands @player
Scenario: Check Forgot Password UX-UI

Given an existing player
When a player covers main flows of forgot password
Then check that UX is correct
