package tests;

public class BookStoreScreenInputs {

		//(1)LOGIN_SCREEN
		public int customerId;
		public int password;

		//(2)LOGIN_ERROR_SCREEN

	    //(3)MENU_SCREEN
		public static final int GOTO_ORDER = 0;
		public static final int GOTO_CANCEL = 1;
		public int whereGoto;

	    //(4)SEARCH_BOOKS_SCREEN
		public boolean isBackToMenu;
		public String title;
		public String publisherName;
		public int maxYear;
		public int minYear;

	    //(5)NO_BOOK_SCREEN

	    //(6)SELECT_BOOKS_SCREEN
		public int[] selectedBooksIndexes;

		//(7)NO_SELECTION_SCREEN

	    //(8)TOO_EXPENSIVE_SCREEN
		public boolean isTooExpensiveOk;

		//(9)FINAL_CHECK_TO_ORDER_SCREEN
		public boolean isOderderOK;

		//(10)THANK_YOU_ORDER_SCREEN

		//(11)DUPLICATE_ORDER_SCREEN
		public boolean isDuplicateOrderOK;

		//(20)NO_ORDER_SCREEN

	    //(21)SELECT_ORDERS_SCREEN
		public int[] selectedOrdersIndexes;

		//(22)NO_SELECTED_ORDER_SCREEN;

		//(23)FINAL_CHECK_TO_CANCEL_SCREEN;
		public boolean isCancelOk;

		//(24)FINISHED_CANCEL_SCREEN;
}
