Feature:
  As a Reward Manager
  I want to create a bonus
  So that players can be rewarded

@reward
Scenario: Create Bonus on Reward Manager

When a reward manager user creates a new draft
Then the draft is displayed in the draft list
When a reward manager user converts the draft into a new bonus
Then the bonus is displayed in the bonus list
And the bonus is available for a new player
When a reward manager user edits the bonus
Then the bonus is displayed in the bonus list
When a reward manager user delete the bonus
Then the bonus is not displayed in the bonus list