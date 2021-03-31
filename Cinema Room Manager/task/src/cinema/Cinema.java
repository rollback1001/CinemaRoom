package cinema;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Cinema {
	Scanner scanner = new Scanner( System.in );
	List<Integer> coordinates = new ArrayList<Integer>();

	int numPurchasedTickets = 0;
	int totalNumTickets = 0;
	int currentIncome = 0;
	int totalIncome = 0;
	int frontSeatRows = 4;
	int backSeatRows = 0;
	int frontSeatRowsTicketPrice = 10;
	int backSeatRowsTicketPrice = 8;

	public static void main( String[] args ) {
		Cinema cinema = new Cinema();

		String[][] cinemaSeating = cinema.buildCinema();

		boolean flag = false;

		while ( !flag ) {
			int selectedOption = cinema.displayMenu();

			if ( selectedOption == 0 ) {
				flag = true;
			}
			else if ( selectedOption == 1 ) {
				cinema.displaySeating( cinemaSeating );
			}
			else if ( selectedOption == 2 ) {
				cinema.purchaseTicket( cinemaSeating, cinema.frontSeatRowsTicketPrice, cinema.backSeatRowsTicketPrice );
				flag = false;
			}
			else if ( selectedOption == 3) {
				cinema.displayStatistics();
			}
			else {
				flag = true;
			}
		}
	}

	public int displayMenu() {
		int option = 0;

		System.out.println();
		System.out.println( "1. Show the seats" );
		System.out.println( "2. Buy a ticket" );
		System.out.println( "3. Statistics" );
		System.out.println( "0. Exit" );

		option = scanner.nextInt();
		System.out.println();

		return option;
	}

	public String[][] buildCinema() {
		Scanner scanner = new Scanner( System.in );

		int numRows = 0;
		int numSeatsPerRow = 0;

		String[][] cinemaSeating;

		boolean flag = false;

		while ( !flag ) {

			System.out.println( "Enter the number of rows:" );
			numRows = scanner.nextInt();

			scanner.nextLine();

			System.out.println( "Enter the number of seats in each row:" );
			numSeatsPerRow = scanner.nextInt();

			if ( numRows < 0 || numSeatsPerRow < 0 ) {
				System.out.println( "Negative values not allowed!" );
				flag = false;
			}
			else if ( numRows == 0 || numSeatsPerRow == 0 ) {
				System.out.println( "Zero values not allowed!" );
				flag = false;
			}
			else {
				flag = true;
			}
		}

		cinemaSeating = populateCinema( numRows, numSeatsPerRow );

		totalIncome = (cinemaSeating[0].length * frontSeatRows) * frontSeatRowsTicketPrice;

		if ( (cinemaSeating.length - frontSeatRows) != 0 ) {
			totalIncome = totalIncome + ((cinemaSeating.length - frontSeatRows) * cinemaSeating[0].length) * backSeatRowsTicketPrice;
		}

		totalNumTickets = cinemaSeating.length * cinemaSeating[0].length;

		return cinemaSeating;
	}

	public String[][] populateCinema( int numRows, int numSeatsPerRow ) {
		String[][] cinemaSeating = new String[numRows][numSeatsPerRow];

		for ( int x = 0; x < cinemaSeating.length; x++ ) {
			for ( int y = 0; y < cinemaSeating[0].length; y++ ) {
				cinemaSeating[x][y] = "S";
			}
		}
		return cinemaSeating;
	}

	public void displaySeating( String[][] cinemaSeating ) {
		System.out.println( "Cinema:" );
		System.out.print( " " );

		for ( int i = 1; i <= cinemaSeating[0].length; i++ ) {
			System.out.print( " " + i );
		}

		System.out.println();

		int index = 1;

		for ( int x = 0; x < cinemaSeating.length; x++ ) {
			System.out.print( index + " " );
			for ( int y = 0; y < cinemaSeating[0].length; y++ ) {
				System.out.print( cinemaSeating[x][y] + " " );
			}
			System.out.println();
			index++;
		}
	}

	public void clearCoordinates( List<Integer> coordinates ) {
		coordinates.clear();
	}

	public void getTicketPrice( String[][] cinemaSeating, int xCoordinate, int normalTicketPrice, int backRowTicketPrice ) {
		if ( (cinemaSeating.length * cinemaSeating[0].length) <= 60 || xCoordinate <= 4 ) {
			currentIncome += normalTicketPrice;
			System.out.printf( "Ticket price: $%s", normalTicketPrice );
		}
		else {
			currentIncome += backRowTicketPrice;
			System.out.printf( "Ticket price: $%s", backRowTicketPrice );
		}
	}

	public List<Integer> getCoordinates( String[][] cinemaSeating ) {
		int rowNum = 0;
		int seatNum = 0;

		boolean flag = false;

		while ( !flag ) {
			System.out.println( "Enter a row number:" );
			rowNum = scanner.nextInt();

			scanner.nextLine();

			System.out.println( "Enter a seat number in that row:" );
			seatNum = scanner.nextInt();

			System.out.println();

			flag = validateCoordinates( cinemaSeating, rowNum, seatNum );
		}
		coordinates.add( 0, rowNum );
		coordinates.add( 1, seatNum );

		return coordinates;
	}

	public boolean validateCoordinates( String[][] cinemaSeating, int rowNum, int seatNum ) {
		if ( rowNum > cinemaSeating.length || rowNum <= 0  ) {
			System.out.println( "Wrong input!" );
			System.out.println();
			return false;
		}
		else if ( seatNum > cinemaSeating[0].length || seatNum <= 0) {
			System.out.println( "Wrong input!" );
			System.out.println( "Enter different seat coordinates." );
			System.out.println();
			return false;
		}
		else {
			return true;
		}
	}

	public boolean checkTicketAvailability( String[][] cinemaSeating, String seat ) {
		if ( seat.equals( "B" ) ) {
			System.out.println( "That ticket has already been purchased!" );
			return false;
		} else {
			return true;
		}
	}

	public void purchaseTicket( String[][] cinemaSeating, int normalTicketPrice, int backRowTicketPrice ) {
		coordinates = getCoordinates( cinemaSeating );

		int xCoordinate = 0;
		int yCoordinate = 0;

		boolean flag = false;

		while ( !flag ) {
			xCoordinate = coordinates.get( 0 );
			yCoordinate = coordinates.get( 1 );

			boolean isTicketAvailable = checkTicketAvailability( cinemaSeating, cinemaSeating[xCoordinate - 1][yCoordinate - 1] );

			if ( isTicketAvailable ) {
				getTicketPrice( cinemaSeating, xCoordinate, normalTicketPrice, backRowTicketPrice );
				clearCoordinates( coordinates );
				numPurchasedTickets++;
				flag = true;
			}
			else {
				clearCoordinates( coordinates );
				System.out.println();
				coordinates = getCoordinates( cinemaSeating );
				flag = false;
			}
		}

		System.out.println();
		cinemaSeating[xCoordinate - 1][yCoordinate - 1] = "B";
	}

	public List<Integer>  computeStatistics() {
		List<Integer> stats = new ArrayList<>();

		stats.add(0,  numPurchasedTickets );
		stats.add( 1, currentIncome );
		stats.add( 2, totalIncome );

		return stats;
	}

	public void displayStatistics() {
		List<Integer> stats = computeStatistics();

		double ratioPurchasedToTotalTickets = ((double ) numPurchasedTickets / totalNumTickets) * 100;

		String temp = String.format( "%.2f", ratioPurchasedToTotalTickets );

		System.out.println("Number of purchased tickets: " + stats.get( 0 ));
		System.out.println("Percentage: " + temp + "%");
		System.out.println("Current income: $" + stats.get( 1 ));
		System.out.println("Total income: $" + stats.get( 2 ));

	}
}