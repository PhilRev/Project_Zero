package dev.crane.runBank;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import dev.crane.entities.Account;
import dev.crane.entities.Person;
import dev.crane.entities.Transaction;
import dev.crane.exceptions.InputValidator;
import dev.crane.exceptions.InputValidatorImpl;
import dev.crane.exceptions.TooShortException;
import dev.crane.exceptions.UnallowedException;
import dev.crane.service.AccountService;
import dev.crane.service.AccountServiceImpl;
import dev.crane.service.TransactionService;
import dev.crane.service.TransactionServiceImpl;
import dev.crane.service.UserService;
import dev.crane.service.UserServiceImpl;


public class BankApp {

	private static Scanner scan = new Scanner(System.in);
	
	private static UserService userServ = new UserServiceImpl();
	private static AccountService accServ = new AccountServiceImpl();
	private static TransactionService tranServ = new TransactionServiceImpl();

	private static boolean bankOpen = true;
	private static boolean isValid = false;
	private static boolean logedIn = false;
	private static int checking = 0;
	private static int savings = 0;

	private static List<Person> allUsers = new ArrayList<Person>();
	private static List<Account> allAccounts = new ArrayList<Account>();
	private static List<Transaction> trans = new ArrayList<Transaction>();
	private static Account acc = new Account();
	private static Person logedInUser = null;
	private static Person customer = null;

	private static Iterator<Account> accIt = allAccounts.iterator();
	private static Iterator<Person> it = allUsers.iterator();
	private static Iterator<Transaction> traIt = trans.iterator();
	private static String test = "";
	private static String username = "";

