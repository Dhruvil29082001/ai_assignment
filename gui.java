import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class gui extends JFrame{
    JLabel title, string, remaining, choose, pScore, compScore;
    JTextField playerChoice;
    JButton p, comp, submit, restart;

    static boolean playerTurn, isWinnerFound = false;
    static String gString, playersValue, computerValue;
    static int playerScore, computerScore, remainingScore, compChoiceIndex, delay=4000;

    gui()
    {
        title = new JLabel();
        title.setText("Who is starting the game?");
        title.setBounds(250, 20, 300, 50);
        title.setForeground(Color.RED);

        p = new JButton();
        p.setText("Player");
        p.setBounds(200, 300, 100, 50);
        p.setForeground(Color.BLUE);

        comp = new JButton();
        comp.setText("Computer");
        comp.setBounds(400, 300, 100, 50);
        comp.setForeground(Color.BLUE);



        string = new JLabel();
        string.setBounds(20, 70, 300, 50);
        string.setForeground(Color.BLACK);
        string.setVisible(false);

        remaining = new JLabel();
        remaining.setBounds(20, 120, 300, 50);
        remaining.setForeground(Color.BLACK);
        remaining.setVisible(false);

        choose = new JLabel();
        choose.setBounds(20, 170, 100, 50);
        choose.setForeground(Color.BLACK);
        choose.setVisible(false);

        playerChoice = new JTextField();
        playerChoice.setBounds(220, 170, 50, 50);
        playerChoice.setVisible( false );

        submit = new JButton();
        submit.setText("SUBMIT");
        submit.setBounds(300, 170, 100, 50);
        submit.setForeground(Color.BLUE);
        submit.setVisible(false);

        pScore = new JLabel();
        pScore.setBounds(20, 300, 200, 50);
        pScore.setForeground(Color.BLACK);
        pScore.setVisible(false);

        compScore = new JLabel();
        compScore.setBounds(350, 300, 200, 50);
        compScore.setForeground(Color.BLACK);
        compScore.setVisible(false);


        restart = new JButton("restart");
        restart.setBounds(300,250, 100,50);
        restart.setVisible(false);
        restart.setForeground(Color.GREEN);

        this.add(title);
        this.add(p);
        this.add(comp);

        this.add(string);
        this.add(remaining);
        this.add(choose);
        this.add(playerChoice);
        this.add(submit);
        this.add(pScore);
        this.add(compScore);

        this.add(restart);

        this.setTitle("FASTEST SUM GAME");
        this.setLayout( null );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(700, 500);
        this.setResizable(false);
        this.setVisible(true);

        p.addActionListener( e -> {
            playerTurn = true;
            title.setText("Player's turn");
            title.setBounds(300, 20, 200, 50);
            setInterface();
            string.setText("STRING: " + stringFormatter(gString));
            remaining.setText("REMAINING SUM: " + remainingScore);
            choose.setText("MAKE CHOICE");
            pScore.setText("Player's Score: " + playerScore);
            compScore.setText("Computer's Score: " + computerScore);
        });

        comp.addActionListener( e -> {
            compChoiceIndex = computerMove(gString);
            title.setText("Computer's turn");
            title.setBounds(300, 20, 200, 50);
            setInterface();
            submit.setVisible(false);
            string.setText("STRING: " + stringFormatter(gString));
            remaining.setText("REMAINING SUM: " + remainingScore);
            choose.setVisible(false);
            playerChoice.setVisible(false);
            pScore.setText("Player's Score: " + playerScore);
            compScore.setText("Computer's Score: " + computerScore);

            computerScore += Character.getNumericValue(gString.charAt(compChoiceIndex));
            gString = removeString(gString, Character.toString(gString.charAt(compChoiceIndex)));
            remainingScore = Sum(gString);

            // after delay
            Timer time = new Timer( delay, d -> {
                string.setText("STRING: " + stringFormatter(gString));
                remaining.setText("REMAINING SUM: " + remainingScore);
                choose.setText("MAKE CHOICE");
                pScore.setText("Player's Score: " + playerScore);
                compScore.setText("Computer's Score: " + computerScore);
                playerTurn= true;
                title.setText("Player's turn");
                submit.setVisible(true);
                choose.setVisible(true);
                playerChoice.setVisible(true);
            });
            //create a delay of 1 sec
            time.start();
            time.setRepeats( false );
        });


        //default values
        gString = randomString();
        playerScore = 0;
        computerScore = 0;
        remainingScore = Sum(gString);

        graph.createGraph(gString);

        submit.addActionListener( e -> {
            //also set for winning
            winnerFound();
            if(!isWinnerFound) {
                title.setText("Player's turn");
                playersValue = playerChoice.getText();
                playerScore += Integer.parseInt(playersValue);
                gString = removeString(gString, playersValue);
                string.setText("STRING: " + stringFormatter(gString));
                remainingScore = Sum(gString);
                remaining.setText(String.valueOf("REMAINING SUM: " + remainingScore));
                pScore.setText("Player's Score: " + playerScore);
                playerTurn = false;
                playerChoice.setText("");
                winnerFound();
                computerPlays();
            }
            else
                declareWinner();
        });
    }

    public void computerPlays()
    {
        if(!isWinnerFound ){
            title.setText("Computer's turn");
            submit.setVisible(false);
            choose.setVisible(false);
            playerChoice.setVisible(false);
            compScore.setText("Computer's Score: " + computerScore);

            compChoiceIndex = computerMove(gString);
            computerScore += Character.getNumericValue(gString.charAt(compChoiceIndex));
            gString = removeString(gString, Character.toString(gString.charAt(compChoiceIndex)));
            remainingScore = Sum(gString);

            Timer time = new Timer( delay, d -> {
                string.setText("STRING: " + stringFormatter(gString));
                remaining.setText("REMAINING SUM: " + remainingScore);
                choose.setText("MAKE CHOICE");
                pScore.setText("Player's Score: " + playerScore);
                compScore.setText("Computer's Score: " + computerScore);
                playerTurn= true;
                title.setText("Player's turn");
                submit.setVisible(true);
                choose.setVisible(true);
                playerChoice.setVisible(true);
                winnerFound();
            });
            //create a delay of 1 sec
            time.start();
            time.setRepeats( false );
        } else
            declareWinner();

    }


    int computerMove( String s )
    {
        int max = 0, index=0;
        for(int i=0; i<s.length(); i++)
        {
            if(Character.getNumericValue(s.charAt(i)) >= max){
                max = Character.getNumericValue(s.charAt(i));
                index = i;
            }
        }
        return index;
    }

    public String removeString( String s, String ch )
    {
        String str = "";
        int count = 0;

        for(int i=0; i<s.length(); i++)
        {
            if(s.charAt(i) == ch.charAt(0) && count<1)
                count++;
            else
                str = str + s.charAt(i);
        }
        return str;
    }

    public void setInterface()
    {
        p.setVisible(false);
        comp.setVisible(false);
        string.setVisible(true);
        remaining.setVisible(true);
        choose.setVisible(true);
        playerChoice.setVisible(true);
        submit.setVisible(true);
        pScore.setVisible(true);
        compScore.setVisible(true);

    }

    public void winnerFound()
    {
        remainingScore = Sum(gString);
        isWinnerFound = (remainingScore == 0 );
    }

    public void declareWinner()
    {
        // reset everything
        title.setText("Deciding winner");
        string.setVisible(false);
        remaining.setVisible(false);
        playerChoice.setVisible(false);
        choose.setVisible(false);
        submit.setVisible(false);

        Timer time = new Timer( 2000, d -> {
            if(playerScore>computerScore)
            {
                title.setText("Player is the winner");
            }
            else if(computerScore > playerScore)
            {
                title.setText("Computer is the winner");
            }
            else {
                title.setText("Game Draw");
            }

            restart.setVisible(true);
            restart.addActionListener( e -> {
                new gui();
                this.dispose();
            });
        });
        //create a delay of 1 sec
        time.start();
        time.setRepeats( false );

    }

    String stringFormatter(String s)
    {
        String formatted = "";
        for(int i=0; i<s.length(); i++)
        {
            formatted =  formatted + s.charAt(i) + " ";
        }
        return formatted;
    }

    static String randomString()
    {
        int number;
        String str = "";
        Random random = new Random();

        for(int i=0; i<6; i++)
        {
            number = random.nextInt((10 - 1) + 1) + 1; // random number between 1 - 10
            if(String.valueOf(number).equals("0"))
                str = str + '9';
            else
                str = str + String.valueOf(number);
        }

        return str;
    }

    int Sum(String s)
    {
        int a, sum = 0;
        for(int i=0; i<s.length(); i++)
        {
            char ab = s.charAt(i);
            sum = sum + Character.getNumericValue(ab);
        }

        return sum;
    }

}
