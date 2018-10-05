Feature:
  As an operations manager
  I want to see the routing payment method rules
  So that I can manage them

@production @ecomm @routing
Scenario: Go to payment method rules page

When a operations manager signs in to ecomm routing services
And the operations manager navigates to payment method rules page
Then the operations manager can see the payment method rules
