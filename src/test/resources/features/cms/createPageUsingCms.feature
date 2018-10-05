Feature:
  As a CMS User
  I want to validate that the page is loaded as expected
  So that I can setup the page using CMS

@cms
Scenario: Page is created using CMS

Given a page is created using CMS
When the tester created a Promotion Page
Then the page should display the correct fields
