package com.codecool.termlib;
import java.util.*;
import java.lang.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;


public class Terminal {
    /**
     * The beginning of control sequences.
     */
    // HINT: In \033 the '0' means it's an octal number. And 33 in octal equals 0x1B in hexadecimal.
    // Now you have some info to decode that page where the control codes are explained ;)
	
	private static final String HEADER = "\033[1;1H\33[37m"
	+"				888b    888 d8b       888888                                 8888888b. 8888888 Y88b   d88P 8888888888 888             d8888 8888888b. 88888888888\n"
	+"				8888b   888 Y8P          88b                                 888   Y88b  888    Y88b d88P  888        888            d88888 888   Y88b    888     \n"
	+"				88888b  888              888                                 888    888  888     Y88o88P   888        888           d88P888 888    888    888     \n"
	+"				888Y88b 888 888 88888b.  888  8888b.  888  888  8888b.       888   d88P  888      Y888P    8888888    888          d88P 888 888   d88P    888     \n"
	+"				888 Y88b888 888 888  88b 888      88b 888  888      88b      8888888P    888      d888b    888        888         d88P  888 8888888P      888     \n"
	+"				888  Y88888 888 888  888 888 .d888888 Y88  88P .d888888      888         888     d88888b   888        888        d88P   888 888 T88b      888     \n"
	+"				888   Y8888 888 888  888 88P 888  888  Y8bd8P  888  888      888         888    d88P Y88b  888        888       d8888888888 888  T88b     888     \n"
	+"				888    Y888 888 888  888 888  Y888888   Y88P    Y888888      888       8888888 d88P   Y88b 8888888888 88888888 d88P     888 888   T88b    888     \n"
	+"						       .d88P                                                                                                                      \n"
	+"						     .d88P                                                                                                                       \n"
	+"						    888P						type: save || load || clear	Move: a w s d   Point: q   Pencil: l   Undo: n							\n"
	+"		   _____________________________________________________________________________________________________________________________________________________________________";
	
	private static int vertical = 30;
	private static int horizontal = 100;
	//private static String cordinate = Integer.toString(vertical) +";"+ Integer.toString(horizontal);
	
	private static final String BLOCK = "\u2588";
	
	
    private static final String CONTROL_CODE = "\033[";
    /**
     * Command for whole screen clearing.
     *
     * Might be partitioned if needed.
     */
    private static final String CLEAR = "2J";
    /**
     * Command for moving the cursor.
     */
    private static final String MOVE = "H";
    /**
     * Command for printing style settings.
     *
     * Handles foreground color, background color, and any other
     * styles, for example color brightness, or underlines.
     */
    private static final String STYLE = "m";

    /**
     * Reset printing rules in effect to terminal defaults.
     *
     * Reset the color, background color, and any other style
     * (i.e.: underlined, dim, bright) to the terminal defaults.
     */
	 
	
	private static String color = CONTROL_CODE + "37" +STYLE;
	
	private static String pencil = BLOCK;
	
	
	private static String outPut = CONTROL_CODE+CLEAR + HEADER;
    
	public void resetStyle() {
    }

    /**
     * Clear the whole screen.
     *
     * Might reset cursor position.
     */
    public void clearScreen() {
		outPut = CONTROL_CODE+CLEAR + HEADER;
		vertical = 30;
		horizontal = 100;
		command(CONTROL_CODE+CLEAR+HEADER+CONTROL_CODE+Integer.toString(vertical) +";"+ Integer.toString(horizontal)+MOVE);
    }

    /**
     * Move cursor to the given position.
     *
     * Positions are counted from one.  Cursor position 1,1 is at
     * the top left corner of the screen.
     *
     * @param x Column number.
     * @param y Row number.
     */
    public void moveTo(Integer x, Integer y) {
    }

    /**
     * Set the foreground printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The color to set.
     */
    public void setColor(Color color) {
		
		switch (color) {
            case RED:
                Terminal.color = CONTROL_CODE + "31" +STYLE;
                break;
                    
            case WHITE:
                Terminal.color = CONTROL_CODE + "37" +STYLE;
                break;
                         
            case GREEN:
                Terminal.color = CONTROL_CODE + "32" +STYLE;
                break;
                    
            case YELLOW:
                Terminal.color = CONTROL_CODE + "33" +STYLE;
                break;
			
			case BLUE:
                Terminal.color = CONTROL_CODE + "34" +STYLE;
                break;
                    
            case MAGENTA:
                Terminal.color = CONTROL_CODE + "35" +STYLE;
                break;
			case CYAN:
                Terminal.color = CONTROL_CODE + "36" +STYLE;
                break;
                    
            case BLACK:
                Terminal.color = CONTROL_CODE + "30" +STYLE;
                break;
        }
    }

    /**
     * Set the background printing color.
     *
     * Already printed text is not affected.
     *
     * @param color The background color to set.
     */
    public void setBgColor(Color color) {
    }

