package textFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import textFormatting.blockObj.*;

public class JFormatter{
	// The text to be formatted
	String inputText;

	/**
	 * Constructor for JFormatter
	 * @param text
	 */
	public JFormatter(String text) {
		this.inputText = text;
	}

	/**
	 * 
	 * @return any errors found in the file and returns them as a String
	 */
	// Searches for 3 kinds of errors:
	// 1. Invalid flags
	// 2. Flags that are not on their own line, at the beginning of the line
	// 3. Any attempt to set indentation and justification
	// 4. Any attempt to set indentation and two 2 column mode
	public String errors() {
		// Get a place to store errors
		List<String> errors = new ArrayList<String>();
		// For every line in the input
		List<String> lines = intoLines(inputText);
		// Track whether non-default indentation, justification, or column count is set
		boolean indented = false;
		boolean justified = false;
		boolean twocolumn = false;
		// For every line
		for (int linenumber = 0; linenumber < lines.size(); linenumber++) {
			// Get the current line. Important since the List implementation
			// may not have efficient lookup to do the work only once
			String line = lines.get(linenumber);
			// If there is a flag in this line
			if (line.contains("-")) {
				// If the line is in this format:
				// Start of the string, hyphen, characters, end of the string
				if (line.matches("^-.+$")) {
					// A regex matching strings that begin with a hyphen and then a valid flag value, with nothing following
					String validFlagLine = "^-(r|c|l|t|1|2|b|i|n|e|d|s)$";
					// If this line only contains a valid flag
					if (line.matches(validFlagLine)) {
						// If it is a justification, indentation, or column flag,
						// Set the values as necessary
						switch (line) {
							case "-i":
							case "-b":
								indented = true;
								break;
							case "-n":
								indented = false;
								break;
							case "-r":
							case "-c":
							case "-t":
								justified = true;
								// Justification turns off indentation
								indented = false;
								break;
							case "-l":
								justified = false;
								break;
							case "-2":
								twocolumn = true;
								break;
							case "-1":
								twocolumn = false;
								break;
						}
						// If there is an error
						if (indented && (justified || twocolumn)) {
							// Undo it (since the command isn't really valid)
							switch (line) {
								case "-i":
								case "-b":
									indented = false;
									break;
								case "-r":
								case "-c":
								case "-t":
									indented = true;
									justified = false;
									break;
								case "-2":
									twocolumn = false;
									break;
							}
							// Error indentation and justification at the same time
							String error = String.format("Line %d: Attempt to set indentation while setting justification or 2-column mode", linenumber);
							errors.add(error);
						}
					}
					else {
						// Error, invalid flag
						String error = String.format("Line %d: Unknown Command '-%s'", linenumber, line.substring(1));
						errors.add(error);
					}
				}
				else {
					// Error, invalid flag placement
					String error = String.format("Line %d: Invalid flag placement", linenumber);
					errors.add(error);
				}
			}
		}
		if(errors.size() == 0) {
			return "";
		} else {
			return mergeNewLine(errors);
		}
	}

	// Completely formats the text that was used to construct this JFormatter
	public String format() {
		// Turn the text into lines
		List<String> lines = intoLines(inputText);
		// Turn the lines into columns
		List<blockObj> blocks = readIntoColumns(lines);
		// Process the blocks
		List<String> output = process(blocks);
		// Merge the lines into a '\n' separated string
		return mergeNewLine(output);
	}

	// Processes the blockObj list that represents the input text and returns the output
	public List<String> process(List<blockObj> blocks) {
		// A place to hold the lines
		List<String> lines = new ArrayList<String>();
		// For every block
		for (blockObj block : blocks) {
			// Process the block in order using its methods
			block.processColumnsAndIndentation();
			block.processNewlines();
			block.processSpacing();
			block.processJustification();
			block.processColumnMerge();
			// Add the block's output to the list of lines
			lines.addAll(block.lineList);
		}
		return lines;
	}

	// Splits a String apart on any newline character
	public List<String> intoLines(String string) {
		return new ArrayList<>(Arrays.asList(string.split("\\R")));
	}

	
	/**
	 * 
	 * @param input
	 * @return
	 */
	// Merges all adjacent text lines into one line while leaving flags alone
	public List<String> lineMerge(List <String> myList)    {
	    	List<String> lineMerged = new ArrayList <String> ();
	    	for(int i = 0; i < myList.size(); i++) {
	    		//if flag add flag and new blank entry
	    		if(myList.get(i).charAt(0) == '-') {
	    			lineMerged.add(myList.get(i));
	    			if(i != myList.size()-1)
	    			lineMerged.add("");
	    		}
	    		//not a flag
	    		else {
	    			//not the first string
		    		if (i >= 1) {
		    			lineMerged.set(lineMerged.size()-1, lineMerged.get(lineMerged.size()-1).concat(myList.get(i)+" "));
		    		}
		    		//first string
		    		else {
		    			lineMerged.add(myList.get(i) + " ");
		    		}
	    		}
	    	}
	    	for(int i = 0; i < lineMerged.size(); i++) {
	    		if(lineMerged.get(i).charAt(0) != '-') {
	    			lineMerged.set(i, lineMerged.get(i).substring(0, lineMerged.get(i).length()-1));
	    		}		
	    	}
	    	return lineMerged;
	    }

	// Turns a list of Strings into a list of blockObj with awareness to how
	// many columns a particular block should have
	public List<blockObj> readIntoColumns (List<String> lines) {
		List<blockObj> blocks = new ArrayList<blockObj>();
		blockObj currBlock = new blockObj(Columns.ONE);
		for(int i = 0; i < lines.size(); i++) {
			switch (lines.get(i)) {
			case "-1":
				blocks.add(currBlock);
				currBlock = new blockObj(Columns.ONE);
				break;
			case "-2":
				blocks.add(currBlock);
				currBlock = new blockObj(Columns.TWO);
				break;
			default:
				currBlock.lineList.add(lines.get(i));
				break;
			}
		}
		blocks.add(currBlock);
		return blocks;
	}

	// Merges a list of Strings into a single String
	public String mergeNewLine(List<String> list) {
		// Merge the lines into one String
		String joined = String.join("\n", list);
		// If the String is null (there were 0 lines)
		if (joined == null) {
			// Replace it with an empty String
			joined = "";
		}
		return joined;
	}
}
