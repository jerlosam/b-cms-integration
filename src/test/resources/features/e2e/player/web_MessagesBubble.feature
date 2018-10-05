Feature:
  As a player
  I want to see my messages
  So that I can read and manage them

@player @brands
Scenario: Player see the list of messages

Given an existing player
When has unread messages
Then the messages bubble is displayed
