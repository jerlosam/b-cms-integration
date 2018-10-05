Feature:
  As a customer manager
  I want to search an existing transaction
  So that I can check its details

@ecomm @production @player @customer-manager
Scenario: Search transaction by paymentId

Given an existing player
And the existing player has made a deposit
When a customer manager searches a payment by id
Then the payment details are displayed