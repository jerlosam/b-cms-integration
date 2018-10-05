Feature:
  As a customer manager
  I want to accesss to related accounts
  So that I can check the relationship between referrer and referee

@raf @player @customer-manager
Scenario: Create relationship between referrer and referee

Given an existing player
And referee existing player
When a customer manager navigates to related accounts
Then the relationship between referrer and referee is created


@raf @player
Scenario: Create relationship between affiliate and referee

Given an existing player
And referee existing alice affiliate
When a customer manager navigates to related accounts
Then the relationship between affiliate and referee is created
And affiliate is displayed in backoffice after clicking in relationship

