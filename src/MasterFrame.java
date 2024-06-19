import java.awt.BorderLayout;
import java.awt.FlowLayout;
import static java.awt.FlowLayout.LEFT;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 * MasterFrame GUI for Craps game, extends JFrame. Comprised of three JPanels for
 * Balance, Wager, and Main.
 * @author <a href="mailto:alexandre.d.paquette@gmail.com">Alexandre Paquette</a>
 * @date 04/21/2022
 */
public class MasterFrame extends JFrame {
    private BalanceFrame balanceFrame = new BalanceFrame();
    private WagerFrame wagerFrame = new WagerFrame();
    private MainFrame mainFrame = new MainFrame();

    /**
     * Constructor for MasterFrame GUI
     */
    public MasterFrame() {
        super("CRAPS - by Alex");
        
        balanceFrame.setBalance(Craps.getBalance());
        
        setLayout(new BorderLayout());
        add(balanceFrame, BorderLayout.NORTH);//NORTH
        add(wagerFrame, BorderLayout.CENTER);//CENTER
        add(mainFrame, BorderLayout.SOUTH);//SOUTH
    }
    
    /**
    * BalanceFrame panel for Craps game, extends JPanel. Contains balance label and
    * text field
    * @author <a href="mailto:alexandre.d.paquette@gmail.com">Alexandre Paquette</a>
    * @date 04/21/2022
    */
    private class BalanceFrame extends JPanel {
        private final JLabel label_Balance = new JLabel("Balance"); //Label
        private JTextField textField_Balance = new JTextField(5); //TextField
        
        /**
        * Constructor for BalanceFrame Panel.
        */
        public BalanceFrame(){
            textField_Balance.setEditable(false);
            textField_Balance.setText("$"+Double.toString(Craps.getBalance()));
            setLayout(new FlowLayout(LEFT));
            add(label_Balance);
            add(textField_Balance);
        }
        
        /**
        * Formats provided balance to currency format
        * @param balance Balance to display in textField_Balance
        */
        public void setBalance(double balance){
            textField_Balance.setText("$" + String.format("%.2f", balance));
        }
        
        /**
        * Returns textField_Balance
        * @return textField_Balance
        */
        public JTextField getTextField_Balance() {
            return textField_Balance;
        }
    }
    
    /**
    * WagerFrame panel for Craps game, extends JPanel. Contains wager label and
    * text field, and bet button
    * @author <a href="mailto:alexandre.d.paquette@gmail.com">Alexandre Paquette</a>
    * @date 04/21/2022
    */
    private class WagerFrame extends JPanel{
        private final JLabel label_Wager = new JLabel("Wager"); //Label
        private JTextField textField_Wager = new JTextField(10); //TextField
        private JButton button_Bet = new JButton("Bet"); //Button to bet
        
        /**
        * Constructor for WagerFrame Panel.
        */
        public WagerFrame(){
            setLayout(new FlowLayout(LEFT));
            add(label_Wager);
            add(textField_Wager);
            add(button_Bet);
            addActionListeners();
        }
        
        /**
        * Returns bet button
        * @return bet button
        */
        public JButton getButton_Bet() {
            return button_Bet;
        }
        
        /**
        * Returns wager text field
        * @return wager text field
        */
        public JTextField getTextField_Wager() {
            return textField_Wager;
        }
        
        /**
        * Enables or disables Bet button and Wager text field
        * @param enable Boolean for enable or disable
        */
        public void enableBetting(boolean enable){
            button_Bet.setEnabled(enable);
            textField_Wager.setEditable(enable);
        }
        
