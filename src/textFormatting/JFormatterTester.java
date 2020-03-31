package textFormatting;

import static org.junit.Assert.*;

import org.junit.Ignore;
import org.junit.Test;

import textFormatting.blockObj.Columns;

import java.util.List;
import java.util.ArrayList;

public class JFormatterTester {
	
	@Test
	public void lineMergeOne() {
		String line0 = "-d";
		String line1 = "In every age mankind attempts to fabricate great works";
		String line2 = "at once magnificent and impossible. From gothic walls and mountains of stone a pyramid.";
		String line3 = "With flying butresses they make a wall of light.";
		String line4 = "-d";
		String line5 = "A chapel streaming screaming one mans ecstasy.";
		
		List<String> list = new ArrayList<String>();
		
		list.add(line0);
		list.add(line1);
		list.add(line2);
		list.add(line3);
		list.add(line4);
		list.add(line5);
		
		JFormatter formatter = new JFormatter("");
		
		/*for(int i = 0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}*/
		
		List<String> mergedLines = formatter.lineMerge(list);
		
		System.out.println("\nlineMergeOne Test:");
		System.out.println("Line0: "+mergedLines.get(0));
		System.out.println("Line1: "+mergedLines.get(1));
		System.out.println("Line2: "+mergedLines.get(2));
		System.out.println("Line3: "+mergedLines.get(3));
		
		assertTrue(mergedLines.get(0).equals(line0));
		assertTrue(mergedLines.get(1).equals(line1 + " " + line2 + " " + line3));
		assertTrue(mergedLines.get(2).equals(line4));
		assertTrue(mergedLines.get(3).equals(line5));
	}
	
	@Test
	public void lineMergeTwo() {
		String line0 = "In every age mankind attempts to fabricate great works";
		String line1 = "-e";
		String line2 = "at once magnificent and impossible. From gothic walls and mountains of stone a pyramid.";
		String line3 = "With flying butresses they make a wall of light.";
		String line4 = "-d";
		String line5 = "A chapel streaming screaming one mans ecstasy.";
		String line6 = "-c";
		
		List<String> list = new ArrayList<String>();
		
		list.add(line0);
		list.add(line1);
		list.add(line2);
		list.add(line3);
		list.add(line4);
		list.add(line5);
		list.add(line6);
		
		JFormatter formatter = new JFormatter("");
		
		/*for(int i = 0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}*/
		
		List<String> mergedLines = formatter.lineMerge(list);
		
		System.out.println("\nlineMergeTwo Test:");
		System.out.println("Line0: "+mergedLines.get(0));
		System.out.println("Line1: "+mergedLines.get(1));
		System.out.println("Line2: "+mergedLines.get(2));
		System.out.println("Line3: "+mergedLines.get(3));
		System.out.println("Line4: "+mergedLines.get(4));
		System.out.println("Line5: "+mergedLines.get(5));
		
		assertTrue(mergedLines.get(0).equals(line0));
		assertTrue(mergedLines.get(1).equals(line1));
		assertTrue(mergedLines.get(2).equals(line2 + " " + line3));
		assertTrue(mergedLines.get(3).equals(line4));
		assertTrue(mergedLines.get(4).equals(line5));
		assertTrue(mergedLines.get(5).equals(line6));
	}
	
	@Test
	public void mergeNewLineTest() {
		String line0 = "In every age mankind attempts to fabricate great works";
		String line1 = "at once magnificent and impossible. From gothic walls and mountains of stone a pyramid.";
		String line2 = "With flying butresses they make a wall of light.";
		String line3 = "A chapel streaming screaming one mans ecstasy.";
		
		List<String> list = new ArrayList<String>();
		
		list.add(line0);
		list.add(line1);
		list.add(line2);
		list.add(line3);
		
		JFormatter formatter = new JFormatter("");
		
		/*for(int i = 0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}*/
		
		String mergedLines = formatter.mergeNewLine(list);
		
		System.out.println("\nmergeNewLine Test:");
		System.out.println("Merged Lines: " + mergedLines);
		
		assertTrue(mergedLines.equals(line0 + "\n" + line1 + "\n" + line2 + "\n" + line3));
	}
	
