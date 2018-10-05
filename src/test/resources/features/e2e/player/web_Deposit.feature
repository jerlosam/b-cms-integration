Feature:
  As a playerSignup
  I want to able to deposit
  So that I can see the details needed for depositing

@production @ecomm @brands
Scenario: Go to deposit page

Given an existing player without pinCode
When the player navigates to deposit page
And the player create a pinCode
Then the player can see the deposit page
