@demo
Feature: validate site launch
  @ValidateSiteLaunc
  Scenario Outline: Verify Site Launch
    Given I launch Site url with "<Brand>" Url
    Examples:
      | Brand    |
      | E-revive |