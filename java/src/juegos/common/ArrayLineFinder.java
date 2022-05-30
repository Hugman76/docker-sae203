package juegos.common;

import java.util.function.Consumer;

public class ArrayLineFinder
{
	private final int emptyValue;
	private final int lineLength;
	private final boolean checkDiagonals;

	public ArrayLineFinder(int emptyValue, int lineLength, boolean checkDiagonals) {
		this.emptyValue = emptyValue;
		this.lineLength = lineLength;
		this.checkDiagonals = checkDiagonals;
	}

	public static void main(String[] args) {
		ArrayLineFinder lineFinder = new ArrayLineFinder(0, 3, true);
		int[][] test1 = {
				{0, 0, 0, 0},
				{0, 0, 0, 1},
				{0, 0, 1, 0},
				{0, 1, 0, 0}
		};
		lineFinder.find(test1, i -> System.out.println("test1:correct"));
		int[][] test2 = {
				{0, 0, 1, 0},
				{0, 1, 0, 0},
				{1, 0, 0, 0},
				{0, 0, 0, 0}
		};
		lineFinder.find(test2, i -> System.out.println("test2:correct"));
		int[][] test3 = {
				{0, 0, 0, 0},
				{1, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0}
		};
		lineFinder.find(test3, i -> System.out.println("test3:correct"));
		int[][] test4 = {
				{0, 0, 0, 0},
				{0, 1, 0, 0},
				{0, 0, 1, 0},
				{0, 0, 0, 1}
		};
		lineFinder.find(test4, i -> System.out.println("test4:correct"));
	}

	public int getEmptyValue() {
		return emptyValue;
	}

	public void find(int[][] array, Consumer<Integer> consumer) {
		int i = find(array);
		if(i != this.emptyValue) {
			consumer.accept(i);
		}
	}

	private int find(int[][] array) {
		int width = array.length;
		int height = array[0].length;

		for(int x = 0; x < width; x++) {
			int i = checkVerticalWin(array, x);
			if(i != this.emptyValue) {
				return i;
			}
		}
		for(int y = 0; y < height; y++) {
			int i = checkHorizontalWin(array, y);
			if(i != this.emptyValue) {
				return i;
			}
		}

		if(this.checkDiagonals) {
			int minY = -(width - 1);
			int maxY = height - 1;
			for(int y = minY; y <= maxY; y++) {
				int i = checkDiagonalWin(array, y, true);
				if(i != emptyValue) {
					return i;
				}
			}
			for(int y = minY; y <= maxY; y++) {
				int i = checkDiagonalWin(array, y, false);
				if(i != emptyValue) {
					return i;
				}
			}
		}
		return this.emptyValue;
	}

	private int checkVerticalWin(int[][] array, int x) {
		int height = array[x].length;

		int consecutive = 1;
		int playerIndex = this.emptyValue;
		for(int y = 0; y < height; y++) {
			int cellValue = array[x][y];
			if(playerIndex != cellValue || cellValue == this.emptyValue) {
				consecutive = 1;
				playerIndex = cellValue;
			}
			else {
				consecutive++;
			}
			if(consecutive >= this.lineLength) {
				return playerIndex;
			}
		}
		return this.emptyValue;
	}

	private int checkHorizontalWin(int[][] array, int y) {
		int consecutive = 1;
		int playerIndex = this.emptyValue;
		for(int[] ints : array) {
			int cellValue = ints[y];
			if(playerIndex != cellValue || cellValue == this.emptyValue) {
				consecutive = 1;
				playerIndex = cellValue;
			}
			else {
				consecutive++;
			}
			if(consecutive >= this.lineLength) {
				return playerIndex;
			}
		}
		return this.emptyValue;
	}

	private int checkDiagonalWin(int[][] array, int startY, boolean increasing) {
		int consecutive = 1;
		int playerIndex = this.emptyValue;
		int y = startY;
		for(int x = 0; x < array.length; x++) {
			if(y < 0) continue;
			if(y >= array[x].length) continue;
			int cellValue = array[x][y];
			if(playerIndex != cellValue || cellValue == this.emptyValue) {
				consecutive = 1;
				playerIndex = cellValue;
			}
			else {
				consecutive++;
			}
			if(consecutive >= this.lineLength) {
				return playerIndex;
			}
			y += increasing ? 1 : -1;
		}
		return this.emptyValue;
	}

}
