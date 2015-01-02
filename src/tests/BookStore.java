/*
 * Copyright (c) 2012, NTT Multimedia Communications Laboratories, Inc. and Koushik Sen
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package tests;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class BookStore {

	//--variables related to control concolic execution--------------------
	private Connection connection = null;
	private Statement statement = null;

	private static final int SCREEN_VISIT_MAX = 1;
	private static final int SCREEN_COUNT_MAX = 25;

	private static final int S01_LOGIN_SCREEN = 1;
	private static final int S02_LOGIN_ERROR_SCREEN = 2;
	private static final int S03_MENU_SCREEN = 3;
	private static final int S04_SEARCH_BOOKS_SCREEN = 4;
	private static final int S05_NO_BOOK_SCREEN = 5;
	private static final int S06_SELECT_BOOKS_SCREEN = 6;
	private static final int S07_NO_SELECTION_SCREEN = 7;
	private static final int S08_TOO_EXPENSIVE_SCREEN = 8;
	private static final int S09_FINAL_CHECK_TO_ORDER_SCREEN = 9;
	private static final int S10_THANK_YOU_ORDER_SCREEN = 10;
	private static final int S11_DUPLICATE_ORDER_SCREEN = 11;
	private static final int S20_NO_ORDER_SCREEN = 20;
	private static final int S21_SELECT_ORDERS_SCREEN = 21;
	private static final int S22_NO_SELECTED_ORDER_SCREEN = 22;
	private static final int S23_FINAL_CHECK_TO_CANCEL_SCREEN = 23;
	private static final int S24_FINISHED_CANCEL_SCREEN = 24;

	private int[] screenVisitCountTable;
	//---------------------------------------------------------------------------

	BookStoreScreenInputs screenInputs;

	//--input variables of concolic execution--------------------------------
	//screen inputs
	private BookStoreScreenInputs[] screenInputsList;
	//global inputs
	private int g_customerId;
	//inner variables
	private ArrayList<Tuple2<Integer,Integer>> selectedBookIdPriceList;
	private ArrayList<Tuple2<Integer,Integer>> orderedBookIdPriceList;
	private ArrayList<Integer> selectedOrderIdList;
	//---------------------------------------------------------------------------

	public static void main(String[] argv){
		BookStore bookStore = new BookStore();
		bookStore.initialize();
		bookStore.execute();
		bookStore.dispose();
	}

	public void initialize(){

		screenVisitCountTable = new int[SCREEN_COUNT_MAX];
		screenInputsList = new BookStoreScreenInputs[SCREEN_VISIT_MAX];
		for(int i=0;i<SCREEN_VISIT_MAX;i++){
			screenInputsList[i] = new BookStoreScreenInputs();
		}

		try
		{
			String databaseName = "sample.db";
			Class.forName("org.sqlite.JDBC");

			// delete the existing database
			File file = new File(databaseName);
			if(file.exists()){
				file.delete();
			}

			// create a database connection and statement
			connection = DriverManager.getConnection("jdbc:sqlite:"+databaseName);
			statement = connection.createStatement();

			// create a database tables
			statement.executeUpdate("create table Customers (Id integer, Name string, PasswordHash integer, Age integer)");
			statement.executeUpdate("create table Orders (Id integer, CustomerId integer,OrderDateTime integer,CancelDate integer, BookId integer, IsCanceled integer)");
			statement.executeUpdate("create table Publishers (Id integer, Name string)");
			statement.executeUpdate("create table Books (Id integer, ISBN integer, Title string,Price integer, Year integer, PublisherId integer, Stock integer)");

			// create an example of inputs and initial database state
			createExampleInitialDatabaseState();
			createExampleInitialInputsList();

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}

	public void  createExampleInitialDatabaseState() throws SQLException{

		//Customers
		statement.executeUpdate("insert into Customers values(0, 'Tanaka', 3,25)");

		//Books
		statement.executeUpdate("insert into Books values(20, 1234567890, 'The Art of C++', 20, 1987, 20, 1)");
		statement.executeUpdate("insert into Books values(21, 1234567891, 'The Art of C#', 25, 2000, 21, 1)");
		statement.executeUpdate("insert into Books values(22, 1234567892, 'The Art of Lisp', 30, 1980, 20, 1)");
		statement.executeUpdate("insert into Books values(23, 1234567894, 'Java HandBook', 5, 1999, 21, 1)");

		//Publishers
		statement.executeUpdate("insert into Publishers values(20, 'Pearson Education')");
		statement.executeUpdate("insert into Publishers values(21, 'O Reilly')");

		//Orders
		statement.executeUpdate("insert into Orders values(20, 1, 20120310, NULL, 20, 0)");

	}

	public void createExampleInitialInputsList() {
		BookStoreScreenInputs is = screenInputsList[0];
		is.customerId = 0;
		is.password = 1;
		is.title = "The Art of C#";
		is.publisherName = "O Reilly";
		is.minYear = 2000;
		is.maxYear = 2010;
	}

	public void execute(){

		try{
			int state = S01_LOGIN_SCREEN;

			while(true){

				// check screen visit count
				int  screenVisitCount = screenVisitCountTable[state];
				if(screenVisitCount >= SCREEN_VISIT_MAX){
					System.err.println("Exceed the max count of screen visit");
					return;
				}
				BookStoreScreenInputs is = screenInputsList[screenVisitCount];

				// execution the business logic related to state (screen)
				switch (state){
				case S01_LOGIN_SCREEN: state = s01_loginScreen(is.customerId, is.password); break;
				case S02_LOGIN_ERROR_SCREEN: state = s02_loginErrorScreen();
				case S03_MENU_SCREEN: state = s03_menuScreen(is.whereGoto); break;
				case S04_SEARCH_BOOKS_SCREEN: state = s04_searchBooksScreen(is.title, is.publisherName, is.minYear, is.maxYear); break;
				case S05_NO_BOOK_SCREEN: state = s05_noBookScreen(); break;
				case S06_SELECT_BOOKS_SCREEN:
					System.out.println("Reach S06_SELECT_BOOKS_SCREEN...");
					System.out.println("Screen S07 to S11 and S20 to S24 are not implemented.");
					return;
					//state = s06_selectBooksScreen(is.selectedBooksIndexes); break;
				case -1: System.err.println("Invalid state"); return;
				}
			}
		}
		catch(Exception e){
			System.err.println(e.getMessage());
		}
	}

	public int s01_loginScreen(int customerId, int password) throws SQLException {

		assume(customerId >= 0 && customerId <= 1000);
		assume(password >= 0 && password <= 20);

		ResultSet rs = statement.executeQuery("select Id from Customers where Id="+customerId+" and PasswordHash=" + hash(password));
		ArrayList<Integer> customerIdList  = new ArrayList<Integer>();
		while(rs.next())
		{
			customerIdList.add(rs.getInt("Id"));
		}
		if(customerIdList.size() > 0){
			g_customerId = customerId;
			return S03_MENU_SCREEN;
		}
		else{
			return S02_LOGIN_ERROR_SCREEN;
		}
	}

	private int s02_loginErrorScreen(){
		return S01_LOGIN_SCREEN;
	}

	private int s03_menuScreen(int whereGoto)  throws SQLException {

		assume(whereGoto == BookStoreScreenInputs.GOTO_ORDER ||  whereGoto == BookStoreScreenInputs.GOTO_CANCEL);

		if(whereGoto == BookStoreScreenInputs.GOTO_ORDER){
			return S04_SEARCH_BOOKS_SCREEN;
		}
		else if(whereGoto == BookStoreScreenInputs.GOTO_CANCEL){
			ResultSet rs = statement.executeQuery("select Id from Orders where CustomerId=" + g_customerId);
			ArrayList<Integer> orderIdList  = new ArrayList<Integer>();
			while(rs.next())
			{
				orderIdList.add(rs.getInt("Id"));
			}
			if(orderIdList.size() > 0){
				return S21_SELECT_ORDERS_SCREEN;
			}
			else{
				return S20_NO_ORDER_SCREEN;
			}
		}
		else{
			return -1;
		}
	}

	private int s04_searchBooksScreen(String title, String publisherName,
		int minYear, int maxYear) throws SQLException{

		assume(title.length() >= 0 && title.length() <= 20);
		assume(publisherName.length() >= 0 && publisherName.length() <= 20);
		assume(minYear >= 1950 && minYear <= 2050);
		assume(maxYear >= 1950 && maxYear <= 2050);

		ResultSet rs = statement.executeQuery("select Books.Id, Books.Price " +
                "from Books inner join Publishers on Books.PublisherId = Publishers.Id " +
				"where Books.Title= '" + title + "' and Publishers.Name='"+publisherName+"' and Books.Year>=" + minYear +
				" and Books.Year<=" + maxYear + " and Books.Stock > 0");

		selectedBookIdPriceList = new ArrayList<Tuple2<Integer,Integer>>();
		while(rs.next())
		{
			selectedBookIdPriceList.add(new Tuple2<Integer, Integer>(rs.getInt("Id"), rs.getInt("Price")));
		}

		if(selectedBookIdPriceList.size() > 0){
			return S06_SELECT_BOOKS_SCREEN;
		}
		else{
			return S05_NO_BOOK_SCREEN;
		}
	}

	private int s05_noBookScreen() {
		return S04_SEARCH_BOOKS_SCREEN;
	}

	private int s06_selectBooksScreen(int[] selectedBooksIndexes) {
		// todo
		return 0;
	}

	private void assume(boolean b) {
		if (!b) System.err.println("Assumption is incorrect");
	}

	private int hash(int password) {
		return password % 5 + 3;
	}

	public void dispose(){
		try
		{
			if(connection != null)
				connection.close();
		}
		catch(SQLException e)
		{
			// connection close failed.
			System.err.println(e);
		}
	}
}
