Feature:
  As a player
  I want to see my messages
  So that I can read and manage them

@player @brands
Scenario: Player see the list of messages

Given an existing player
When has messages
And player opens a message
Then the player can see the message details
