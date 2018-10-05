Feature:
  As a Portal User
  I want to enter the portal and go to some app
  So that I can start using that app

@internal @customer-manager
Scenario: Enter application from Portal

Given a user logged in Portal
When the user navigates to one of the available apps
Then the user is redirected to another app