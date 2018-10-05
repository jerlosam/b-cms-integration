Feature:
  As a Portal User
  I want to switch between enterprise applications
  So that my user experience is great

@internal @customer-manager
Scenario: Switch between applications

Given a user logged in Rewards Manager
And Customer Manager is available
When the user switches the app to Customer Manager
Then the user is redirected to another app