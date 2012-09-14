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
