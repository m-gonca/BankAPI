# BankApi
This project's main goal is to create an API for a Banking System that can be managed by Admins, Account Holders and ThirdParties(like an atm). It's required to generate a Database with the necessary tables to store all necessary information about users, accounts and transgferences.

## Instructions
Set a MySql instance, with a schema created called <i>bankapi</i>.
Set private properties with the named schema and your password.
Run the programme in Spring and use Postman or any other HTTP petition programme to test the routes.

## Testing
Tests have been done for all repositories and controllers.

## Main technologies used
- Java
- Unit testing
- Spring
- SpringBoot
- JPA

## Class diagram

https://lucid.app/lucidchart/a21c4c17-b3f1-4fb0-a592-a5db876b8ec4/edit?invitationId=inv_cf9e7068-c8f0-4bed-ac06-40d900d64e1c&page=0_0#

## Required Methods
- Creation of Admins, Account Holders and ThirdParties.
- Creation of accounts and binding to existing Account Holders.
- There are four types of accounts: Checking, Student, Savings and Credit Card. 
- Student accounts are generated automatically when creating a checking account if the user is under 24 years old.
- There are penalty fees to be applied in accounts with minimum Balance drop below it.
- There are interests to be applied anually in savings account and monthly in credit card accounts.
- Admins can create other admins, account holders and thirdparties. They can also check accounts, update balances and delete accounts.
- Account Holders can make transferences and check only their accounts.
- Third parties can send or receive money to accounts.
- Security is applied to users secret keys and is implemented in all the api.


## Process
- Definition of class diagram and creation of basic structure.
- Creation of database with Spring and confirmation of this creation with MySQL.
- Development of main with all necessary methods.
- Unit testing of those methods and main debugging.
- Adding security
