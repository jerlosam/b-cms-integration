Feature:
  As a player
  I want to see my messages
  So that I can read and manage them

@player @brands
Scenario: Player see the list of messages

Given an existing player
When has messages
And the player checks a message
When clicks the button to delete selected
Then the message is deleted