	public static void main(String[] args) {	
	
		while (bankOpen) {
			boolean hasSav = false;
			boolean hasChk = false;
			boolean noSavBal = true;
			boolean noChkBal = true;

			int action = 0;
			int action2 = 0;
			double amount = 0;
			Account temp = new Account();

			boolean validUser = false;

			while (!validUser) {
				allUsers = userServ.viewAllUsers();

				System.out.println("*******************************************"
						+ "*********\n*         Welcome to the Bank of Revature"
						+ "!         *\n***************************************" + "*************\n");
				System.out.println("Please enter your username or type"
						+ " \"new\" if you would \nlike to register as a new user.");
				String currentUser = scan.nextLine();
				while (currentUser.equals("")) {
					currentUser = scan.nextLine();
				}
				if (currentUser.toLowerCase().equals("new")) {
					System.out.println("Welcome new customer! \nTo create you account we need some "
							+ "information \nfrom you first. What is your first name?");
					String fName = scan.nextLine();
					while(fName.equals("")) {
						fName = scan.nextLine();
					}
					System.out.println("What is your last name?");
					String lName = scan.nextLine();
					while(lName.equals("")) {
						lName = scan.nextLine();
					}
					System.out.println("Hello " + fName + " " + lName + "! \nWhat would you like your"
							+ " username to be?\nPlease make the username a single word at least 6 letters long.");	
					isValid = false;
					while (!isValid) {
						username = scan.nextLine();
						isValid = validInput(username, "username");						
					}
					System.out.println("Please choose a password.\nYour password must be 6 characters long and contain no spaces.");
					isValid = false;
					String password = "";
					while (!isValid) {
						while(!isValid) {
							password = scan.nextLine();
							isValid = validInput(password, "password");
						}
						System.out.println("Please confirm your password.");
						String passCheck = scan.nextLine();
						if (!passCheck.equals(password)) {
							isValid = false;
							System.out.println("Passwords did not match. \nPlease " + "reenter your password.");
						}
					}
					isValid = userServ.registerUser("Standard", fName, lName, username, password);
					if (isValid) {
						System.out.println("New account created. You can now sign in " + "using your new credentials.\n\n");
					} else {
						System.out.println("Error creating account.");
					}
				} else {
					allUsers = userServ.viewAllUsers();
					it = allUsers.iterator();
					while (it.hasNext()) {
						logedInUser = it.next();
						test = logedInUser.getUsername();
						if (test.equals(currentUser)) {
							validUser = true;
							break;
						}
					}

					if (validUser) {
						System.out.println("Please enter your password.");
						String password = scan.nextLine();
						if (password.equals(logedInUser.getUser_pass())) {
							logedIn = true;
						} else {
							System.out.println("Sorry, the username and password to not match.\n\n");
							validUser = false;
						}
					}
					if (!validUser) {
						System.out.println("Username not found.\n\n");
					}
				}
			}

			if (logedInUser.getUser_type().equals("super")) {
				while (logedIn) {
					allAccounts = accServ.viewAllAccounts();
					allUsers = userServ.viewAllUsers();
					it = allUsers.iterator();
					accIt = allAccounts.iterator();
					System.out.println("\nAll customer accounts.\n");
					while (it.hasNext()) {
						accIt = allAccounts.iterator();
						customer = it.next();
						if (customer.getUser_type().equals("super")) {
							System.out.println(customer + " **Bank Employee**");
						} else {
							System.out.println(customer);
						}
						while (accIt.hasNext()) {
							temp = accIt.next();
							if (temp.getUser_id() == customer.getUser_id()) {
								System.out.println("     " + temp);
							}
						}
					}
					System.out.println("What would you like to do?");
					
					System.out.println("Enter \"1\" to register new user.");
					
					System.out.println("Enter \"2\" to edit an exsisting user.");
					
					System.out.println("Enter \"3\" to view all account transactions.");
					
					System.out.println("Enter \"4\" to delete all user accounts with zero balance.");
					
					System.out.println("Enter \"5\" to delete all users.");
					
					System.out.println("Enter \"6\" to log out.");
					
					test = scan.nextLine();
					while(test.equals("")) {
						test = scan.nextLine();
					}
					action = Integer.parseInt(test);
					if (action == 1) {
						System.out.println("Enter first name?");
						String fName = scan.nextLine();
						System.out.println("Enter last name?");
						String lName = scan.nextLine();
						String username = "";
						System.out.println("What would " + fName + " " + lName + " like as a "
								+ "username? \nUsername must be at least 6 letters long.");
	/////////// Improper character and too short exceptions.	
						isValid = false;
						while (!isValid) {
							while (!isValid) {
								username = scan.nextLine();
								if (username.equals("")) {
									username = scan.nextLine();
								}
								isValid = validInput(username, "username");
							}
						}
						System.out.println("What would " + fName + " " + lName + " like as a  password.");
						isValid = false;
						String password = "";
	/////////// Improper character and too short exceptions.
						while (!isValid) {
							while (!isValid) {
								password = scan.nextLine();
								isValid = validInput(password, "password");
							}
							System.out.println("Please confirm the password.");
							String passCheck = scan.next();
							if (!passCheck.equals(password)) {
								isValid = false;
								System.out.println("Passwords did not match. \nPlease " + "reenter your password.");
							}
						}
						isValid = userServ.registerUser("Standard", fName, lName, username, password);
						if (isValid) {
							System.out.println("New account created.\n");
						} else {
							System.out.println("Error occured attempting to create account.");
						}
					} else if (action == 2) {
						System.out.println("Enter Customer ID.");
						action = scan.nextInt();
						isValid = false;
						customer = userServ.getUserByID(action);
						while (customer == null) {
							System.out.println("Invalid entry. \nPlease choose a valid customer or enter \"0\" to exit.");
							allUsers = userServ.viewAllUsers();
							it = allUsers.iterator();
							while (it.hasNext()) {
								customer = it.next();
								if (customer.getUser_type().equals("super")) {
									System.out.println(customer + " **Bank Employee**");
								} else {
									System.out.println(customer);
								}
							}
							action = scan.nextInt();
							if (action == 0) {
								break;
							}
							customer = userServ.getUserByID(action);
						}

						if (action == 0) {
							action = 7;
						}else {
							System.out.println("Enter desired action. \n 1. Edit user type\n 2. "
									+ "Edit name\n 3. Edit username\n 4. Edit password \n 5. Accounts "
									+ "menu \n 6. Delete user \n 7. Exit");
							action = scan.nextInt();
							isValid = false;
						}
						switch (action) {
						case 1:
							System.out.println("Enter type of user. \"standard\" or \"super\"");
							test = scan.next();
							customer.setUser_type(test);
							isValid = userServ.updateUser(customer);
							break;
						case 2:
							System.out.println("Enter new first name.");
							test = scan.next();
							customer.setfName(test);
							System.out.println("Enter new last name.");
							test = scan.next();
							customer.setlName(test);
							isValid = userServ.updateUser(customer);
							break;
						case 3:
							System.out.println("Enter new username.");
							isValid = false;
							while (!isValid) {
								
								username = scan.nextLine();
								if (username.equals("")) {
									// This prevents the nextline error
								} else {
									isValid = validInput(username, "username");
									if (!isValid) {
										System.out.println("That username is already in use. " +
										"\nPlease choose a different username.");
									}
								}
							}
							customer.setUsername(username);
							isValid = userServ.updateUser(customer);
							break;
						case 4:
							System.out.println("Enter new password. \nMust be 6 letters long and contain no spaces.");
							test = scan.next();
							customer.setUser_pass(test);
							isValid = userServ.updateUser(customer);
							break;
						case 5:
							allAccounts = accServ.viewUserAccounts(customer.getUser_id());
							accIt = allAccounts.iterator();

							if (!accIt.hasNext()) {
								System.out.println("Currently there are no accounts with the Bank "
										+ "of Revature.\nEnter \"1\" to create a new Checking account."
										+ " \nEnter \"2\" to create a new Savings account. \nEnter "
										+ "\"6\" to delete an account. \nEnter \"7\" to exit.");
								isValid = false;
								while (!isValid) {
									action2 = scan.nextInt();
									if (action2 == 1 || action2 == 2 || action2 == 6 || action2 == 7) {
										isValid = true;
									} else {
										System.out.println("\"" + action2 + "\" is an invalid choice. "
												+ "Please enter one of the choises listed.");
									}
									
								}
							} else {
								System.out.println("Here are your open accounts and balances.");
								hasSav = false;
								hasChk = false;
								while (accIt.hasNext()) {
									temp = accIt.next();
									System.out.println(temp);
									if (temp.getAccount_type().equals("Savings")) {
										hasSav = true;
										savings = temp.getAccount_id();
										if (temp.getBalance() <= 0.00) {
											noSavBal = true;
										} else {
											noSavBal = false;
										}
									} else if (temp.getAccount_type().equals("Checking")) {
										hasChk = true;
										checking = temp.getAccount_id();
										if (temp.getBalance() <= 0.00) {
											noChkBal = true;
										} else {
											noChkBal = false;
										}
									}
								}
								System.out.println("What would you like to do?");
								if (!hasChk) {
									System.out.println("Enter \"1\" to create a Checking account.");
								} else if (!hasSav) {
									System.out.println("Enter \"2\" to create a Savings account.");
								}
								if ((hasSav && !noSavBal) || (hasChk && !noChkBal)) {
									System.out.println("Enter \"3\" to withdraw money.");
								}
								System.out.println("Enter \"4\" to deposit money.");
								if ((hasSav && hasChk) && (!noSavBal || !noChkBal)) {
									System.out.println("Enter \"5\" to transfer money between accounts.");
								}
								if ((hasSav && noSavBal) || (hasChk && noChkBal)) {
									System.out.println("Enter \"6\" to delete an account with a zero balance.");
								}
								System.out.println("Enter \"7\" to exit.");
								action2 = scan.nextInt();
							}
							switch (action2) {
							case 1:
								if (hasChk) {
									System.out.println("You can only have one checking account. To "
											+ "create another please talk to a bank manager.");
									break;
								}
								isValid = accServ.createAccount(customer.getUser_id(), 0.00, "Checking");
								if (isValid) {
									System.out.println("New Checking Account created.\n");
								} else {
									System.out.println("There was a problem creating the new account.\n");
								}
								break;
							case 2:
								if (hasSav) {
									System.out.println("You can only have one savings account.");
									break;
								}
								isValid = accServ.createAccount(customer.getUser_id(), 0.00, "Savings");
								if(isValid) {
									System.out.println("New Savings Account created.\n");
								} else {
									System.out.println("There was a problem creating the new savings account.");
								}
								break;
							case 3:
								if ((hasSav && !noSavBal) && (hasChk && !noChkBal)) {
									isValid = false;
									while (!isValid) {
										System.out.println("Which account do you want to withdraw "
												+ "money from? \nEnter \"1\" for Checking. \nEnter \"2\" for Savings.");
										action2 = scan.nextInt();
										if (action2 == 1) {
											action2 = checking;
											isValid = true;
										} else if (action2 == 2) {
											action2 = savings;
											isValid = true;
										} else {
											System.out.println("That is not a valid entry.");
										}
									}
								} else if (hasChk && !noChkBal) {
									action2 = checking;
								} else {
									action2 = savings;
								}
								System.out.println("How much will you be withdrawing today?");
								amount = 0;
								isValid = false;
								while (!isValid) {
									amount = scan.nextDouble();
									acc = accServ.getAccountByAccountID(action2);
									if (amount >= 0.01) {
										double temp2 = acc.getBalance() - amount;
										if (temp2 < -0.001) {
											System.out.println(
													"You don't have that much in your account. \nYou can withdraw up to $"
															+ acc.getBalance() + ".\nPlease enter this amount or less.");
										} else {
											isValid = true;
										}
									} else {
										System.out.println(
												"You can not withdraw a nevgative amount. Please enter a number greater then zero.");
									}
								}
								isValid = accServ.withdraw(amount, action2);
								break;
							case 4:
								if (hasSav && hasChk) {
									isValid = false;
									while (!isValid) {
										System.out.println("Which account do you want to deposit money to? "
												+ "\nEnter \"1\" for Checking. \nEnter \"2\" for Savings.");
										action2 = scan.nextInt();
										if (action2 == 1) {
											action2 = checking;
											isValid = true;
										} else if (action2 == 2) {
											action2 = savings;
											isValid = true;
										} else {
											System.out.println("That is not a valid entry.");
										}
									}
								} else if (hasChk) {
									action2 = checking;
								} else {
									action2 = savings;
								}
								System.out.println("How much will you be depositing today?");
								isValid = false;
								amount = 0;
								while (!isValid) {
									amount = scan.nextDouble();

									if (amount >= 0.01) {
										isValid = true;
									} else {
										System.out.println("You can not deposit a nevgative amount."
												+ " Please enter a number greater then zero.");
									}
								}
								isValid = accServ.deposit(amount, action2);
								break;
							case 5:
								isValid = false;
								while (!isValid) {
									if (!noChkBal && !noSavBal) {
										System.out.println("Enter \"1\" to transfer from Savings to Checking. "
												+ "\nEnter \"2\" to transfer from Checking to Savings.\nEnter \"3\" to"
												+ "cancel transaction. ");
										action2 = scan.nextInt();
									} else if (!noChkBal) {
										action2 = 2;
									} else {
										action2 = 1;
									}
									if (action2 == 1 || action2 == 2) {
										isValid = true;
									} else if (action2 == 3) {
										break;
									} else {
										System.out.println("That is not a valid entry.");
									}
								}
								if (action2 == 3) {
									break;
								}
								isValid = false;
								System.out.println("How much do you want to transfer?");
								while (!isValid) {
									amount = scan.nextDouble();
									if (action2 == 1) {
										acc = accServ.getAccountByAccountID(savings);
										if (amount >= 0.01) {
											double temp2 = acc.getBalance() - amount;
											if (temp2 < -0.001) {
												System.out.println(
														"You don't have that much in your account. \nYou can withdraw up to $"
																+ acc.getBalance() + ".\nPlease enter this amount or less.");
											} else {
												isValid = accServ.withdraw(amount, savings);
												try {
													Thread.sleep(20);
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
												if (isValid) {
													isValid = accServ.deposit(amount, checking);
												}
												if (!isValid) {
													System.err.println("There was a problem with the transaction. Please see a manager.");
												}
											}
										} else {
											System.out.println(
													"You can not withdraw a nevgative amount. Please enter a number greater then zero.");
										}

									} else {
										acc = accServ.getAccountByAccountID(checking);
										if (amount >= 0.01) {
											double temp2 = acc.getBalance() - amount;
											if (temp2 < -0.001) {
												System.out.println(
														"You don't have that much in your account. \nYou can withdraw up to $"
																+ acc.getBalance() + ".\nPlease enter this amount or less.");
											} else {
												isValid = accServ.withdraw(amount, checking);
												try {
													Thread.sleep(20);
												} catch (InterruptedException e) {
													e.printStackTrace();
												}
												if(isValid) {
													isValid = accServ.deposit(amount, savings);
												}
												isValid = true;
											}
										} else {
											System.out.println(
													"You can not withdraw a nevgative amount. Please enter a number greater then zero.");
										}
									}

								}
								break;

							case 6:
								if ((hasSav && noSavBal) && (hasChk && noChkBal)) {
									isValid = false;
									while (!isValid) {
										System.out.println("Which account do you want to delete?\n"
												+ "Enter \"1\" for Checking. \nEnter \"2\" for Savings.");
										action2 = scan.nextInt();
										if (action2 == 1) {
											action2 = checking;
											hasChk = false;
											isValid = true;
										} else if (action2 == 2) {
											action2 = savings;
											hasSav = false;
											isValid = true;
										} else {
											System.out.println("That is not a valid entry.");
										}
									}
								} else if (hasChk && noChkBal) {
									action2 = checking;
									hasChk = false;
								} else {
									action2 = savings;
									hasSav = false;
								}

								isValid = accServ.deleteAccount(action2);
								break;
							case 7:
								System.out.println("Exiting.\n\n");
								break;
							default:
								System.out.println("Not a valid entry. \nPlease choose an option listed.");
								break;
							}
							break;
						case 6:
							isValid = false;
							System.err.println("This is a perminate action. Are you sure you want want to delet the user?");
							System.err.print("Type \"DELETE\" to delete user.");
							test = scan.nextLine();
							if (test.equals("")) {
								test = scan.nextLine();
							}
							if (test.equals("DELETE")) {
								boolean fin = true;
								allAccounts = accServ.viewUserAccounts(customer.getUser_id());
								accIt =allAccounts.iterator();
								while(accIt.hasNext()) {
									acc = accIt.next();
									isValid = accServ.deleteAccount(acc.getAccount_id());
									if(!isValid) {
										fin = false;
									}
								}
								isValid = fin;
							}
							if (!isValid) {
								System.out.println("Delete not performed.");
							} else {
								isValid = userServ.deleteUser(customer.getUser_id());
								System.out.println("User deleted.");
							}
							break;
						case 7:
							break;
						}
						if(isValid) {
							System.out.println("Update applied.");
						} else {
							System.out.println("No updates applied.");
						}						
					} else if (action == 3) {
						trans = tranServ.getAllTransactions();
						traIt = trans.iterator();
						System.out.println("   Date     Account #   User ID    Account   Action   $ amount");
						while(traIt.hasNext()) {
							Transaction t = traIt.next();
							System.out.println(t);
						}						
					} else if (action == 4) {
						isValid = false;
						System.err.println("This is a perminate action. Are you sure you want want to delet all accounts?");
						System.err.println("Type \"DELETE\" to delete all accounts.");
						test = scan.next();
						
						if (test.equals("DELETE")) {
							isValid = accServ.deleteAllAcounts();
						}
						if (!isValid) {
							System.out.println("Delete not performed.");
						} else {
							System.out.println("Deleted was successful.");
						}
						
					} else if (action == 5) {
						isValid = false;
						System.err.println("This is a perminate action. Are you sure you want want to delete all users?");
						System.err.println("Type \"DELETE\" to delete all users. NOTE: All accounts must be closec or have zero balance.");
						
						test = scan.next();
						
						if (test.equals("DELETE")) {
							isValid = accServ.deleteAllAcounts();
						} else {
							isValid = false;
						}
						if (!isValid) {
							System.out.println("Delete not performed.");
						} else {
							isValid = userServ.deleteAllUsers();
							System.out.println("All users deleted.");
						}
						
					} else if (action == 6) {
						System.out.println("Loging out.");
						logedIn = false;
					}
				}
			} else {
				System.out.println("Hello " + logedInUser.getfName() + "" + " " + logedInUser.getlName() + "!");
				while (logedIn) {

					allAccounts = accServ.viewUserAccounts(logedInUser.getUser_id());
					accIt = allAccounts.iterator();

					if (!accIt.hasNext()) {
						System.out.println("You currently have no accounts with the Bank "
								+ "of Revature.\nWhat would you like to do? \nEnter \"1\" "
								+ "to create a new Checking account. \nEnter \"2\" to create"
								+ " a new Savings account. \nEnter \"8\" to log out.\nEnter "
								+ "\"9\" to delete account.");
						isValid = false;
						while (!isValid) {
							action = scan.nextInt();
							if (action == 1 || action == 2 || action == 9 || action == 8) {
								isValid = true;
							} else {
								System.out.println("\"" + action + "\" is an invalid choice. "
										+ "Please enter one of the choises listed.");
							}

						}
					} else {
						System.out.println("Here are your open accounts and balances.");
						hasSav = false;
						hasChk = false;
						while (accIt.hasNext()) {
							temp = accIt.next();
							System.out.println(temp);
							if (temp.getAccount_type().equals("Savings")) {
								hasSav = true;
								savings = temp.getAccount_id();
								if (temp.getBalance() <= 0.00) {
									noSavBal = true;
								} else {
									noSavBal = false;
								}
							} else if (temp.getAccount_type().equals("Checking")) {
								hasChk = true;
								checking = temp.getAccount_id();
								if (temp.getBalance() <= 0.00) {
									noChkBal = true;
								} else {
									noChkBal = false;
								}
							}
						}
						System.out.println("What would you like to do?");
						if (!hasChk) {
							System.out.println("Enter \"1\" to create a Checking account.");
						} else if (!hasSav) {
							System.out.println("Enter \"2\" to create a Savings account.");
						}
						if ((hasSav && !noSavBal) || (hasChk && !noChkBal)) {
							System.out.println("Enter \"3\" to withdraw money.");
						}
						System.out.println("Enter \"4\" to deposit money.");
						if ((hasSav && hasChk) && (!noSavBal || !noChkBal)) {
							System.out.println("Enter \"5\" to transfer money between accounts.");
						}
						System.out.println("Enter \"6\" to view transaction history.");
						if ((hasSav && noSavBal) || (hasChk && noChkBal)) {
							System.out.println("Enter \"7\" to delete an account with a zero balance.");
						}
						System.out.println("Enter \"8\" to log out.");
						if (!hasSav && !hasChk) {
							System.out.println("Enter \"9\" to delete your account.");
						}
						action = scan.nextInt();
					}

					switch (action) {
					case 1:
						if (hasChk) {
							System.out.println("You can only have one checking account.");
							break;
						}
						isValid = accServ.createAccount(logedInUser.getUser_id(), 0.00, "Checking");
						if (isValid) {
							System.out.println("New Checking Account created.\n");
						} else {
							System.out.println("There was a problem creating the new account.\n");
						}
						break;
					case 2:
						if (hasSav) {
							System.out.println("You can only have one savings account.");
							break;
						}
						isValid = accServ.createAccount(logedInUser.getUser_id(), 0.00, "Savings");
						if(isValid) {
							System.out.println("New Savings Account created.\n");
						} else {
							System.out.println("There was a problem creating the new savings account.");
						}
						break;
					case 3:
						if ((hasSav && !noSavBal) && (hasChk && !noChkBal)) {
							isValid = false;
							while (!isValid) {
								System.out.println("Which account do you want to withdraw "
										+ "money from? \nEnter \"1\" for Checking. \nEnter \"2\" for Savings.");
								action = scan.nextInt();
								if (action == 1) {
									action = checking;
									isValid = true;
								} else if (action == 2) {
									action = savings;
									isValid = true;
								} else {
									System.out.println("That is not a valid entry.");
								}
							}
						} else if (hasChk && !noChkBal) {
							action = checking;
						} else {
							action = savings;
						}
						System.out.println("How much will you be withdrawing today?");
						amount = 0;
						isValid = false;
						while (!isValid) {
							amount = scan.nextDouble();
							acc = accServ.getAccountByAccountID(action);
							if (amount >= 0.01) {
								double temp2 = acc.getBalance() - amount;
								if (temp2 < -0.001) {
									System.out.println(
											"You don't have that much in your account. \nYou can withdraw up to $"
													+ acc.getBalance() + ".\nPlease enter this amount or less.");
								} else {
									isValid = true;
								}
							} else {
								System.out.println(
										"You can not withdraw a nevgative amount. Please enter a number greater then zero.");
							}
						}
						isValid = accServ.withdraw(amount, action);
						break;
					case 4:
						if (hasSav && hasChk) {
							isValid = false;
							while (!isValid) {
								System.out.println("Which account do you want to deposit money to? "
										+ "\nEnter \"1\" for Checking. \nEnter \"2\" for Savings.");
								action = scan.nextInt();
								if (action == 1) {
									action = checking;
									isValid = true;
								} else if (action == 2) {
									action = savings;
									isValid = true;
								} else {
									System.out.println("That is not a valid entry.");
								}
							}
						} else if (hasChk) {
							action = checking;
						} else {
							action = savings;
						}
						System.out.println("How much will you be depositing today?");
						isValid = false;
						amount = 0;
						while (!isValid) {
							amount = scan.nextDouble();

							if (amount >= 0.01) {
								isValid = true;
							} else {
								System.out.println("You can not deposit a nevgative amount."
										+ " Please enter a number greater then zero.");
							}
						}
						isValid = accServ.deposit(amount, action);
						break;
					case 5:
						isValid = false;
						while (!isValid) {
							if (!noChkBal && !noSavBal) {
								System.out.println("Enter \"1\" to transfer from Savings to Checking. "
										+ "\nEnter \"2\" to transfer from Checking to Savings.\nEnter \"3\" to"
										+ "cancel transaction. ");
								action = scan.nextInt();
							} else if (!noChkBal) {
								action = 2;
							} else {
								action = 1;
							}
							if (action == 1 || action == 2) {
								isValid = true;
							} else if (action == 3) {
								break;
							} else {
								System.out.println("That is not a valid entry.");
							}
						}
						isValid = false;
						System.out.println("How much do you want to transfer?");
						while (!isValid) {
							amount = scan.nextDouble();
							if (action == 1) {
								acc = accServ.getAccountByAccountID(savings);
								if (amount >= 0.01) {
									double temp2 = acc.getBalance() - amount;
									if (temp2 < -0.001) {
										System.out.println(
												"You don't have that much in your account. \nYou can withdraw up to $"
														+ acc.getBalance() + ".\nPlease enter this amount or less.");
									} else {
										isValid = accServ.withdraw(amount, savings);
										try {
											Thread.sleep(20);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										if (isValid) {
											isValid = accServ.deposit(amount, checking);
										}
										if (!isValid) {
											System.err.println("There was a problem with the transaction. Please see a manager.");
										}
									}
								} else {
									System.out.println(
											"You can not withdraw a nevgative amount. Please enter a number greater then zero.");
								}

							} else {
								acc = accServ.getAccountByAccountID(checking);
								if (amount >= 0.01) {
									double temp2 = acc.getBalance() - amount;
									if (temp2 < -0.001) {
										System.out.println(
												"You don't have that much in your account. \nYou can withdraw up to $"
														+ acc.getBalance() + ".\nPlease enter this amount or less.");
									} else {
										isValid = accServ.withdraw(amount, checking);
										try {
											Thread.sleep(20);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										if(isValid) {
											isValid = accServ.deposit(amount, savings);
										}
										isValid = true;
									}
								} else {
									System.out.println(
											"You can not withdraw a nevgative amount. Please enter a number greater then zero.");
								}
							}

						}
						break;
					case 6:
						if (hasChk && hasSav) {
							System.out.println("Which account would you like to view the transaction history "
									+ "of? \nEnter 1 for Checking or 2 for Savings.");
							action = scan.nextInt();
						} else if (hasChk) {
							action = 1;
						} else if (hasSav) {
							action = 2;
						} else {
							break;
						}
						trans = tranServ.getAllTransactions();
						traIt = trans.iterator();
						System.out.println("   Date     Account #   User ID    Account   Action   $ amount");
						while(traIt.hasNext()) {
							Transaction t = traIt.next();
							if (logedInUser.getUser_id() == t.getUser_id()) {
								if (action == 1 && t.getAccount_type().equals("Checking")) {
									System.out.println(t);
								} else if (action == 2 && t.getAccount_type().equals("Savings")) {
									System.out.println(t);
								}
							}
						}				
						break;
					case 7:
						if ((hasSav && noSavBal) && (hasChk && noChkBal)) {
							isValid = false;
							while (!isValid) {
								System.out.println("Which account do you want to delete?\n"
										+ "Enter \"1\" for Checking. \nEnter \"2\" for Savings.");
								action = scan.nextInt();
								if (action == 1) {
									action = checking;
									hasChk = false;
									isValid = true;
								} else if (action == 2) {
									action = savings;
									hasSav = false;
									isValid = true;
								} else {
									System.out.println("That is not a valid entry.");
								}
							}
						} else if (hasChk && noChkBal) {
							action = checking;
							hasChk = false;
						} else {
							action = savings;
							hasSav = false;
						}

						isValid = accServ.deleteAccount(action);
						break;
					case 8:
						logedInUser = null;
						validUser = false;
						logedIn = false;
						System.out.println("User loged out.\n\n");
						break;
					case 9:
						System.err.println("This is a perminate action! Are you sure you "
								+ "want to delete your user account with us? \nType "
								+ "\"DELETE\" to delete your user account with us.");
						isValid = false;
						test = scan.nextLine();
						if (test.equals("")) {
							test = scan.nextLine();
						}
						if (test.equals("DELETE")) {
							isValid = userServ.deleteUser(logedInUser.getUser_id());
						}
						if (!isValid) {
							System.out.println("Delete not performed.");
						} else {
							System.out.println("Account deleted. \nSorry to see you go.\n\n");
							logedInUser = null;
							validUser = false;
							logedIn = false;
						}
						break;
					default:
						System.out.println("Not a valid entry. \nPlease choose an option listed.");
					}
				}
			}
		}
	}
	
	private static boolean validInput(String input, String inputType) {
		InputValidator check = new InputValidatorImpl();
		allUsers = userServ.viewAllUsers();
		isValid = true;
		it = allUsers.iterator();
		while (it.hasNext()&& inputType.equals("username")) {
			test = it.next().getUsername();
			if (test.equals(input)) {
				isValid = false;
				System.out.println("That username is already in use. \nPlease choose a different username.");
			}
		}
		if (isValid) {
			isValid = false;
			try {
				isValid = check.validateInput(input);
			} catch (UnallowedException e) {
				System.out.println(inputType + " must not contain any spaces. "
						+ "\nPlease choose a different username.");
			} catch (TooShortException e) {
				System.out.println(e.getMessage() + " The " + inputType + " \'" + input + "\'"
						+ " is only " + e.getNameLenght() + " letters long.");
			}
		}
		return isValid;
	}

}
