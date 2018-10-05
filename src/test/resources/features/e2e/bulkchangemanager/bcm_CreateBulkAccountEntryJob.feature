Feature:
  As a customer manager
  I want to create a job in bulk manager
  So that I can interact with many players at once

@bulk
Scenario: Create job of type BULK_ACCOUNT_ENTRY

Given an existing player
And bulk account entry data for the csv file
When the user creates a Bulk Account Entry job
Then the job is successfully executed and completed
And the player is found in customer manager and the player balance displayed is greater than zero
And a transaction with the bulk account entry reference is displayed on the transactions list
And an activity with Bulk Account Entry action is displayed on the activities list
