/**
Program: Assignment 2: Application - Ball Navigation of a Maze
Filename: CBallMaze.java
@author: Warren Haskins (14438496)
Course: BSc Computing Year 1
Module: CSY1020 Problem Solving & Programming
Tutor: Gary Hill
Date: 30/04/15
*/

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class CBallMaze extends JFrame implements ActionListener, KeyListener    
{
   //Declarations for all used instances of a class.
	private JButton rightButton;
    private JButton leftButton;
    private JButton downButton;
    private JButton upButton;
    private JButton option1;
    private JButton option2;
    private JButton option3;
    private JButton exit;
    private JButton act;
    private JButton run;
    private JButton reset;
    private JButton[] mapButton = new JButton [208];
    private JButton compass;
    private JPanel mainPanel;
    private JPanel sidePanel;
    private JPanel bottomPanel;
    private JPanel optionSquareDirectionPanel;
    private JPanel fourArrowPanel;
    private JPanel spaceClaimerPanel;
    private JPanel compassContainer;
    private JPanel OptionButtonsPanel;
    private JPanel fillerSidePanel;
    private JPanel actRunResetPanel;
    private JPanel speedSliderPanel;
    private JPanel bottomFillerPanel;
    private JMenuBar menuBar;
    private JMenu menuOptionFile;
    private JMenu menuOptionEdit;
    private JMenu menuOptionExtras;
    private JMenuItem menuItemFileNew;
    private JMenuItem menuItemFileExit;
    private JMenuItem menuItemEditOption1;
    private JMenuItem menuItemEditOption2;
    private JMenuItem menuItemEditOption3;
    private JMenuItem menuItemExtrasRun;
    private JMenuItem menuItemExtrasAbout;
    private JTextField textFieldOption;
    private JTextField textFieldSquare;
    private JTextField textFieldDirection;
    private JLabel labelOption;
    private JLabel labelSquare;
    private JLabel labelDirection;
    private JLabel labelSpeed;
    private JSlider speedSlider;
    private Icon iconAct;
    private Icon iconRun;
    private Icon iconReset;
    private Icon compassNorth;
    private Icon compassSouth;
    private Icon compassEast;
    private Icon compassWest;
    private Icon sandTile;
    private Icon white;
    private Icon sandstone;
    private Icon sandBall;
    private javax.swing.Timer timer;
    
    int ball = 15; //used to keep track of the ball's current location.
    int[] pathCheck = new int[150]; //array used to record all valid path tiles for the current scenario.
	int option = 1; //used to keep track on the currently selected map.
	int buttonMade = 0; //used to record how many buttons have been created in the main panel.
	boolean timerOn = false; //used to record whether the timer is currently on.
	char lastMove; //used to record the last movement made by the ball when automating the scenario.
	
    public static void main (String[] args)
    {
    	//Creates the frame of the application.
    	CBallMaze frame = new CBallMaze();
        frame.setSize(775, 650);
        frame.createGUI();
        frame.show();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setTitle("                                                                                CBallMaze Ball Maze Application");
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/gold-ball.png")));
    }
 
    private void createGUI()
    {
    	//Creates the GUI of the application.
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Container window = getContentPane();
        window.setLayout(new BorderLayout() );
        
        try	
        {
        	iconAct = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/Act.jpg")));
        	iconRun = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/Run.jpg")));
        	iconReset = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/Reset.jpg")));
        	compassNorth = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/north.jpg")));
        	compassSouth = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/south.jpg")));
        	compassEast = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/east.jpg")));
        	compassWest = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/west.jpg")));
        	sandTile = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/sand.jpg")));
        	white = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/white32x32.jpg")));
        	sandstone = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/sandstone.jpg")));
        	sandBall = new ImageIcon(Toolkit.getDefaultToolkit().createImage(CBallMaze.class.getResource("images/sand60x60.png")));
        }
        catch (Exception e)
        {
            System.err.println("image not found"+e);
        } 
        
        createMenuBar();
     
        createMainPanel();
        window.add(mainPanel, BorderLayout.CENTER);
        
        createSidePanel();
        window.add(sidePanel, BorderLayout.EAST);
 
        createBottomPanel();
        window.add(bottomPanel, BorderLayout.SOUTH);
        
        setFocus();
        window.setFocusable(true);
        window.addKeyListener(this);
    }
    
   public void actionPerformed(ActionEvent aEvent)
   	{
	   //Locates the source of an action listener being triggered and uses it to determine which action to perform.
	   	Object aESource = aEvent.getSource();
	   	
	   	if (aESource == upButton){
	   		up();
	   	}
	   	else if (aESource == downButton){
	   		down();
	   	}
	   	else if (aESource == leftButton){
	   		left();
	   	}
	   	else if (aESource == rightButton){
	   		right();
	   	}
	   	else if(aESource == exit || aESource == menuItemFileExit){
	   		System.exit(0);
	   	}
	   	else if(aESource == reset||aESource == menuItemFileNew){
	   		ball = 15;
	   		textFieldOption.setText("         "+"1");
	   		textFieldSquare.setText("        "+"15");
	   		textFieldDirection.setText("         "+"W");
	   		if (timerOn == true){
	   			timer.stop();
	   			timerOn = false;
	   		}
	   		speedSlider.setValue(350);
	   		option = 1;
	   		fillMainPanel(option);
	   		lastMove = 'a';
	   	}
	   	else if(aESource == run || aESource == menuItemExtrasRun){
	   		timer = new javax.swing.Timer(speedSlider.getValue(),this);
	   		timerOn = true;
	   		timer.start();
	   	}
	   	else if(aESource == timer){
		   		timer.setDelay(speedSlider.getValue());
		   		runBallMaze();
	   	}
	   	else if(aESource == act){
	   		runBallMaze();
	   	}
	   	else if(aESource == option1 || aESource == menuItemEditOption1){
	   		option = 1;
	   		textFieldOption.setText("         "+"1");
	   		ball = 15;
	   		fillMainPanel(option);
	   		if (timerOn == true){
	   			timer.stop();
	   			timerOn = false;
	   		}
	   	}
	   	else if(aESource == option2 || aESource == menuItemEditOption2){
	   		option = 2;
	   		textFieldOption.setText("         "+"2");
	   		ball = 0;
	   		fillMainPanel(option);
	   		if (timerOn == true){
	   			timer.stop();
	   			timerOn = false;
	   		}
	   	}
	   	else if(aESource == option3 || aESource == menuItemEditOption3){
	   		option = 3;
	   		textFieldOption.setText("         "+"3");
	   		ball = 199;
	   		fillMainPanel(option);
	   		if (timerOn == true){
	   			timer.stop();
	   			timerOn = false;
	   		}
	   	}
	   	else if(aESource == menuItemExtrasAbout){
	   		JOptionPane.showMessageDialog(null,"Program created by: Warren Haskins \nStudent Number: 14438496 \n2015");
	   	}
   	}
   
   private void runBallMaze(){
	   //This method contains the algorithms used to allow the ball to complete each scenario.
	   if(option == 1 && ball != 192){
		   if(pathChecker(16) == true){
			   down();
	  	       lastMove = 'd';
		   }else if(pathChecker(-1) == true && lastMove != 'r'){
			   left();
			   lastMove = 'l';
		   }else if(pathChecker(1) == true){
			   right();
			   lastMove = 'r';
		   }
	   }else if(option == 1 && ball == 192){
		   timer.stop();
	   }else if(option == 2 && ball != 103){
		   if(pathChecker(16) == true && lastMove != 'u'){
			   down();
	  	       lastMove = 'd';
		   }else if(pathChecker(-16) == true && lastMove != 'd'){
			   up();
	  	       lastMove = 'u';
		   }else if(pathChecker(-1) == true && lastMove != 'r' && ball!=192){
			   left();
			   lastMove = 'l';
		   }else if(pathChecker(1) == true && lastMove != 'l'){
			   right();
			   lastMove = 'r';
		   }
	   }else if(option == 2 && ball == 103){
		   timer.stop();
	   }else if(option == 3 && ball != 169){
		   if(pathChecker(-16) == true && lastMove != 'd'){
			   up();
	  	       lastMove = 'u';
		   }else if(pathChecker(16) == true && lastMove != 'u' && ball !=22 && ball !=126){
			   down();
	  	       lastMove = 'd';
		   }else if(pathChecker(-1) == true && lastMove != 'r'){
			   left();
			   lastMove = 'l';
		   }else if(pathChecker(1) == true){
			   right();
			   lastMove = 'r';
		   }
	   }else if(option == 3 && ball == 169){
		   timer.stop();
	   }
   }
   
   private void up(){
	   	//This method is used when the ball is asked to move upwards.
	   	compass.setIcon(compassNorth);
  		textFieldDirection.setText("         "+"N");
  		
  		if(option == 1 && ball != 192 || option == 2 && ball != 103 || option == 3 && ball != 169){
	  		if (pathChecker(-16) == true && ball>15){
	  			mapButton[ball].setIcon(sandTile);
	  			ball-=16;
	  			mapButton[ball].setIcon(sandBall);
	  			String squareText = new Integer(ball).toString();
	  	        textFieldSquare.setText("        " + squareText);
	  		}
  		}
   }
   
   private void down(){
	   	//This method is used when the ball is asked to move downwards. 
	   	compass.setIcon(compassSouth);
  		textFieldDirection.setText("         "+"S");
  		
  		if(option == 1 && ball != 192 || option == 2 && ball != 103 || option == 3 && ball != 169){
	  		if (pathChecker(16) == true && ball<192){
	  			mapButton[ball].setIcon(sandTile);
	  			ball+=16;
	  			mapButton[ball].setIcon(sandBall);
	  			String squareText = new Integer(ball).toString();
	  	        textFieldSquare.setText("        " + squareText);
	  		}
  		}
   }
   
   private void left(){
	   	//This method is used when the ball is asked to move to the left.
	   	compass.setIcon(compassWest);
  		textFieldDirection.setText("         "+"W");
  		
  		if(option == 1 && ball != 192 || option == 2 && ball != 103 || option == 3 && ball != 169){
	  		if (pathChecker(-1) == true && ball>0 && ball!=16 && ball!=32 && ball!=48 && ball!=64 && ball!=80 && ball!=96 && ball!=112 && ball!=128 && ball!=144 && ball!=160 && ball!=176 && ball!=192){
	  			mapButton[ball].setIcon(sandTile);
	  			ball-=1;
	  			mapButton[ball].setIcon(sandBall);
	  			String squareText = new Integer(ball).toString();
	  	        textFieldSquare.setText("        " + squareText);
	  		}
  		}
   }
   
   private void right(){
	   	//This method is used when the ball is asked to move to the right.
	   	compass.setIcon(compassEast);
  		textFieldDirection.setText("         "+"E");
  		
  		if(option == 1 && ball != 192 || option == 2 && ball != 103 || option == 3 && ball != 169){
	  		if (pathChecker(1) == true && ball<207 && ball!=15 && ball!=31 && ball!=47 && ball!=63 && ball!=79 && ball!=95 && ball!=111 && ball!=127 && ball!=143 && ball!=159 && ball!=175 && ball!=191 && ball!=207){
	  			mapButton[ball].setIcon(sandTile);
	  			ball+=1;
	  			mapButton[ball].setIcon(sandBall);
	  			String squareText = new Integer(ball).toString();
	  	        textFieldSquare.setText("        " + squareText);
	  		}
  		}
   }
   
   private boolean pathChecker(int calc)
   {
	   //This method determines whether the instruction given to the ball will allow it to remain on the path.
	   boolean canMove = false;
	   int destination = ball + calc;
	   
	   for(int count = 0; count < pathCheck.length; count++){
		  if(pathCheck[count] == destination){
			  canMove = true;
		  }
	   }
	   
	   return canMove;
   }
    
    private void createMainPanel()
    {
    	//This method creates the main panel and then asks for the contents to be created.
    	mainPanel = new JPanel();
        mainPanel.setBackground(Color.white);
        mainPanel.setLayout(new GridLayout(13,16));
        fillMainPanel(option);
    }
    
    private void createSidePanel()
    {
    	//This method creates the side panel and then asks for the contents to be created.
    	sidePanel = new JPanel();
        sidePanel.setPreferredSize(new Dimension(150, 450));
        createOptionSquareDirectionpanel();
        createFourArrowPanel();
        createFillerSidePanel();
        createOptionButtonsPanel();
        createCompassContainer();
    }

    private void createBottomPanel()
    {
    	//This method creates the bottom panel and then asks for the contents to be created.
    	bottomPanel = new JPanel();
        bottomPanel.setPreferredSize(new Dimension(735, 60));
        createActRunResetPanel();
        createBottomFillerPanel();
        createSpeedSliderPanel();
    }
    
    private void fillMainPanel(int selectedOption)
    {
    	//This method creates the map, clears the path check array and fills it with the new values.
    	for(int count = 0; count < 150; count++){
    		pathCheck[count] = 999;
    	}
    	
    	for(int buttonCount = 0, pcIndex = 0; buttonCount<208; buttonCount++, buttonMade++){
    		if(buttonMade < 208){
    			//This if statement creates the actual buttons in the main panel. It will not be triggered if 208 buttons have already been created.
    			mapButton[buttonCount]=new JButton();
    			mainPanel.add(mapButton[buttonCount]);
    			mapButton[buttonCount].setContentAreaFilled(false);
        		mapButton[buttonCount].setBorderPainted(false);
    		}
			mapButton[buttonCount].setIcon(white);
    		if(option == 1){
    			//This creates the layout for the option 1 map.
	    		if (buttonCount <= 15||buttonCount >=48 && buttonCount <=63||buttonCount >=96 && buttonCount <=111||buttonCount >=144 && buttonCount <=159||buttonCount >=193 && buttonCount <=207){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if (buttonCount==17||buttonCount==33||buttonCount==21||buttonCount==37||buttonCount==25||buttonCount==41){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount==66||buttonCount==82||buttonCount==70||buttonCount==86||buttonCount==75||buttonCount==91){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount==113||buttonCount==129||buttonCount==117||buttonCount==133||buttonCount==124||buttonCount==140){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount==162||buttonCount==178||buttonCount==166||buttonCount==182){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount==192){
	    			mapButton[buttonCount].setIcon(sandstone);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount==15){
	    			mapButton[buttonCount].setIcon(sandBall);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
    		}
	    	if(option==2){
	    	  //This creates the layout for the option 2 map.
	    	  if(buttonCount <= 15||buttonCount >=32 && buttonCount <=45|| buttonCount >= 66 && buttonCount <= 75||buttonCount >= 100 && buttonCount <= 103||
	    	  buttonCount >= 132 && buttonCount <= 139||buttonCount >= 162 && buttonCount <= 173||buttonCount >= 192 && buttonCount <= 207){
	    	  		mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    	  }
	    	  if(buttonCount == 16|| buttonCount == 32||buttonCount == 48||buttonCount == 64||buttonCount == 80||buttonCount == 96||buttonCount == 112||buttonCount == 128||buttonCount == 144){
	    	  		mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    	  }
	    	  if(buttonCount == 160||buttonCount == 176||buttonCount == 192||buttonCount == 31||buttonCount == 47||buttonCount == 63||buttonCount == 79||buttonCount == 95||buttonCount == 111){
	    	  		mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    	  }
	    	  if(buttonCount == 127||buttonCount == 143||buttonCount == 159||buttonCount == 175||buttonCount == 191||buttonCount == 207||buttonCount == 61||buttonCount == 77||buttonCount == 93){
	    	 		mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    	  }
	    	  if(buttonCount == 109||buttonCount == 125||buttonCount == 141||buttonCount == 157||buttonCount == 183||buttonCount == 100||buttonCount == 116||buttonCount == 98||buttonCount == 82){
	    	  		mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    	  }
	    	  if(buttonCount == 114||buttonCount == 130||buttonCount == 146||buttonCount == 91||buttonCount == 107||buttonCount == 123){
	    	  		mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    	  }
	    	  if(buttonCount == 0){
	    		  mapButton[buttonCount].setIcon(sandBall);
	    		  pathCheck[pcIndex] = buttonCount;
	    		  pcIndex++;
	    	  }
	    	  if(buttonCount == 103){
	    		  mapButton[buttonCount].setIcon(sandstone);
	    		  pathCheck[pcIndex] = buttonCount;
	    		  pcIndex++;
	    	  }
	    	}
	    	if(option==3){
	    		//This creates the layout for the option 3 map.
	    		if(buttonCount < 7 && buttonCount > 0|| buttonCount == 17|| buttonCount > 21 && buttonCount < 31|| buttonCount == 33|| buttonCount == 38|| buttonCount == 46 || buttonCount>48 && buttonCount<60 || buttonCount == 62){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount == 65|| buttonCount ==75|| buttonCount ==78|| buttonCount ==81|| buttonCount >82 && buttonCount < 92|| buttonCount ==94|| buttonCount ==97|| buttonCount ==102){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount == 110|| buttonCount >111 && buttonCount < 119|| buttonCount > 119 && buttonCount < 127|| buttonCount == 128|| buttonCount ==130|| buttonCount == 140 || buttonCount == 142){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount == 144|| buttonCount > 145 && buttonCount < 152|| buttonCount >152 && buttonCount < 157 || buttonCount == 158 || buttonCount == 160 || buttonCount == 167 || buttonCount == 174){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount == 176 || buttonCount == 183 || buttonCount > 186 && buttonCount < 191 || buttonCount >191 && buttonCount < 200 || buttonCount == 206){
	    			mapButton[buttonCount].setIcon(sandTile);
	    			pathCheck[pcIndex] = buttonCount;
	    			pcIndex++;
	    		}
	    		if(buttonCount == 199){
	    			mapButton[buttonCount].setIcon(sandBall);
	    			pathCheck[pcIndex] = buttonCount;
		    		pcIndex++;
	    		}
	    		if(buttonCount == 169){
	    			mapButton[buttonCount].setIcon(sandstone);
		    		pathCheck[pcIndex] = buttonCount;
		    		pcIndex++;
	    		}
	    		
	    	}
    	}
    }
    
    public void createActRunResetPanel()
    {
    	//This method adds a panel containing the act/run/reset buttons to the bottom panel.
    	actRunResetPanel = new JPanel();
    	bottomPanel.add(actRunResetPanel);
    	
    	act = new JButton("Act");
    	act.setIcon(iconAct);
    	actRunResetPanel.add(act);
    	act.addActionListener(this);
    	
    	run = new JButton("Run");
    	run.setIcon(iconRun);
    	actRunResetPanel.add(run);
    	run.addActionListener(this);
    	
    	reset = new JButton("Reset");
    	reset.setIcon(iconReset);
    	actRunResetPanel.add(reset);
    	reset.addActionListener(this);
    }
    
    public void createBottomFillerPanel()
    {
    	//This method creates an invisible filler panel used to space out the other panels.
    	bottomFillerPanel = new JPanel();
    	bottomFillerPanel.setPreferredSize(new Dimension(100,40));
    	bottomPanel.add(bottomFillerPanel);
    	
    }
    
    public void createSpeedSliderPanel()
    {
    	//This method adds a panel containing the speed slider to the bottom panel, it also adds ticks below the speed slider.
    	speedSliderPanel = new JPanel();
    	bottomPanel.add(speedSliderPanel);
    	
    	labelSpeed = new JLabel("Speed: ");
    	speedSliderPanel.add(labelSpeed);
    	
    	speedSlider = new JSlider(100,1000,350);
    	speedSlider.setMajorTickSpacing(100);
    	speedSlider.setPaintTicks(true);
    	speedSliderPanel.add(speedSlider);
    }
    
    public void createMenuBar()
    {
    	//This method creates the menu bar, the menu options, and the menu items.
    	menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        menuOptionFile = new JMenu("File");
        menuBar.add(menuOptionFile);        

        menuOptionEdit = new JMenu("Edit");
        menuBar.add(menuOptionEdit);
        
        menuOptionExtras = new JMenu("Extras");
        menuBar.add(menuOptionExtras);
        
        menuItemFileNew = new JMenuItem("New Game");
        menuOptionFile.add(menuItemFileNew);
        menuItemFileNew.addActionListener(this);
        
        menuItemFileExit = new JMenuItem("Exit");
        menuOptionFile.add(menuItemFileExit);
        menuItemFileExit.addActionListener(this);
        
        menuItemEditOption1 = new JMenuItem("Option 1");
        menuOptionEdit.add(menuItemEditOption1);
        menuItemEditOption1.addActionListener(this);
        
        menuItemEditOption2 = new JMenuItem("Option 2");
        menuOptionEdit.add(menuItemEditOption2);
        menuItemEditOption2.addActionListener(this);
        
        menuItemEditOption3 = new JMenuItem("Option 3");
        menuOptionEdit.add(menuItemEditOption3);
        menuItemEditOption3.addActionListener(this);
        
        menuItemExtrasRun = new JMenuItem("Run current map");
        menuOptionExtras.add(menuItemExtrasRun);
        menuItemExtrasRun.addActionListener(this);
        
        menuItemExtrasAbout = new JMenuItem("About");
        menuOptionExtras.add(menuItemExtrasAbout);
        menuItemExtrasAbout.addActionListener(this);
    }
    
    private void createOptionSquareDirectionpanel()
    {
    	//This method adds a panel to the side panel containing the option, square and direction labels and text fields.
    	optionSquareDirectionPanel = new JPanel();
        optionSquareDirectionPanel.setPreferredSize(new Dimension(140, 100));
        optionSquareDirectionPanel.setLayout(new GridLayout(3,3));
        sidePanel.add(optionSquareDirectionPanel);
        
        labelOption = new JLabel("Option:");
        optionSquareDirectionPanel.add (labelOption);
        
        textFieldOption = new JTextField("         "+"1");
        optionSquareDirectionPanel.add (textFieldOption);
        
        labelSquare = new JLabel("Square:");
        optionSquareDirectionPanel.add (labelSquare);
        
        textFieldSquare = new JTextField("        "+"15");
        optionSquareDirectionPanel.add (textFieldSquare);
        
        labelDirection = new JLabel("Direction:");
        optionSquareDirectionPanel.add (labelDirection);
        
        textFieldDirection = new JTextField("         "+"W");
        optionSquareDirectionPanel.add (textFieldDirection);
    }
    
    private void createFourArrowPanel()
    {
    	//This method adds a panel to the side panel containing the arrow buttons used to navigate the scenario.
    	fourArrowPanel = new JPanel();
        fourArrowPanel.setPreferredSize(new Dimension(140, 100));
        fourArrowPanel.setLayout(new GridLayout(3,3,2,2));
        sidePanel.add(fourArrowPanel);
        
        spaceClaimerPanel = new JPanel();
        fourArrowPanel.add(spaceClaimerPanel);
        
        upButton = new JButton("^");
        fourArrowPanel.add(upButton);
        upButton.addActionListener(this);
        
        spaceClaimerPanel = new JPanel();
        fourArrowPanel.add(spaceClaimerPanel);
        
        leftButton = new JButton ("<");
        fourArrowPanel.add(leftButton);
        leftButton.addActionListener(this);
        
        spaceClaimerPanel = new JPanel();
        fourArrowPanel.add(spaceClaimerPanel);
        
        rightButton = new JButton (">");
        fourArrowPanel.add(rightButton);
        rightButton.addActionListener(this);
        
        spaceClaimerPanel = new JPanel();
        fourArrowPanel.add(spaceClaimerPanel);
        
        downButton = new JButton ("v");
        fourArrowPanel.add(downButton);
        downButton.addActionListener(this);
        
        spaceClaimerPanel = new JPanel();
        fourArrowPanel.add(spaceClaimerPanel);
    }
    
   private void createOptionButtonsPanel()
    {
	    //This method adds a panel containing the map option buttons and the exit button to the side panel.
    	OptionButtonsPanel = new JPanel();
    	OptionButtonsPanel.setPreferredSize(new Dimension(140,80));
    	OptionButtonsPanel.setLayout(new GridLayout(2,2,2,2));
    	sidePanel.add(OptionButtonsPanel);
    	
    	option1 = new JButton("Option 1");
    	option1.setFont(new Font("Ariel",Font.BOLD, 8));
    	OptionButtonsPanel.add(option1);
    	option1.addActionListener(this);
    	
    	option2 = new JButton("Option 2");
    	option2.setFont(new Font("Ariel",Font.BOLD, 8));
    	OptionButtonsPanel.add(option2);
    	option2.addActionListener(this);
    	
    	option3 = new JButton("Option 3");
    	option3.setFont(new Font("Ariel",Font.BOLD, 8));
    	OptionButtonsPanel.add(option3);
    	option3.addActionListener(this);
    	
    	exit = new JButton("Exit");
    	exit.setFont(new Font("Ariel",Font.BOLD, 8));
    	OptionButtonsPanel.add(exit);
    	exit.addActionListener(this);   		
    }
   
    private void createCompassContainer()
    {
    	//This method adds a panel containing the compass to the side panel.
    	compassContainer = new JPanel();
    	compassContainer.setPreferredSize(new Dimension(120,100));
    	sidePanel.add(compassContainer);
    	compass = new JButton();
    	compass.setIcon(compassWest);
    	compassContainer.add(compass);
    }
    
    private void createFillerSidePanel()
    {
    	//This method creates an invisible panel in the side panel to properly space the other panels.
    	fillerSidePanel = new JPanel();
    	fillerSidePanel.setPreferredSize(new Dimension(80,120));
    	sidePanel.add(fillerSidePanel);
    }
    
    public void setFocus(){
    	//This method sets all elements other than the container to not focusable so that only the container requires a key listener.
    	rightButton.setFocusable(false);
    	leftButton.setFocusable(false);
    	downButton.setFocusable(false);
    	upButton.setFocusable(false);
    	option1.setFocusable(false);
    	option2.setFocusable(false);
    	option3.setFocusable(false);
    	exit.setFocusable(false);
    	act.setFocusable(false);
    	run.setFocusable(false);
    	reset.setFocusable(false);
    	for(int count = 0; count < mapButton.length; count++){
    		mapButton[count].setFocusable(false);
    	}
    	compass.setFocusable(false);
    	mainPanel.setFocusable(false);
    	sidePanel.setFocusable(false);
    	bottomPanel.setFocusable(false);
    	optionSquareDirectionPanel.setFocusable(false);
    	fourArrowPanel.setFocusable(false);
    	spaceClaimerPanel.setFocusable(false);
    	compassContainer.setFocusable(false);
    	OptionButtonsPanel.setFocusable(false);
    	fillerSidePanel.setFocusable(false);
    	actRunResetPanel.setFocusable(false);
    	speedSliderPanel.setFocusable(false);
    	bottomFillerPanel.setFocusable(false);
    	menuBar.setFocusable(false);
    	menuOptionFile.setFocusable(false);
    	menuOptionEdit.setFocusable(false);
    	menuOptionExtras.setFocusable(false);
    	menuItemFileNew.setFocusable(false);
    	menuItemFileExit.setFocusable(false);
    	menuItemEditOption1.setFocusable(false);
    	menuItemEditOption2.setFocusable(false);
    	menuItemEditOption3.setFocusable(false);
    	menuItemExtrasRun.setFocusable(false);
    	textFieldOption.setFocusable(false);
    	textFieldSquare.setFocusable(false);
    	textFieldDirection.setFocusable(false);
    	labelOption.setFocusable(false);
    	labelSquare.setFocusable(false);
    	labelDirection.setFocusable(false);
    	speedSlider.setFocusable(false);
    }

	public void keyPressed(KeyEvent e) {
		//This method contains the instructions for what to do when a key is pressed.
		if (e.getKeyCode() == KeyEvent.VK_UP){
			up();
		}else if (e.getKeyCode() == KeyEvent.VK_DOWN){
			down();
		}else if (e.getKeyCode() == KeyEvent.VK_LEFT){
			left();
		}else if (e.getKeyCode() == KeyEvent.VK_RIGHT){
			right();
		}
	}
	//These two methods are unused but necessary for the key listener to stop creating errors.
	public void keyReleased(KeyEvent arg0) {	
	}
	public void keyTyped(KeyEvent arg0) {
	}   
}