        /**
        * Adds action listeners for WagerFrame objects
        */
        private void addActionListeners(){
            button_Bet.addActionListener((ActionEvent e) -> {
                try{
                    double bet = Double.parseDouble(textField_Wager.getText());
                
                    if(bet <= 0) {//if input is zero or less
                        JOptionPane.showMessageDialog(null, "Please enter a bet with a positive value", "INVALID", JOptionPane.INFORMATION_MESSAGE);
                    }else if(bet > Craps.getBalance()){//if input is higher than balance
                        JOptionPane.showMessageDialog(null, "Please enter a bet equal or less than your balance", "INVALID", JOptionPane.INFORMATION_MESSAGE);
                    }else{//input is valid
                        mainFrame.getButton_Roll().setEnabled(true);
                        enableBetting(false);//disable betting
                        mainFrame.textArea_content.setText("");//empty content area
                        mainFrame.label_Point.setText("");//empty point label
                    }
                }catch(Exception ex){//non numeric value provided
                    JOptionPane.showMessageDialog(null, "Please enter a valid number", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }
    
    /**
    * MainFrame panel for Craps game, extends JPanel. Contains content area, point
    * label, reset and roll buttons
    * @author <a href="mailto:alexandre.d.paquette@gmail.com">Alexandre Paquette</a>
    * @date 04/21/2022
    */
    private class MainFrame extends JPanel{
        private JTextArea textArea_content = new JTextArea(10, 0);
        private JLabel label_Point = new JLabel();
        private JButton button_Reset = new JButton("Reset");
        private JButton button_Roll = new JButton("Roll");
        
        /**
        * Constructor for MainFrame Panel.
        */
        public MainFrame(){
            setLayout(new BorderLayout());
            JScrollPane scroll = new JScrollPane(textArea_content);
            
            add(scroll, BorderLayout.CENTER);
            add(button_Roll, BorderLayout.EAST);
            add(label_Point, BorderLayout.NORTH);
            add(button_Reset, BorderLayout.SOUTH);
            addActionListeners();
            
            button_Roll.setEnabled(false);
            textArea_content.setEditable(false);
        }
        
        /**
        * Get content text area
        * @return content text area 
        */
        public JTextArea getTextArea_content() {
            return textArea_content;
        }
        
        /**
        * Get roll button
        * @return roll button
        */
        public JButton getButton_Roll() {
            return button_Roll;
        }
        
        /**
        * Adds action listeners for MainFrame objects
        */
        private void addActionListeners(){
            GameHandler gameHandler = new GameHandler();//non-anonymous event handler (normally I'd have made it anonymous)
            button_Roll.addActionListener(gameHandler);
            button_Reset.addActionListener((ActionEvent e) -> {
                Craps.resetGame();//reset craps game
                button_Roll.setEnabled(false);//disable dice rolling
                wagerFrame.enableBetting(true);//enable betting
                wagerFrame.textField_Wager.setText("");//empty bet amount
                balanceFrame.setBalance(Craps.getBalance());//reset balance
                textArea_content.setText("");//empty content area
                label_Point.setText("");//empty point label
            });
        }
        
        /**
        * GameHandler action listener for Craps game, extends ActionListener.
        * @author <a href="mailto:alexandre.d.paquette@gmail.com">Alexandre Paquette</a>
        * @date 04/21/2022
        */
        private class GameHandler implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sum = Craps.rollDice(textArea_content);
                
                if(Craps.getRollCount() == 1){//if first roll
                    switch (sum){
                    case Craps.SEVEN: // win with 7 on first roll    
                    case Craps.YO_LEVEN: // win with 11 on first roll
                        Craps.setGameStatus(Craps.Status.WON);
                        break;
                    case Craps.SNAKE_EYES: // lose with 2 on first roll
                    case Craps.TREY: // lose with 3 on first roll      
                    case Craps.BOX_CARS: // lose with 12 on first roll .
                        Craps.setGameStatus(Craps.Status.LOST);
                        break;
                    default: // did not win or lose, so remember point  
                        Craps.setGameStatus(Craps.Status.CONTINUE);//game is not over
                        label_Point.setText("Point is " + sum);
                        textArea_content.setText("Point is " + sum + "\n");
                        Craps.setMyPoint(sum);// remember the point
                        break;
                    }
                }else{//if second or later roll
                    if(sum == Craps.getMyPoint()){
                        Craps.setGameStatus(Craps.Status.WON);
                    }else if(sum == Craps.SEVEN){
                        Craps.setGameStatus(Craps.Status.LOST);
                    }
                }
                
                if(Craps.getGameStatus() != Craps.Status.CONTINUE){//check for win/loss
                    button_Roll.setEnabled(false);//disable roll when winning or losing
                    if(Craps.getGameStatus() == Craps.Status.WON) {
                        label_Point.setText("Player WON!");
                        Craps.addToBalance(Double.parseDouble(wagerFrame.getTextField_Wager().getText()));
                        //Craps.balance += ;
                    }if(Craps.getGameStatus() == Craps.Status.LOST) {
                        label_Point.setText("Player LOST!");
                        Craps.addToBalance(-(Double.parseDouble(wagerFrame.getTextField_Wager().getText())));
                        //Craps.balance -= Double.parseDouble(wagerFrame.getTextField_Wager().getText());
                    }
                    button_Roll.setEnabled(false);//disable dice rolling
                    wagerFrame.enableBetting(true);//enable betting
                    wagerFrame.textField_Wager.setText("");//empty bet amount
                    balanceFrame.setBalance(Craps.getBalance());
                    
                    Craps.resetRollCount();
                    
                    if(Craps.getBalance() <= 0){
                        wagerFrame.enableBetting(false);//disable betting
                        textArea_content.append("Click reset to play again");
                    }
                }
            }
        }
    }
}