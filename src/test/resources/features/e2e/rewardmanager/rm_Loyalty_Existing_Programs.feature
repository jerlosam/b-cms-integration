Feature:
  As a Reward Manager - Loyalty user
  I want to retrieve existing loyalty programs
  So that I can access list of them

@reward @loyalty
Scenario: Retrieve existing programs from loyalty-program-service

Given a user logged in Rewards Manager inside Loyalty section
When Existing Programs tab is accessed
Then call to loyalty-program-service is successful