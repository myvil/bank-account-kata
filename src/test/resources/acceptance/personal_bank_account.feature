Feature: Bank account experience

  Bank client want ot deposit withdraw money from his account and list account statement

  Scenario Outline: Bank account deposit
    Given I am a client with id "<client_id>" and my account number is "<account_id>" with initial balance of "<initial_balance>" euro
    When I make a deposit of "<amount>" euros in my bank account
    Then the transaction is done and the final balance is "<final_balance>"
    Examples:
      | client_id | account_id | amount  | initial_balance | final_balance |
      | abcd      | 1231       | 1000.00 | 0.00            | 1000.00       |
      | abcd      | 1232       | 3000.00 | 1000.00         | 4000.00       |

  Scenario Outline: Bank account withdrawal
    Given I am a client with id "<client_id>" and my account number is "<account_id>" with initial balance of "<initial_balance>" euro
    When I make a withdrawal of "<amount>" euros in my bank account
    Then the transaction is done and the final balance is "<final_balance>"
    Examples:
      | client_id | account_id | amount  | initial_balance | final_balance |
      | abcd      | 1233       | 1000.00 | 3000.000        | 2000.00       |
      | abcd      | 1234       | 2000.00 | 2000.00         | 0.00          |

  Scenario: I want to see history
    Given I am a client with id "abc" and account number "1234" and no money
    And I have the following transactions to execute
      | DEPOSIT  | 3000.00  |
      | WITHDRAW | 100.00   |
      | WITHDRAW | 200.00   |
    When I search for my account statement
    Then I can find 1 deposit and 2 withdrawals