	@Test
	public void processNewLineTestOne() {
		String line0 = "In every age mankind attempts to fabricate great works";
		String line1 = "-e";
		String line2 = "at once magnificent and impossible.";
		String line3 = "-r";
		String line4 = "-e";
		
		Columns column = Columns.ONE;
		//int testNum = 1;
		
		//while(testNum < 3) {
			List<String> correctList = new ArrayList<String>();
			
			correctList.add(line0);
			correctList.add("");
			correctList.add(line2);
			correctList.add(line3);
			correctList.add("");
			
			List<String> list = new ArrayList<String>();
			
			list.add(line0);
			list.add(line1);
			list.add(line2);
			list.add(line3);
			list.add(line4);
			
			blockObj theBlock = new blockObj(column);
			theBlock.lineList = list;
			
			System.out.println("\nprocessNewLine Test One:");
			
			System.out.println("\nBefore:");
			for(int i = 0; i < theBlock.lineList.size(); i++) {
				System.out.println(theBlock.lineList.get(i));
			}
			
			theBlock.processNewlines();
			
			System.out.println("\nAfter:");
			for(int i = 0; i < theBlock.lineList.size(); i++) {
				System.out.println(theBlock.lineList.get(i));
				assertTrue(theBlock.lineList.get(i).equals(correctList.get(i)));
			}
			//column = Columns.TWO;
			//testNum++;
		//}
	}
	
	@Test
	public void processNewLineTestTwo() {
		String line0 = "-e";
		String line1 = "In every age mankind attempts to fabricate great works";
		String line2 = "at once magnificent and impossible.";
		String line3 = "-e";
		String line4 = "-r";
		
		Columns column = Columns.ONE;
		//int testNum = 1;
		
		//while(testNum < 3) {
			List<String> correctList = new ArrayList<String>();
			
			correctList.add("");
			correctList.add(line1);
			correctList.add(line2);
			correctList.add("");
			correctList.add(line4);
			
			List<String> list = new ArrayList<String>();
			
			list.add(line0);
			list.add(line1);
			list.add(line2);
			list.add(line3);
			list.add(line4);
			
			blockObj theBlock = new blockObj(column);
			theBlock.lineList = list;
			
			System.out.println("\nprocessNewLine Test Two:");
			
			System.out.println("\nBefore:");
			for(int i = 0; i < theBlock.lineList.size(); i++) {
				System.out.println(theBlock.lineList.get(i));
			}
			
			theBlock.processNewlines();
			
			System.out.println("\nAfter:");
			for(int i = 0; i < theBlock.lineList.size(); i++) {
				System.out.println(theBlock.lineList.get(i));
				assertTrue(theBlock.lineList.get(i).equals(correctList.get(i)));
			}
			//column = Columns.TWO;
			//testNum++;
		//}
	}
	
	@Test
	public void readIntoColumnTest() {
		String line0 = "In every age mankind attempts to fabricate great works";
		String line1 = "at once magnificent and impossible. From gothic walls and mountains of stone a pyramid.";
		String line2 = "With flying butresses they make a wall of light.";
		String line3 = "A chapel streaming screaming one mans ecstasy.";
		String line4 = "-2";
		String line5 = "One mans ecstasy...";
		String line6 = "Miracles them all, china's endless wall.";
		String line7 = "-1";
		String line8 = "Stonehenge the Pantheonm the Duomo";
		String line9 = "-2";
		String line10 = "The aqueducts of Rome.";
		
		List<blockObj> correctList = new ArrayList<blockObj>();
		blockObj currBlock = new blockObj(Columns.ONE);
		currBlock.lineList.add(line0);
		currBlock.lineList.add(line1);
		currBlock.lineList.add(line2);
		currBlock.lineList.add(line3);
		correctList.add(currBlock);
		
		currBlock = new blockObj(Columns.TWO);
		currBlock.lineList.add(line5);
		currBlock.lineList.add(line6);
		correctList.add(currBlock);
		
		currBlock = new blockObj(Columns.ONE);
		currBlock.lineList.add(line8);
		correctList.add(currBlock);
		
		currBlock = new blockObj(Columns.TWO);
		currBlock.lineList.add(line10);
		correctList.add(currBlock);
		
		List<String> list = new ArrayList<String>();
		
		list.add(line0);
		list.add(line1);
		list.add(line2);
		list.add(line3);
		list.add(line4);
		list.add(line5);
		list.add(line6);
		list.add(line7);
		list.add(line8);
		list.add(line9);
		list.add(line10);
		
		JFormatter formatter = new JFormatter("");
		
		/*for(int i = 0; i<list.size(); i++) {
			System.out.println(list.get(i));
		}*/
		
		List<blockObj> columnReadList = formatter.readIntoColumns(list);
		
		System.out.println("readIntoColumnTest:");
		for(int i = 0; i<columnReadList.size(); i++) {
			System.out.println("Box"+ i +"("+columnReadList.get(i).columns+"):");
			for(int j = 0; j<columnReadList.get(i).lineList.size(); j++) {
				System.out.println(columnReadList.get(i).lineList.get(j));
				assertTrue(columnReadList.get(i).lineList.get(j).equals(correctList.get(i).lineList.get(j)));
			}
		}
		assertTrue(columnReadList.get(3).lineList.get(0).equals(correctList.get(3).lineList.get(0)));
		
		//assertTrue(mergedLines.equals(line0 + "\n" + line1 + "\n" + line2 + "\n" + line3));
	}

