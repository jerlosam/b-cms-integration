@brands
Feature:
  As a player
  I want to be able to access reactive chat
  So that I can use it

Scenario: Launch the reactive chat in Contact Us

Given an existing player
When the player navigates to contact us page
And the player clicks on the Chat Now link in Contact Us
Then the reactive chat is displayed

Scenario: Launch the reactive chat in Deposit Payment Methods

Given an existing player
When the player navigates to deposit page
And the player can see the deposit payment method selection page
And the player clicks on the Chat Now link
Then the reactive chat is displayed

Scenario: Reactive chat stays open while navigating

Given an existing player
And the player navigates to contact us page
And the player clicks on the Chat Now link in Contact Us
And the reactive chat is displayed
When the player closes contact us page and navigates to join
Then the reactive chat is displayed
