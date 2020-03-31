package textFormatting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class blockObj {
	// The only possible numbers of columns are 1 and 1
	public static enum Columns {
		ONE, TWO
	}

	// Represents how many columns a block has
	Columns columns;
	// The lines a block holds
	public List<String> lineList;

	// Create a new blockObj with space to hold lines and the inputted number of columns
	public blockObj(Columns columns) {
		this.lineList = new ArrayList<String>();
		this.columns = columns;
	}

	// Turns any -e flags into a newline in the text
	public void processNewlines() {
		// For every line
		for (int i = 0; i < lineList.size(); i++) {
			// If the line starts with "-e"
			if (lineList.get(i).charAt(0) == '-' && lineList.get(i).charAt(1) == 'e') {
				// Make it blank
				lineList.set(i,  "");
			}
		}
	}

	// Consumes input tags, using the length of the columns (a constant)
	// Ex. A single column goes to 80 characters, so an indent would make the format
	// 5 spaces, 75 char
	// while block would make it
	// 10 spaces, 70 char
	// The column number is obtained from this.columns
	public void processColumnsAndIndentation() {
		String indentWhat = "-n";
		String stringBuilder = "";
		String validFlagLine = "^-(r|c|l|t|b|i|n|e|d|s)$";
		ArrayList<String> listOfStrings = new ArrayList<String>();
		int desiredLength = 0;

		// Takes the line list of words and splits into list of words
		for (int i = 0; i < lineList.size(); i++) {

			String subList[] = (lineList.get(i).split(" "));
			for (String word : subList) {
				listOfStrings.add(word);
			}
		}

		// Resets lineList after words have been added to listOfStrings
		lineList = new ArrayList<String>();

		if (columns == Columns.TWO) {
			desiredLength = 35;

			// TODO: Here you check if the line is a command or not. If not a command, you
			// must split up the strings and create new
			// spaces in the list to distribute them based on line length! Since it is a two
			// column block, the max char length
			// would be 35

			// Goes through each element in list of Strings
			for (String word : listOfStrings) {

				// Checks word for a validFlag bit
				if (word.matches(validFlagLine)) {

					// We don't want -n anymore
					if (!word.matches("^-(n|b|i)$")) {

						// If stringBuilder empty, add to list
						if (stringBuilder != "") {
							lineList.add(stringBuilder);
							stringBuilder = "";
						}

						// Adds back the flags that are supposed to be there
						lineList.add(word);
					}
				} else {
					// If the String is greater or equal to the desired length, it stores the line
					// back into lineList
					if (stringBuilder.length() + word.length() >= desiredLength) {

						// Adds word back into lineList
						lineList.add(stringBuilder);

						// Resets the StringBuilder with word in builder
						stringBuilder = word;
					} else {
						// If the StringBuilder is empty, it appends the word to String builder
						if (stringBuilder == "") {
							stringBuilder = word;

							// If not, it adds a space, then the word next in order
						} else {
							stringBuilder += " " + word;
						}
					}
				}
			}

			if (stringBuilder.length() != 0) {
				lineList.add(stringBuilder);
				stringBuilder = "";
			}

		} else {
			desiredLength = 80;
			// Here you check if the line is a command or not. If not a command, you
			// must split up the strings and create new
			// spaces in the list to distribute them based on line length! Since it is a one
			// column block, the max char length
			// would be 80. If it IS a command, check if it is an indentation one, and
			// adjust the strings accordingly.
			// Remember that indentations add 5 spaces while blocks add 10 to the beginning
			// of each new line!

			// Goes through Each element in listOfStrings
			for (String word : listOfStrings) {

				// Checks to see if the word is a flag
				if (word.matches(validFlagLine)) {

					// Switch Statement for cases
					switch (word) {

					// if -b
					case "-b":
						indentWhat = "-b";
						break;
					case "-i":

						if (stringBuilder.length() != 0) {
							lineList.add(stringBuilder);
							stringBuilder = "";
						}
						indentWhat = "-i";
						break;
					case "-n":
						indentWhat = "-n";
						break;
					default:
						if (stringBuilder.length() != 0) {
							lineList.add(stringBuilder);
							stringBuilder = "";
						}
						lineList.add(word);
						break;
					}
				} else {
					if (stringBuilder.length() + word.length() >= desiredLength) {

						// Adds the long text
						lineList.add(stringBuilder);
						if (indentWhat == "-b") {
							stringBuilder = String.join("", Collections.nCopies(10, " ")) + word;
						} else if (indentWhat == "-i") {
							stringBuilder = String.join("", Collections.nCopies(5, " ")) + word;
							indentWhat = "-n";
						} else {
							stringBuilder = word;
						}

					} else {
						if (stringBuilder == "") {
							if (indentWhat == "-b") {
								stringBuilder = String.join("", Collections.nCopies(10, " ")) + word;
							} else if (indentWhat == "-i") {
								stringBuilder = String.join("", Collections.nCopies(5, " ")) + word;
								indentWhat = "-n";
							} else {
								stringBuilder = word;
							}
						} else {
							stringBuilder += " " + word;
						}
					}
				}

			}

			if (stringBuilder.length() != 0) {
				lineList.add(stringBuilder);
			}
		}
	}

	// Add double spacing where necessary
	public void processSpacing() {
		// The starting spacing is single
		String spaceWhat = "-s";
		// For each line
		for (int i = 0; i < lineList.size(); i++) {
			switch (lineList.get(i)) {
				case "-s":
					spaceWhat = "-s";
					lineList.remove(i);
					i--;
					break;
				case "-d":
					spaceWhat = "-d";
					lineList.remove(i);
					i--;
					break;

				default:

					if (spaceWhat == "-d") {
						// The length of list will change and the next element in the list will be empty
						i++;
						lineList.add(i, "");

					}
			}

		}
	}

	// Justify as necessary
	public void processJustification() {

		// Gets the length of the line that is needed based on the column
		int desiredLength = (this.columns == Columns.ONE) ? 80 : 35;
		String justWhat = "-l";
		int lineLength = 0;

		for (int i = 0; i < lineList.size(); i++) {
			lineLength = lineList.get(i).length();

			// Checks the first part of the line for a -
			// if (lineList.get(i).charAt(0) == '-' && lineList.get(i).length() == 2) {
			switch (lineList.get(i)) {
			case "-t":
				justWhat = "-t";
				lineList.remove(i);
				i--;
				break;
			case "-c":
				justWhat = "-c";
				lineList.remove(i);
				i--;
				break;
			case "-r":
				justWhat = "-r";
				lineList.remove(i);
				i--;
				break;

			case "-l":
				justWhat = "-l";
				lineList.remove(i);
				i--;
				break;

			/*
			 * No Justification with these blocks enabled except for left Just case "-b":
			 * justWhat = "-b"; lineList.remove(i); i--; break; case "-i": justWhat = "-i";
			 * lineList.remove(i); i--; break;
			 */
			default:

				int spaceNeeded = desiredLength - lineLength;
				if (justWhat == "-r") {

					// Sets the line at index i, to the spaces needed plus the original line
					lineList.set(i,

							String.join("", Collections.nCopies(spaceNeeded, " ")) + lineList.get(i));

				} else if (justWhat == "-t") {

					// Adds the spaceNeeded/2 + spaceNeeded%2 to the left and spaceNeeded/2 to the
					// end
					lineList.set(i, String.join("", Collections.nCopies(spaceNeeded / 2 + spaceNeeded % 2, " "))
							+ lineList.get(i) + String.join("", Collections.nCopies(spaceNeeded / 2, " ")));
				} else if (justWhat == "-l") {
					
					lineList.set(i, lineList.get(i) + "" + String.join("", Collections.nCopies(spaceNeeded, " ")) + "");
				} else if (justWhat == "-c") {
					// Finds the amount of spaces in-between words (wordCount - 1)
					int spacesInBetweenWords = lineList.get(i).length() - lineList.get(i).replace(" ", "").length();

					// Finds the amount of characters that aren't spaces in the text what is needed
					// to reach desiredLength
					spaceNeeded = desiredLength - lineList.get(i).replace(" ", "").length();

					// Replaces the spaces already needed in the
					if ((spaceNeeded / spacesInBetweenWords) != 0) {
						lineList.set(i, lineList.get(i).replaceAll(" ",
								String.join("", Collections.nCopies(spaceNeeded / spacesInBetweenWords, " "))));
					}

					spaceNeeded = spaceNeeded % spacesInBetweenWords;
					String stringBuilder = "";
					
					for (int j = 0; j < lineList.get(i).length(); j++) {
						if (lineList.get(i).charAt(j) == ' ') {
							if (lineList.get(i).charAt(j + 1) != ' ' && spaceNeeded > 0) {
								stringBuilder += " ";
								spaceNeeded--;
							}

						}
						stringBuilder += lineList.get(i).charAt(j);

					}

					lineList.set(i, stringBuilder);

				}
			}
		}
	}

	// Merges this.lineList into two columns,
	// splitting in half and putting the first half (rounded up) on the left
	// and the second half (rounded down) on the right, adding spaces where
	// necessary
	public void processColumnMerge() {
		// Only do this if there are two columns
		if (this.columns == Columns.TWO) {
			// Get the length of the list
			int length = lineList.size();
			// Get the length of half of the list, plus any remainder (either 0 or 1, in
			// this case)
			int first = (length / 2) + (length % 2);
			// Get the length of the reset of the list
			int second = length - first;
			// Split the list into two halves, making a list out of each half
			List<String> firstHalf = new ArrayList<>(lineList.subList(0, first));
			List<String> secondHalf = new ArrayList<>(lineList.subList(first, length));
			// If there is excess in the first half
			if (first > second) {
				// Insert a blank line into the second half
				secondHalf.add("");
				// Increment the length to reflect this
				second++;
			}
			// Create a list to store joined lines in
			List<String> joined = new ArrayList<String>();
			// For every line in the lists
			for (int index = 0; index < first && index < second; index++) {
				// Get the next string from the first half and right-pad with spaces to 35
				// characters
				String front = String.format("%-35s", firstHalf.get(index));
				// Get the next string from the second half and right-pad with spaces to 35
				// characters
				String back = String.format("%-35s", secondHalf.get(index));
				// Combine the first column and second column with 10 spaces in-between
				joined.add(front + (String.join("", Collections.nCopies(10, " "))) + back);
			}
			this.lineList = joined;
		}
	}
}
