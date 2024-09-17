// Mark Andrew Tamisen
// September 16, 2024

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class BlackJack {

    // Replacing Card array with ArrayList for dynamic handling of cards
    private static ArrayList<Card> cards = new ArrayList<Card>();
    private static int currentCardIndex = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean turn = true;
        String playerDecision = "";

        while (turn) {
            initializeDeck();    // Initialize the deck of cards
            shuffleDeck();       // Shuffle the deck for randomness

            int playerTotal = dealInitialPlayerCards();
            int dealerTotal = dealInitialDealerCards();

            // Player's turn
            playerTotal = playerTurn(scanner, playerTotal);
            if (playerTotal > 21) {
                System.out.println("You busted! Dealer wins.");
                return;
            }

            // Dealer's turn
            dealerTotal = dealerTurn(dealerTotal);

            // Determine the winner
            determineWinner(playerTotal, dealerTotal);

            // Ask if the player wants to play again
            System.out.println("Would you like to play another hand? (yes/no)");
            playerDecision = scanner.nextLine().toLowerCase();

            // Validate input
            while (!(playerDecision.equals("no") || playerDecision.equals("yes"))) {
                System.out.println("Invalid action. Please type 'yes' or 'no'.");
                playerDecision = scanner.nextLine().toLowerCase();
            }

            // Exit loop if player chooses "no"
            if (playerDecision.equals("no"))
                turn = false;
        }
        System.out.println("Thanks for playing!");
        scanner.close();
    }

    // Initialize deck of cards using ArrayList
    private static void initializeDeck() {
        String[] SUITS = { "Hearts", "Diamonds", "Clubs", "Spades" };
        String[] RANKS = { "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace" };
        cards.clear();  // Clear the deck if replaying the game
        for (int suitIndex = 0; suitIndex < 4; suitIndex++) {
            for (int rankIndex = 0; rankIndex < 13; rankIndex++) {
                int val = (rankIndex < 9) ? Integer.parseInt(RANKS[rankIndex]) : 10;
                if (RANKS[rankIndex].equals("Ace")) val = 11;  // Ace is worth 11
                cards.add(new Card(val, SUITS[suitIndex], RANKS[rankIndex]));
            }
        }
    }

    // Shuffle the deck using ArrayList
    private static void shuffleDeck() {
        Random random = new Random();
        for (int i = 0; i < cards.size(); i++) {
            int index = random.nextInt(cards.size());
            Card temp = cards.get(i);
            cards.set(i, cards.get(index));
            cards.set(index, temp);
        }
        currentCardIndex = 0; // Reset card index
    }

    // Deal initial player cards and return the total value
    private static int dealInitialPlayerCards() {
        Card card1 = dealCard();
        Card card2 = dealCard();
        System.out.println("Your cards: " + card1 + " and " + card2);
        return card1.getValue() + card2.getValue();
    }

    // Deal initial dealer card and return the total value
    private static int dealInitialDealerCards() {
        Card card1 = dealCard();
        System.out.println("Dealer's card: " + card1);
        return card1.getValue();
    }

    // Player's turn to hit or stand
    private static int playerTurn(Scanner scanner, int playerTotal) {
        while (true) {
            System.out.println("Your total is " + playerTotal + ". Do you want to hit or stand?");
            String action = scanner.nextLine().toLowerCase();
            if (action.equals("hit")) {
                Card newCard = dealCard();
                playerTotal += newCard.getValue();
                System.out.println("You drew a " + newCard);
                if (playerTotal > 21) {
                    System.out.println("You busted! Dealer wins.");
                    return playerTotal;
                }
            } else if (action.equals("stand")) {
                break;
            } else {
                System.out.println("Invalid action. Please type 'hit' or 'stand'.");
            }
        }
        return playerTotal;
    }

    // Dealer's turn to draw cards
    private static int dealerTurn(int dealerTotal) {
        while (dealerTotal < 17) {
            Card newCard = dealCard();
            dealerTotal += newCard.getValue();
        }
        System.out.println("Dealer's total is " + dealerTotal);
        return dealerTotal;
    }

    // Determine the winner based on player and dealer totals
    private static void determineWinner(int playerTotal, int dealerTotal) {
        if (dealerTotal > 21 || playerTotal > dealerTotal) {
            System.out.println("You win!");
        } else if (dealerTotal == playerTotal) {
            System.out.println("It's a tie!");
        } else {
            System.out.println("Dealer wins!");
        }
    }

    // Deal a card from the deck
    private static Card dealCard() {
        return cards.get(currentCardIndex++);
    }
}
