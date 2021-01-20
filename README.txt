Name: Philip Crane
Project: Project Zero Banking Application
Features Completed:
1.  An unregistered user can register by creating a user name and password.
2.  A registered user can login with their user name and password.
3.  A user can view their own accounts and balances.
4.  A user can create an account.
5.  A user can delete an account with zero balance.
6.  A user can deposit and withdraw money from an account.
7.  A user can perform multiple deposits or withdrawals in a session.
8.  A user can logout.
9.  A Superuser can view all users accounts.
10. A Superuser can create accounts for any user.
11. A Superuser can update any users account or profile.
12. A Superuser can delete any and all accounts with zero balance.
13. A Superuser can delete any users with no money in an account.
14. A Superuser can delete all users if no one has money in an account.
15. Validation or notice of failure accompanies all user transactions.
16. Use of the DAO design pattern.
17. Database connection information in a properties (util) file.
18. Three stored procedures used.
19. Three sequences used. One for user_id, one for account_id, and one for recording 
	transactions (t_key).
20. Scanner used for user input.
Bonus.
21. Users transactions are recorded.
22. A User can view an open accounts transaction history.
23. A Superuser can view all transactions that have ever been made on accounts that
	are open or have been deleted.

	
Below you will find all the necessary commands to set up the tables, procedures, 
sequences, and constraints to recreate the Bank of Revature on your computer.

NOTE: You will be required to put in your aws credentials into the JDBCConnection 
	class found in the util package.

SQL Developer setup:

CREATE SEQUENCE person_seq
    START WITH 1
    INCREMENT BY 1;
    
CREATE SEQUENCE account_seq
    START WITH 1
    INCREMENT BY 1;
    
CREATE SEQUENCE trans_seq
    START WITH 1
    INCREMENT BY 1;
    
    
CREATE TABLE person (
    user_id NUMBER(10) PRIMARY KEY,
    user_type VARCHAR2(10) NOT NULL,
    fname VARCHAR2(50) NOT NULL,
    lname VARCHAR2(50) NOT NULL,
    username VARCHAR2(50) NOT NULL,
    user_pass VARCHAR2(50) NOT NULL);
    
CREATE TABLE account (
    account_id NUMBER(10) PRIMARY KEY,
    user_id NUMBER (10),
    balance NUMBER (10) NOT NULL,
    account_type VARCHAR(10) NOT NULL);
    
ALTER TABLE account ADD CONSTRAINT fk_account_person
    FOREIGN KEY (user_id) REFERENCES person(user_id)
    ON DELETE CASCADE;
    
ALTER TABLE account MODIFY (user_id NOT NULL);
    
CREATE OR REPLACE PROCEDURE add_person(
    user_type VARCHAR2,
    fname VARCHAR2,
    lname VARCHAR2,
    username VARCHAR2,
    user_pass VARCHAR2)
IS
BEGIN
    INSERT INTO person VALUES (
        person_seq.nextval,
        user_type,
        fname,
        lname,
        username,
        user_pass);
END;

CREATE OR REPLACE PROCEDURE add_account(
    user_id NUMBER, 
    balance NUMBER,
    account_type VARCHAR)
IS
BEGIN
    INSERT INTO account VALUES(
    account_seq.nextval,
    user_id,
    balance,
    account_type);
END;

INSERT INTO person VALUES(
0,
'super',
'Bank', 
'Manager', 
'Manager', 
'ss');

SELECT * FROM person;

/* No foreign keys were use to allow for data to be retained
after a customer or account has been deleted. I am allowing orphen
entries because this is an archive. It can be used to recreate
account activity after the account or user has been deleted.*/
CREATE TABLE transaction (
    date_in DATE PRIMARY KEY,
    account_id NUMBER(10) NOT NULL ,
    user_id NUMBER(10) NOT NULL,
    account_type VARCHAR2(10) NOT NULL,
    trans_type VARCHAR2(20) NOT NULL,
    amount NUMBER(10));

ALTER SESSION SET nls_date_format='DD/MM/YYYY HH24:MI:SS';

CREATE OR REPLACE PROCEDURE add_transaction(
    account_id NUMBER,
    user_id NUMBER,
    account_type VARCHAR2,
    trans_type VARCHAR2,
    amount NUMBER)
IS
BEGIN
   INSERT INTO transaction VALUES (
        SYSDATE,
        account_id,
        user_id,
        account_type,
        trans_type,
        amount);
    
END;