	@Test
	public void invalidFlagErrorTest() {
		String input = "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-e\n"
				+ "With flying butresses they make a wall of light.\n"
				+ "-x\n"
				+ "A chapel streaming screaming on mans ecstasy.\n"
				+ "-r";
		
		String correctErrorLog = "Line 5: Unknown Command '-x'";
		
		JFormatter formatter = new JFormatter(input);
		
		String errorLog = formatter.errors();
		System.out.println("invalidFlagErrorTestOne:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
		
		input = "-p\n"
				+ "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-e\n"
				+ "With flying butresses they make a wall of light.\n"
				+ "-x\n"
				+ "A chapel streaming screaming on mans ecstasy.\n"
				+ "-z";
		
		correctErrorLog = "Line 0: Unknown Command '-p'\n"
				+ "Line 6: Unknown Command '-x'\n"
				+ "Line 8: Unknown Command '-z'";
		
		formatter = new JFormatter(input);
		
		errorLog = formatter.errors();
		System.out.println("invalidFlagErrorTestTwo:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
		
		input = "-d\n"
				+ "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-e Hello There\n"
				+ "With flying butresses they make a wall of light.\n"
				+ "-s\n"
				+ "A chapel streaming screaming on mans ecstasy.\n"
				+ "-i -d";
		
		correctErrorLog = "Line 4: Unknown Command '-e Hello There'\n"
				+ "Line 8: Unknown Command '-i -d'";
		
		formatter = new JFormatter(input);
		
		errorLog = formatter.errors();
		System.out.println("\ninvalidFlagErrorTestThree:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
	}
	
	@Test
	public void inlineFlagErrorTest() {
		String input = "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From -e gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-d\n"
				+ "With flying butresses they -x make a wall of light.\n"
				+ "-l\n"
				+ "A chapel streaming screaming on mans ecstasy.\n"
				+ "-r";
		
		String correctErrorLog = "Line 1: Invalid flag placement\n"
				+ "Line 4: Invalid flag placement";
		
		JFormatter formatter = new JFormatter(input);
		
		String errorLog = formatter.errors();
		System.out.println("\ninlineFlagErrorTestOne:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
	}
	
	@Test
	public void indentTwoColumnErrorTest() {
		String input = "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-2\n"
				+ "-i\n"
				+ "With flying butresses they make a wall of light.";
		
		String correctErrorLog = "Line 4: Attempt to set indentation while setting justification or 2-column mode";
		
		JFormatter formatter = new JFormatter(input);
		
		String errorLog = formatter.errors();
		System.out.println("\nindentTwoColumnErrorTestOne:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
		
		input = "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-2\n"
				+ "With flying butresses they make a wall of light.\n"
				+ "-i\n"
				+ "A chapel streaming screaming on mans ecstasy.";
		
		correctErrorLog = "Line 5: Attempt to set indentation while setting justification or 2-column mode";
		
		formatter = new JFormatter(input);
		
		errorLog = formatter.errors();
		System.out.println("\nindentTwoColumnErrorTest:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
	}
	
	@Test
	public void indentJustificationErrorTestOne() {
		String input = "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-r\n"
				+ "-b\n"
				+ "With flying butresses they make a wall of light.\n"
				+ "A chapel streaming screaming on mans ecstasy.\n"
				+ "-t\n"
				+ "-c\n";
		
		String correctErrorLog = "Line 4: Attempt to set indentation while setting justification or 2-column mode";
		
		JFormatter formatter = new JFormatter(input);
		
		String errorLog = formatter.errors();
		System.out.println("\nindentJustificationErrorTestOne:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
	}
	
	@Test
	public void indentJustificationErrorTestTwo() {
		String input = "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-r\n"
				+ "With flying butresses they make a wall of light.\n"
				+ "-i\n"
				+ "A chapel streaming screaming on mans ecstasy.\n"
				+ "-c";
		
		String correctErrorLog = "Line 5: Attempt to set indentation while setting justification or 2-column mode";
		
		JFormatter formatter = new JFormatter(input);
		
		String errorLog = formatter.errors();
		System.out.println("\nindentJustificationErrorTestTwo:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
	}
	
	@Test
	public void indentJustificationErrorTestThree() {
		String input = "In every age mankind attempts to fabricate great works\n"
				+ "at once magnificent and impossible. From gothic walls and mountains of stone\n"
				+ "a pyramid.\n"
				+ "-l\n"
				+ "With flying butresses they make a wall of light.\n"
				+ "-i\n"
				+ "A chapel streaming screaming on mans ecstasy.\n"
				+ "-c";
		
		String correctErrorLog = "";
		
		JFormatter formatter = new JFormatter(input);
		
		String errorLog = formatter.errors();
		System.out.println("\nindentJustificationErrorTestThree:");
		System.out.println(errorLog);
		System.out.println("\n");
		assertTrue(errorLog.equals(correctErrorLog));
	}
	
	@Test
	public void columnMergeTest() {
		String line0 = "In every age mankind";
		String line1 = "attempts to fabricate great works";
		String line2 = "at once magnificent";
		String line3 = "and impossible. From";
		String line4 = "gothic walls and";
		String line5 = "mountains of stone.";
		
		Columns column = Columns.TWO;
		List<String> correctList = new ArrayList<String>();
			
		correctList.add(line0 + "                         " + line3 + "               ");
		correctList.add(line1 + "            " + line4 + "                   ");
		correctList.add(line2 + "                          " + line5 + "                ");
			
		List<String> list = new ArrayList<String>();
			
		list.add(line0);
		list.add(line1);
		list.add(line2);
		list.add(line3);
		list.add(line4);
		list.add(line5);
			
		blockObj theBlock = new blockObj(column);
		theBlock.lineList = list;
			
		System.out.println("\ncolumnMergeTest One:");
			
		System.out.println("\nBefore:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
		}
			
		theBlock.processColumnMerge();
			
		System.out.println("\nAfter:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
			assertTrue(theBlock.lineList.get(i).equals(correctList.get(i)));
		}
	}
	
	@Test
	public void processColumnsAndIndentationsTestOne() {
		String line0 = "-b";
		String line1 = "In every age mankind attempts to fabricate great works "
				+ "at once magnificent and impossible. "
				+ "From gothic walls and mountains of stone"
				+ " a pyramid. With flying butresses they make a wall of light. "
				+ "A chapel streaming screaming one man's ecstasy! One man's ecstasy...";
		String line2 = "-n";
		String line3 = "Miracles them all, China's endless wall! Stonehenge the pantheon "
				+ "the duomo. The aqueducts of Rome. We did not attempt to make with mammoth blocks of stone "
				+ "a giant pyramid, no not a pyramid. Or gothic walls that radiate with light.";
		String line4 = "-i";
		String line5 = "-r";
		String line6 = "Our task was to dream upon and then create a floating city. Floating city. "
				+ "A human metropolis, a complete civilization, sleek and fast, at once a poem, and the "
				+ "perfection of physical engineering";
		
		Columns column = Columns.ONE;
		List<String> correctList = new ArrayList<String>();
			
		correctList.add("          "+"In every age mankind attempts to fabricate great works at once");
		correctList.add("          "+"magnificent and impossible. From gothic walls and mountains of stone a");
		correctList.add("          "+"pyramid. With flying butresses they make a wall of light. A chapel");
		correctList.add("          "+"streaming screaming one man's ecstasy! One man's ecstasy... Miracles");
		correctList.add("them all, China's endless wall! Stonehenge the pantheon the duomo. The aqueducts");
		correctList.add("of Rome. We did not attempt to make with mammoth blocks of stone a giant");
		correctList.add("pyramid, no not a pyramid. Or gothic walls that radiate with light.");
		correctList.add("-r");
		correctList.add("     "+"Our task was to dream upon and then create a floating city. Floating city.");
		correctList.add("A human metropolis, a complete civilization, sleek and fast, at once a poem, and");
		correctList.add("the perfection of physical engineering");
			
		List<String> list = new ArrayList<String>();
			
		list.add(line0);
		list.add(line1);
		list.add(line2);
		list.add(line3);
		list.add(line4);
		list.add(line5);
		list.add(line6);
			
		blockObj theBlock = new blockObj(column);
		theBlock.lineList = list;
			
		System.out.println("\nprocessColumnsAndIndentationsTest One:");
			
		System.out.println("\nBefore:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
		}
			
		theBlock.processColumnsAndIndentation();
			
		System.out.println("\nAfter:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
			assertTrue(theBlock.lineList.get(i).equals(correctList.get(i)));
		}
		
	}
	
	@Test
	public void processColumnsAndIndentationsTestTwo() {
		String line0 = "In every age mankind attempts to fabricate great works "
				+ "at once magnificent and impossible. "
				+ "From gothic walls and mountains of stone"
				+ " a pyramid. With flying butresses they make a wall of light. "
				+ "A chapel streaming screaming one man's ecstasy! One man's ecstasy...";
		String line1 = "-n";
		String line2 = "-r";
		
		Columns column = Columns.TWO;
		List<String> correctList = new ArrayList<String>();
			
		correctList.add("In every age mankind attempts to");
		correctList.add("fabricate great works at once");
		correctList.add("magnificent and impossible. From");
		correctList.add("gothic walls and mountains of stone");
		correctList.add("a pyramid. With flying butresses");
		correctList.add("they make a wall of light. A chapel");
		correctList.add("streaming screaming one man's");
		correctList.add("ecstasy! One man's ecstasy...");
		correctList.add("-r");
			
		List<String> list = new ArrayList<String>();
			
		list.add(line0);
		list.add(line1);
		list.add(line2);
			
		blockObj theBlock = new blockObj(column);
		theBlock.lineList = list;
			
		System.out.println("\nprocessColumnsAndIndentationsTest Two:");
			
		System.out.println("\nBefore:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
		}
			
		theBlock.processColumnsAndIndentation();
			
		System.out.println("\nAfter:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
			assertTrue(theBlock.lineList.get(i).equals(correctList.get(i)));
		}
		
	}

	@Test
	public void formatterTest() {
		String line0 = "Hello there my name is billybob joe and I am here to "
				+ "purchase some goods at this fine establishment and also I "
				+ "am hungry and wish to purchase stuff\n";
		String line1 = "-d\n";
		String line2 = "Hello hello there" + 
				"I am hungry" + 
				"and oh no oh god" + 
				"LELEL AKDA KSDADASDA SADADADA DADASD SSSDF FFFFFFFF FFFFFFFFFFF FFFF IIIOOP PPPLE\n";
		String line3 = "-s\n";
		String line4 = "-r\n";
		String line5 = "Did you know the airspeed velocity of an african swallow is around "
				+ "ten times the velocity of a french sparrow and also I am hungry\n";
		String line6 = "-l\n";
		String line7 = "Oh no oh dear we must go to eat food now" + 
				"and merge these lines AAAAAAHA HHA HAHHAHAHHAHH AHAHHAHA\n";
		String line8 = "-i\n";
		String line9 = "INDENT THIS LINE INDENT DARN YOU DOOOA SDADO ADODOOODDOA "
				+ "AAAAAAAAA AAAAAAAAAA AAAAAAAAAAAAAAA AAAA AAAAAAAAAAAD" + 
				"LK" + 
				"LKLKL AAAA AAAAAAa\n";
		String line10 = "-b\n";
		String line11 = "BLOCK BLOCK BLOCK BLOCK BLOCK BLOCK "
				+ "BLOCK BLOCK BLOCK BLOCK BLOCK BLOCK BLOCK "
				+ "BLOCK BLOCKB LOCKBLO CKBLOCKBLO CKBLO CKBLO"
				+ " CKBLOCK BLO  CKBLO  CKBLOCKB LOCKBLO CKBLO "
				+ "CKBLOCKBLO KBLOCK BLOCKBLO CKBLOC KBLOCKB LOCKBLO"
				+ " CKBLOCKBLO CKBLOCKBL OC KBLOCK BLOC KBLO CKB LOCK"
				+ " BLO CKBLOC KBLOC KBLO C KBLOCK BLOCKB LOCKBL OCKB "
				+ "LOCKBLOC KBLO KBLO KBLOCKBLOC KBLOC KBLO CKBLO CKBLOCK"
				+ " BLOC KBLOC KBLOCK\n";
		String line12 = "-n\n";
		String line13 = "NO MORE BLOCK it is done we are all done with block block sucks anyways and also\n";
		String line14 = "-t\n";
		String line15 = "THIS IS THE TITLE\n";
		String line16 = "-r\n"; //Change to -c eventually
		String line17 = "I HATE CENTERING TEXT OH MY GOD OH NO NONONO NO\n";
		String line18 = "-l\n";
		String line19 = "-e\n";
		String line20 = "BLANK ABOVE AND BELOW\n";
		String line21 = "-e\n";
		String line22 = "OH NO COLUMNS ARE COMING\n";
		String line23 = "-2\n";
		String line24 = "AAAAAAA AAAAAAA AAAAAAAAAAA AAAAAA AAAAAAAAAAA AAAAAAAAAA "
				+ "AAAAAAAAAAA AAA AAAAAAAAAAAAAA AAAAA AAAAA  AAAAAAAAAAAAAAA AAAAAAA "
				+ "AAAA AAA AAAAAAA AA AAAAAAAAAAAAA AAAAAAAAAAAA AAAA AAAAAAAAA AAAAAAAAAAAAAAA"
				+ " AA AAAAAAAAAAAAA AAAAAAA AAAAAAAAA AAAAAA AAAAAAAAAAAAAA AAAAAAAAAAAAA AAA"
				+ "  AAAAAAAAAAAAA AAAAAAAA AAAAAAAAAAAAAAA AAAAAAAAA AAAAAAA AAAAAAAAAAA AAAAA "
				+ "AAAAAAA\n";
		String line25 = "-r\n";
		String line26 = "RRRRR RRRRRRRRRR RRRRRRRRR RRRRRRRRRRRR RRRRRRRRR RRRRRRRRRRR RRRRRRRRRRRR"
				+ " RRRRRRRRRR RRRRRRRRRR RRRRRRRRR RRRRRRRRRRRRR RRRRRRR RRRRRRR RRRRRRRRRRR RRRRRRR "
				+ "RRRRRRRRRRR RRRRRRRR RRRRRRRRRRRRR RRRRRRRRRRRR RRRRRRRRR\n";
		String line27 = "-1\n";
		String line28 = "OH NO OH NO OH NONONONONO OH NO OH NO OH NONONO OH NO\n";
		
		
		String fullFile = line0+line1+line2+line3+line4+line5+line6+line7+line8+line9+line10+line11+line12+line13+line14+line15+line16+line17+line18+line19+line20+line21+line22+line23+line24+line25+line26+line27+line28;
		JFormatter formatter = new JFormatter(fullFile);
		String lastAn = formatter.format();
		System.out.println(lastAn);
		
		
		
	}
	
	@Test
	public void spacingTest() {
		String line0 = "-d";
		String line1 = "     In every age mankind attempts to fabricate great works";
		String line2 = "at once magnificent and impossible.";
		String line3 = "     From gothic walls and mountains of stone";
		String line4 = "-s";
		String line5 = "a pyramid. With flying butresses they make";
		String line6 = "-r";
		String line7 = "a wall of light.";
		
		Columns column = Columns.ONE;
		List<String> correctList = new ArrayList<String>();
			
		correctList.add(line1);
		correctList.add("");
		correctList.add(line2);
		correctList.add("");
		correctList.add(line3);
		correctList.add("");
		correctList.add(line5);
		correctList.add(line6);
		correctList.add(line7);
			
		List<String> list = new ArrayList<String>();
			
		list.add(line0);
		list.add(line1);
		list.add(line2);
		list.add(line3);
		list.add(line4);
		list.add(line5);
		list.add(line6);
		list.add(line7);
			
		blockObj theBlock = new blockObj(column);
		theBlock.lineList = list;
			
		System.out.println("\nspacingTest One:");
			
		System.out.println("\nBefore:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
		}
			
		theBlock.processSpacing();
			
		System.out.println("\nAfter:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
			assertTrue(theBlock.lineList.get(i).equals(correctList.get(i)));
		}
		
	}
	
	@Test
	public void processJustificationTestOne() {
		String line0 = "-r";
		String line1 = "In every age mankind attempts to fabricate great works";
		String line2 = "at once magnificent and impossible.";
		String line3 = "From gothic walls and mountains of stone";
		String line4 = "-l";
		String line5 = "a pyramid. With flying butresses they make";
		String line6 = "-t";
		String line7 = "a wall of.";
		
		Columns column = Columns.ONE;
		List<String> correctList = new ArrayList<String>();
			
		correctList.add("                          "+ line1);
		correctList.add("                                             "+line2);
		correctList.add("                                        "+line3);
		correctList.add(line5+"                                      ");
		correctList.add("                                   "+line7+"                                   ");
			
		List<String> list = new ArrayList<String>();
			
		list.add(line0);
		list.add(line1);
		list.add(line2);
		list.add(line3);
		list.add(line4);
		list.add(line5);
		list.add(line6);
		list.add(line7);
			
		blockObj theBlock = new blockObj(column);
		theBlock.lineList = list;
			
		System.out.println("\nprocessJustificationTest One:");
			
		System.out.println("\nBefore:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
		}
			
		theBlock.processJustification();
			
		System.out.println("\nAfter:");
		for(int i = 0; i < theBlock.lineList.size(); i++) {
			System.out.println(theBlock.lineList.get(i));
			assertTrue(theBlock.lineList.get(i).equals(correctList.get(i)));
		}
		
	}
	
	@Ignore("Not complete")
	@Test
	public void completeTestOne() {
		String line0 = "In every age mankind attempts to fabricate great works\n";
		String line1 = "-e\n";
		String line2 = "At once magnificent, and impossible.\n";
		String line3 = "-r\n";
		String line4 = "From gothic walls and mountains of stone a pyramid.\n";
		String line5 = "-l\n";
		String line6 = "-b\n";
		String line7 = "With flying buttresses they make a wall of light.\n";
		String line8 = "A castle streaming screaming one man's ecstasy!\n";
		String line9 = "One man's ecstasy...\n";
		String line10 = "-n\n";
		String line11 = "-2\n";
		String line12 = "Miracles them all!\n";
		String line13 = "China's endless wall!\n";
		String line14 = "-t\n";
		String line15 = "Stonehenge the pantheon, the duomo\n";
		String line16 = "The aqueducts of rome\n";
		String line17 = "-2\n";
		String line18 = "d\n";
		String line19 = "We did not attempt to make with mammoth blocks of stone a giant pyramid.\n";
		String line20 = "No not a pyramid\n";
		String line21 = "-s\n";
		String line22 = "-i\n";
		String line23 = "Or gothic walls that radiate with light!\n";
		String line24 = "Our task was to dream upon and then create a floating city";
		
		String total = line0+line1+line2+line3+line4+line5+line6+line7+line8+line9+line10+line11+line12+
				line13+line14+line15+line16+line17+line18+line19+line20+line21+line22+line23+line24;
		
		
	}
}
