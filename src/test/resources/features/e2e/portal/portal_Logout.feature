Feature:
  As a Portal User
  I want to go back to Portal after logout
  So that my user experience is great

@internal @customer-manager
Scenario: Logout from the application

Given a user logged in Customer Manager
When the user does logout
Then the user is on Portal login page