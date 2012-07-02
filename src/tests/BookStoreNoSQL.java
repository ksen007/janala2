
package tests;

import database.table.Table;
import database.table.TableFactory;
import database.table.Where;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

public class BookStoreNoSQL {
	//--variables related to control concolic execution--------------------

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
    private Table Customers;
    private Table Orders;
    private Table Publishers;
    private Table Books;
    private int cid;
    private int pwd;
    private String tte;
    private String pn;
    private int min;
    private int max;
    //---------------------------------------------------------------------------

	public static void main(String[] argv){
        try {
		BookStoreNoSQL bookStore = new BookStoreNoSQL();
		bookStore.initialize();
		bookStore.execute();
		bookStore.dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void initialize(){

		screenVisitCountTable = new int[SCREEN_COUNT_MAX];
		screenInputsList = new BookStoreScreenInputs[SCREEN_VISIT_MAX];
		for(int i=0;i<SCREEN_VISIT_MAX;i++){
			screenInputsList[i] = new BookStoreScreenInputs();
		}

		try
		{
            Customers = TableFactory.create("Customers",new String[]{"Id","Name","PasswordHash","Age"});
            Orders = TableFactory.create("Orders", new String[]{"Id","CustomerId","OrderDateTime","CancelDate", "BookId", "IsCanceled"});
            Publishers = TableFactory.create("Publishers",new String[]{"Id","Name"});
            Books = TableFactory.create("Books",new String[]{"Id","ISBN","Title","Price","Year","PublisherId","Stock"});

			// create an example of inputs and initial database state
			createExampleInitialDatabaseState();
			createExampleInitialInputsList();

		}
		catch(Exception e)
		{
			System.err.println(e.getMessage());
		}
	}

	public void  createExampleInitialDatabaseState() throws SQLException {

		//Customers
		Customers.insert(new Object[]{0, "Tanaka", 3, 25});

		//Books
		Books.insert(new Object[]{20, 1234567890, "The Art of C++", 20, 1987, 20, 1});
		Books.insert(new Object[]{21, 1234567891, "The Art of C#", 25, 2000, 21, 1});
		Books.insert(new Object[]{22, 1234567892, "The Art of Lisp", 30, 1980, 20, 1});
		Books.insert(new Object[]{23, 1234567894, "Java HandBook", 5, 1999, 21, 1});

		//Publishers
		Publishers.insert(new Object[]{20, "Pearson Education"});
        Publishers.insert(new Object[]{21, "O Reilly"});

		//Orders
		Orders.insert(new Object[]{20, 1, 20120310, null, 20, 0});

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
            e.printStackTrace();
			System.err.println(e.getMessage());
		}
	}

	public int s01_loginScreen(int customerId, int password) throws SQLException {

		assume(customerId >= 0 && customerId <= 1000);
		assume(password >= 0 && password <= 20);

        this.cid = customerId;
        this.pwd = password;
        
        ResultSet rs = Customers.select(new Where() {
            public boolean isTrue(Map<String, Object>[] rows) {
                Integer i = (Integer)rows[0].get("Id");
                if (i==null || i!=cid) return false;
                i = (Integer)rows[0].get("PasswordHash");
                if (i==null || i!=hash(pwd)) return false;
                return true;
            }
        },new String[][]{{"Id"}},null).getResultSet();

//		ResultSet rs = statement.executeQuery("select Id from Customers where Id="+customerId+" and PasswordHash=" + hash(password));
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
            this.cid = g_customerId;
//			ResultSet rs = statement.executeQuery("select Id from Orders where CustomerId=" + g_customerId);
            ResultSet rs = Orders.select(new Where() {
                public boolean isTrue(Map<String, Object>[] rows) {
                    Integer i = (Integer)rows[0].get("CustomerId");
                    if (i==null || i!=cid) return false;
                    return true;
                }
            },new String[][]{{"Id"}},null).getResultSet();

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

        this.tte = title;
        this.pn = publisherName;
        this.min = minYear;
        this.max = maxYear;
        ResultSet rs = Books.select(new Where() {
            public boolean isTrue(Map<String, Object>[] rows) {
                if (!rows[0].get("PublisherId").equals(rows[1].get("Id"))) return false;
                if (!tte.equals(rows[0].get("Title"))) return false;
                if ((Integer)rows[0].get("Stock")<=0) return false;
                if ((Integer)rows[0].get("Year")<min) return false;
                if ((Integer)rows[0].get("Year")>max) return false;
                if (!pn.equals(rows[1].get("Name"))) return false;
                return true;
            }
        },new String[][]{{"Id","Price"},null},new Table[]{Publishers}).getResultSet();

//		ResultSet rs = statement.executeQuery("select Books.Id, Books.Price from Books inner join Publishers on Books.PublisherId = Publishers.Id " +
//				"where Books.Title= '" + title + "' and Publishers.Name='"+publisherName+"' and Books.Year>=" + minYear +
//				" and Books.Year<=" + maxYear + " and Books.Stock > 0");

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
	}

}
