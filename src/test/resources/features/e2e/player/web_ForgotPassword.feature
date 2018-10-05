Feature:
  As a player
  I want to reset my password
  So that I can log in if I forgot it

@player @brands
Scenario: Player request a reset password link

Given an existing player
When a player request to reset his password
Then a reset password link is sent