    /**
     * Make printed text underlined.
     *
     * On some terminals this might produce slanted text instead of
     * underlined.  Cannot be turned off without turning off colors as
     * well.
     */
    public void setUnderline() {
    }

    /**
     * Move the cursor relatively.
     *
     * Move the cursor amount from its current position in the given
     * direction.
     *
     * @param direction Step the cursor in this direction.
     * @param amount Step the cursor this many times.
     */
    public void moveCursor(Direction direction, Integer amount) {
		
		switch (direction) {
            case UP:
				if (vertical >= 14){
					Terminal.vertical -= amount;
					break;
				} else {
					vertical = 12;
				}
            case DOWN:
                Terminal.vertical += amount;
                break;
                         
            case BACKWARD:
                Terminal.horizontal -= amount;
                break;
                        
            case FORWARD:
                Terminal.horizontal += amount;
                break;
        }
		
    }

    /**
     * Set the character diplayed under the current cursor position.
     *
     * The actual cursor position after calling this method is the
     * same as beforehand.  This method is useful for drawing shapes
     * (for example frame borders) with cursor movement.
     *
     * @param c the literal character to set for the current cursor
     * position.
     */
    public void setChar(char c) {
    }

    /**
     * Helper function for sending commands to the terminal.
     *
     * The common parts of different commands shall be assembled here.
     * The actual printing shall be handled from this command.
     *
     * @param commandString The unique part of a command sequence.
     */
    private void command(String commandString) {
		System.out.print(commandString);
    }
	
	private void setPencil(){
		if (pencil.contains(BLOCK)){
			pencil = "";
		} else {
			pencil = BLOCK;
		}
	}
	
	
	private void saveArt(){
		try {
			File file = new File("art.txt");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(outPut.substring(HEADER.length()+4));
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadArt(){
		String temp = "";
		File file = new File("art.txt");
		try{
		Scanner sc = new Scanner(file); 
  
		while (sc.hasNextLine()) 
			temp += sc.nextLine();
		}catch(IOException e) {
			e.printStackTrace();
		}
		outPut = CONTROL_CODE+CLEAR+HEADER+temp;
	}
	
	public void printer(){
		//String outPut = CONTROL_CODE+CLEAR + HEADER;
		System.out.print(CONTROL_CODE+CLEAR+HEADER+CONTROL_CODE+Integer.toString(vertical) +";"+ Integer.toString(horizontal)+MOVE);
		
		Scanner scanner = new Scanner(System.in);
		while(true){
			
			String input = scanner.nextLine();

			if (input.equals("quit")) break;
			if (input.equals("clear")) clearScreen();
			if (input.equals("save")) saveArt();
			if (input.equals("load")) loadArt();
			if (input.equals("w")) moveCursor(Direction.UP, 1);
			if (input.equals("s")) moveCursor(Direction.DOWN, 1);
			if (input.equals("a")) moveCursor(Direction.BACKWARD, 1);
			if (input.equals("d"))  moveCursor(Direction.FORWARD, 1);
			if (input.equals("red")) setColor(Color.RED);
			if (input.equals("white")) setColor(Color.WHITE);
			if (input.equals("green")) setColor(Color.GREEN);
			if (input.equals("yellow")) setColor(Color.YELLOW);
			if (input.equals("blue")) setColor(Color.BLUE);
			if (input.equals("magenta")) setColor(Color.MAGENTA);
			if (input.equals("cyan")) setColor(Color.CYAN);
			if (input.equals("black")) setColor(Color.BLACK);
			
			String cordinate = Integer.toString(vertical) +";"+ Integer.toString(horizontal);
			if (input.equals("l")) setPencil();
			outPut += color + CONTROL_CODE + cordinate + MOVE +pencil;
			command(outPut);

			if (input.equals("q")){
				String testy = color + CONTROL_CODE + cordinate + MOVE +BLOCK;
				outPut += testy;
			
				command(outPut);
			}
			
			if (input.equals("n")) {				
				int lastIndexBeforeColor = outPut.lastIndexOf("H");
				if (lastIndexBeforeColor == outPut.length() - 1) {
					int whitePixelFirstCoord = outPut.lastIndexOf("\u2588");
					int indexOfH = whitePixelFirstCoord - 1;
					String newundo = "";
					for (int i = 0; i < outPut.length(); i++){
						if (i != indexOfH) {
							newundo += outPut.charAt(i);
						} else {
							newundo += "H";							
							break;					
						}
					}		
					outPut = newundo;				
					System.out.print(outPut);
				} else {
					int firstIndexOfColor = lastIndexBeforeColor + 1;
					String undo = "";
					for (int i = 0; i < outPut.length(); i++){
						if (i != firstIndexOfColor) {
							undo += outPut.charAt(i);
						} else {
							break;					
						}
					}		
					outPut = undo;				
					System.out.print(outPut);
				}
			}
			
			
		}
		System.out.println("quited");
	}
}
