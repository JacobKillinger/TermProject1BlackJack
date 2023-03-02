package gui.Cards;

import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;


public class form {
    private JPanel panelMain;
    private JButton btnDealHit;
    private JLabel lblDealerCard1;
    private JLabel lblDealerCard2;
    private JLabel lblDealerCard3;
    private JLabel lblDealerCard4;
    private JLabel lblDealerCard5;
    private JLabel lblPlayerCard1;
    private JLabel lblPlayerCard2;
    private JLabel lblPlayerCard3;
    private JLabel lblPlayerCard4;
    private JLabel lblPlayerCard5;
    private JButton btnStay;
    private JLabel lblPlayerCard6;
    private JLabel lblDealerCard6;
    private JLabel lblPlayerScore;
    private JLabel lblDealerScore;
    private JButton btnReset;

    static  JFrame frame;
    static Integer counter, dealerScore = 0, cardValue, playerScore = 0, returnedValue;
    static String cardPath, card, dealersHiddenCard, returnedSuit;


    public form() {
        btnDealHit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon icon = new ImageIcon();

                if (counter == 0){
                    //Deals hand for player
                    card = hit(true);
                    cardPath = String.format("src/gui/Cards/Playing Cards/card-%s.png", card);
                    icon = new ImageIcon(cardPath);
                    lblPlayerCard1.setIcon(icon);

                    card = hit(true);
                    cardPath = String.format("src/gui/Cards/Playing Cards/card-%s.png", card);
                    icon = new ImageIcon(cardPath);
                    lblPlayerCard2.setIcon(icon);

                    counter++;

                    //Deals hand for dealer

                    dealersHiddenCard = hit(false); //saves dealers hidden card to be revealed when stay is called
                    cardPath = String.format("src/gui/Cards/Playing Cards/card-back1.png");
                    icon = new ImageIcon(cardPath);
                    lblDealerCard1.setIcon(icon);

                    card = hit(false);
                    cardPath = String.format("src/gui/Cards/Playing Cards/card-%s.png", card);
                    icon = new ImageIcon(cardPath);
                    lblDealerCard2.setIcon(icon);

                    btnDealHit.setText("Hit");
                    lblPlayerScore.setText(String.format("%d",playerScore));
                }
                else{
                    card = hit(true); // Returns "suit-value" as string because card names are variations to "card-clubs-1.png"
                    cardPath = String.format("src/gui/Cards/Playing Cards/card-%s.png", card);
                    icon = new ImageIcon(cardPath);

                    switch (counter) // Applies card images to labels
                    {
                        case 1:
                            lblPlayerCard3.setIcon(icon);
                            counter++;
                            break;

                        case 2:
                            lblPlayerCard4.setIcon(icon);
                            counter++;
                            break;

                        case 3:
                            lblPlayerCard5.setIcon(icon);
                            counter++;
                            break;

                        case 4:
                            lblPlayerCard6.setIcon(icon);
                            counter++;
                            break;

                        default:
                            counter = 0;
                    }
                    if(playerScore > 21){
                        lblPlayerScore.setText("Busted");
                        btnStay.doClick();
                    }
                    else
                    {
                        lblPlayerScore.setText(String.format("%d", playerScore));
                    }

                }
            }
        });


        btnStay.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ImageIcon dealersIcon = new ImageIcon();
                cardPath = String.format("src/gui/Cards/Playing Cards/card-%s.png", dealersHiddenCard);
                dealersIcon = new ImageIcon(cardPath);
                lblDealerCard1.setIcon(dealersIcon);

                while(dealerScore < 16)   //calls the hit function for dealer whenever the dealer score is less than 16 the code breaks here due to dealerscore being null at the moment
                {
                    card = hit(false);
                    cardPath = String.format("src/gui/Cards/Playing Cards/card-%s.png", card);
                    dealersIcon = new ImageIcon(cardPath);
                    counter = 0;

                    switch (counter) // Applies card images to labels
                    {
                        case 0:
                            lblDealerCard3.setIcon(dealersIcon);
                            counter++;
                            break;

                        case 1:
                            lblDealerCard4.setIcon(dealersIcon);
                            counter++;
                            break;

                        case 2:
                            lblDealerCard5.setIcon(dealersIcon);
                            counter++;
                            break;

                        case 3:
                            lblDealerCard6.setIcon(dealersIcon);
                            counter++;
                            break;

                        default:
                            counter = 0;
                    }
                }

                if(dealerScore > 21){
                    lblDealerScore.setText("Busted");
                }
                else
                {
                    lblDealerScore.setText(String.format("%d", dealerScore));
                }

                stay();
            }
        });


        btnReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                playerScore = 0;
                dealerScore = 0;
                counter = 0;

                ImageIcon icon = new ImageIcon("");

                lblPlayerCard1.setIcon(icon);
                lblPlayerCard2.setIcon(icon);
                lblPlayerCard3.setIcon(icon);
                lblPlayerCard4.setIcon(icon);
                lblPlayerCard5.setIcon(icon);
                lblPlayerCard6.setIcon(icon);

                lblDealerCard1.setIcon(icon);
                lblDealerCard2.setIcon(icon);
                lblDealerCard3.setIcon(icon);
                lblDealerCard4.setIcon(icon);
                lblDealerCard5.setIcon(icon);
                lblDealerCard6.setIcon(icon);

                lblPlayerScore.setText("");
                lblDealerScore.setText("");

            }
        });
    }

    public static void main(String[] args) {
        frame = new JFrame("Blackjack");

        frame.setContentPane(new form().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(650,520);
        frame.setVisible(true);

        counter = 0;

    }

    public static String hit(Boolean player)
    {
        String cardFormatted = ""; //For testing to have hit return card name this can be deleted

        returnedSuit = randomCardSuit();

        if (player == true)
        {
            //code to store players score and set cardTest to the formatted string
            returnedValue = randomCardValue(playerScore, player);

        }
        else
        {
            //code to store Dealers score and set cardTest to the formatted string
            returnedValue = randomCardValue(dealerScore, player);
        }
        cardFormatted = String.format("%s-%d", returnedSuit, returnedValue);


        return(cardFormatted);
    }

    public static void stay(){
        //Finds out who wins and resets game
        if(playerScore > dealerScore && playerScore <= 21 || dealerScore > 21 && playerScore < 22)
        {
            JOptionPane.showMessageDialog(frame, "You Win!");
        }
        else if(dealerScore > playerScore && dealerScore <= 21 || playerScore > 21 && dealerScore < 22)
        {
            JOptionPane.showMessageDialog(frame, "You Lose! ):");
        }
        else
        {
            JOptionPane.showMessageDialog(frame, "You Tie!");
        }
    }

    public static int randomCardValue(int currentScore, boolean player)
    {
        Random random = new Random();
        int randomValue = random.nextInt(12) + 1;

        if(randomValue == 1 && currentScore >= 11)
        {
            cardValue = 1;
        }
        else if(randomValue == 1)
        {
            cardValue = 11;
        }
        else if(randomValue == 11 || randomValue == 12 || randomValue == 13)
        {
            cardValue = 10;
        }
        else
        {
            cardValue = randomValue;
        }

        if(player == true)
        {
            playerScore = playerScore + cardValue;

        }
        else
        {
            dealerScore = dealerScore + cardValue;
        }

        return randomValue;
    }

    public static String randomCardSuit(){
        String randomSuit = "";

        Random random = new Random();
        int randomSuitValue = random.nextInt(4);

        switch (randomSuitValue)
        {
            case 0:
                randomSuit = "clubs";
                break;

            case 1:
                randomSuit = "spades";
                break;

            case 2:
                randomSuit = "diamonds";
                break;

            case 3:
                randomSuit = "hearts";
                break;
        }

        return randomSuit;
    }


}
