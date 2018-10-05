Feature:
  As a customer manager
  I want to create a job in bulk manager
  So that I can interact with many players at once

@bulk
Scenario: Create job of type FORCE_PHONE_UPDATE

When the user creates a Force Phone update job
Then the job is successfully executed and completed
And pending action and internal message are displayed in Backoffice properly
