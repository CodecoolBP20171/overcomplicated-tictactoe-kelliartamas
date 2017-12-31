package com.codecool.enterprise.overcomplicated.service;

import com.codecool.enterprise.overcomplicated.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TictactoeGame {

	@Autowired
	private Player player;

	private boolean validMove = true;
	private String winner;
	private String gameState;
	private List<Integer> playerMoveList = new ArrayList<>();
	private List<Integer> computerMoveList = new ArrayList<>();
	private List<Integer> availableFields = new ArrayList<>();

	public TictactoeGame() {
		populateAvailableFields();
	}

	public List<Integer> getComputerMoveList() {
		return computerMoveList;
	}

	public void setValidMove(boolean validMove) {
		this.validMove = validMove;
	}

	public List<Integer> getPlayerMoveList() {
		return playerMoveList;
	}

	public String getWinner() {
		return winner;
	}

	public void setWinner(String winner) {
		this.winner = winner;
	}

	public String getGameState() {
		return gameState;
	}

	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public void checkWinConditions() {
		List<Integer> winCase1 = Arrays.asList(0, 1, 2);
		List<Integer> winCase2 = Arrays.asList(3, 4, 5);
		List<Integer> winCase3 = Arrays.asList(6, 7, 8);

		List<Integer> winCase6 = Arrays.asList(0, 3, 6);
		List<Integer> winCase7 = Arrays.asList(1, 4, 7);
		List<Integer> winCase8 = Arrays.asList(2, 5, 8);

		List<Integer> winCase4 = Arrays.asList(0, 4, 8);
		List<Integer> winCase5 = Arrays.asList(2, 4, 6);

		// Player WIN
		if (playerMoveList.containsAll(winCase1) || playerMoveList.containsAll(winCase2) ||
				playerMoveList.containsAll(winCase3) || playerMoveList.containsAll(winCase4) ||
				playerMoveList.containsAll(winCase5) || playerMoveList.containsAll(winCase6) ||
				playerMoveList.containsAll(winCase7) || playerMoveList.containsAll(winCase8)) {
			setUpWinner(player.getUserName());
			// AI WIN
		} else if (computerMoveList.containsAll(winCase1) || computerMoveList.containsAll(winCase2) ||
				computerMoveList.containsAll(winCase3) || computerMoveList.containsAll(winCase4) ||
				computerMoveList.containsAll(winCase5) || computerMoveList.containsAll(winCase6) ||
				computerMoveList.containsAll(winCase7) || computerMoveList.containsAll(winCase8)) {
			setUpWinner("Computer");
			// DRAW
		} else if (computerMoveList.size() + playerMoveList.size() == 9) {
			setUpWinner("Last game was a DRAW!");
			clearLists();
			populateAvailableFields();
		}
	}

	public void setUpWinner(String winner) {
		setWinner(winner);
		clearLists();
		populateAvailableFields();
	}


	public void populateAvailableFields() {
		for (int i = 0; i < 9; i++) {
			availableFields.add(i);
		}
	}

	private void move(int move, List<Integer> listToAddMove) {
		if (checkIfMoveIsValid(move)) {
			listToAddMove.add(move);
			availableFields.remove(availableFields.indexOf(move));
			checkWinConditions();
		} else {
			System.out.println("Not a valid move!");
		}
	}

	public void playerMove(int move) {
		move(move, playerMoveList);
	}

	public void computerMove(int move) {
		move(move, computerMoveList);
	}


	private boolean checkIfMoveIsValid(int move) {
		if (playerMoveList.contains(move) || computerMoveList.contains(move) ||
				playerMoveList.size() + computerMoveList.size() > 9) {
			setValidMove(false);
			return validMove;
		}
		setValidMove(true);
		return validMove;
	}

	private void clearLists() {
		playerMoveList.clear();
		computerMoveList.clear();
		availableFields.clear();
	}

	public void gameState() {
		List<Character> fields = new ArrayList<>(8);
		List<Integer> fieldIndxs = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);

		for (int i = 0; i < 9; i++) {
			fields.add('-');
		}

		for (int num : playerMoveList) {
			int index = fieldIndxs.indexOf(num);
			fields.set(index, 'O');
		}
		for (int num : computerMoveList) {
			int index = fieldIndxs.indexOf(num);
			fields.set(index, 'X');
		}

		StringBuilder stringToSend = new StringBuilder();

		for (Character aList : fields) {
			stringToSend.append(aList);
		}
		setGameState(stringToSend.toString());
	}

}
