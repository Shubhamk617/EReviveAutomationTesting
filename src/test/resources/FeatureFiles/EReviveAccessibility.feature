@Accessibility
Feature: Accessibility Scenario

  @AccessibilityOnLandingPage
  Scenario Outline: Verify Accessibility on ERevive pages
    Given I launch Site url with "<Brand>" Url
    Then I validate accessibility on "<Brand>" page
    Examples:
      | Brand    |
      | E-revive |