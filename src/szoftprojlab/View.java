package szoftprojlab;

import szoftprojlab.entity.Alien;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;
import szoftprojlab.resource.Resource;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class View extends JFrame{
    private JPanel fieldPanel;
    private DefaultListModel inventoryListModel = new DefaultListModel();
    private DefaultListModel eventListModel = new DefaultListModel();
    private JList eventList;
    private DefaultListModel gatesListModel = new DefaultListModel();
    private JList gatesList;

    private int numberOfAsteroids = 0;

    public View(int asteroidCount) {
        numberOfAsteroids = asteroidCount;

        setTitle("Asteroid Miner");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel = new JPanel();
        getContentPane().add(panel);
        panel.setLayout(new GridLayout(1, 0, 0, 0));

        JPanel panel_1 = new JPanel();
        panel.add(panel_1);
        
        Font font = new Font("Tahoma", Font.PLAIN, 10);

        JButton btnNewButton_1 = new JButton("Drill");
        btnNewButton_1.setFont(font);
        btnNewButton_1.setVerticalAlignment(SwingConstants.TOP);
        btnNewButton_1.addActionListener(e -> Main.AddCommand("Drill"));

        JButton btnNewButton = new JButton("Move");
        btnNewButton.setFont(font);
        btnNewButton.addActionListener(e -> Main.AddCommand("listen for move"));

        JButton btnNewButton_2 = new JButton("CraftRobot");
        btnNewButton_2.setFont(font);
        btnNewButton_2.addActionListener(e -> Main.AddCommand("craft robot"));

        JButton btnNewButton_3 = new JButton("CraftGate");
        btnNewButton_3.setFont(font);
        btnNewButton_3.addActionListener(e -> Main.AddCommand("craft gates"));

        JButton btnNewButton_6 = new JButton("Mine");
        btnNewButton_6.setFont(font);
        btnNewButton_6.addActionListener(e -> Main.AddCommand("mine"));

        JScrollPane inventoryListScroller = createInventoryScroller();
        JScrollPane eventListScroller = createEventScroller();
        JScrollPane gatesListScroller = gatesScroller();

        JLabel lblNewLabel = new JLabel("Inventory:");

        JLabel lblNewLabel_1 = new JLabel("Player Name");

        JLabel lblNewLabel_2 = new JLabel("Events:");

        fieldPanel = new JPanel();
        fieldPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JButton btnNewButton_7 = new JButton("Place resource");
        btnNewButton_7.setFont(font);
        btnNewButton_7.addActionListener(arg0 -> {
            JOptionPane.showMessageDialog(fieldPanel, "Select a resource from your inventory to place");
            Main.AddCommand("listen for place");
        });

        JButton placeGateButton = new JButton("Place gate");
        placeGateButton.setFont(font);
        placeGateButton.addActionListener(arg0 -> {
            JOptionPane.showMessageDialog(fieldPanel, "Select a gate from your gates to place");
            Main.AddCommand("listen for gate place");
        });

        JLabel lblNewLabel_3 = new JLabel("Gates:");
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                                .addGroup(gl_panel_1.createSequentialGroup()
                                                        .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                                                .addComponent(placeGateButton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(inventoryListScroller, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(10))
                                                .addGroup(gl_panel_1.createSequentialGroup()
                                                        .addComponent(gatesListScroller, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                                        .addPreferredGap(ComponentPlacement.RELATED)))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblNewLabel_3)
                                                .addPreferredGap(ComponentPlacement.RELATED)))
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(fieldPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(eventListScroller, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addGap(8)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblNewLabel_1)
                                                .addGap(9)
                                                .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_6, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_7, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(placeGateButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addGap(68)
                                                .addComponent(lblNewLabel_3)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(gatesListScroller, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(lblNewLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(inventoryListScroller, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(ComponentPlacement.RELATED))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(fieldPanel, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(lblNewLabel_2)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(eventListScroller, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
                                .addGap(11))
        );
        panel_1.setLayout(gl_panel_1);

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("File");
        menuBar.add(mnNewMenu);

        JMenuItem mntmNewMenuItem_1 = new JMenuItem("Save");
        mnNewMenu.add(mntmNewMenuItem_1);

        JMenuItem mntmNewMenuItem = new JMenuItem("Load");
        mnNewMenu.add(mntmNewMenuItem);

        JMenuItem mntmNewMenuItem_2 = new JMenuItem("Quit");
        mnNewMenu.add(mntmNewMenuItem_2);
        mntmNewMenuItem_2.addActionListener(e -> Runtime.getRuntime().exit(0));


        fieldPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Game game = Game.getInstance();
                int clickedAsteroid = game.GetClickedAsteroidId(e.getX(), e.getY());
                if (clickedAsteroid != -1)
                    Main.AddCommand("move " + clickedAsteroid);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }
        });

        setSize(900, 660);
        setVisible(true);

        setAlwaysOnTop(true);
        setResizable(false);
    }

    /**
     * Creates and initializes the player inventory scrollabel list
     * @return  the scroll pane
     */
    private JScrollPane createInventoryScroller() {
        JList inventoryList = new JList(inventoryListModel);
        inventoryList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        JScrollPane inventoryListScroller = new JScrollPane(inventoryList);
        inventoryListScroller.setPreferredSize(new Dimension(250, 80));

        inventoryList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() > 0) {
                    int index = list.locationToIndex(evt.getPoint());
                    Main.AddCommand("place resource " + index);
                }
            }
        });

        return inventoryListScroller;
    }

    /**
     * Creates and initializes the event scrollabel list
     * @return  the scroll pane
     */
    private JScrollPane createEventScroller() {
        eventList = new JList(eventListModel);
        eventList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        JScrollPane eventListScroller = new JScrollPane(eventList);
        eventListScroller.setPreferredSize(new Dimension(250, 80));
        return eventListScroller;
    }

    /**
     * Creates and initializes the gates scrollable list
     * @return  the scroll pane
     */
    private JScrollPane gatesScroller() {
        gatesList = new JList(gatesListModel);
        gatesList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        JScrollPane gatesListScroller = new JScrollPane(gatesList);
        gatesListScroller.setPreferredSize(new Dimension(250, 80));

        gatesList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() > 0) {
                    int index = list.locationToIndex(evt.getPoint());
                    Main.AddCommand("place gate " + index);
                }
            }
        });

        return gatesListScroller;
    }

    private int fieldWidth = 735;
    private int fieldHeight = 430;

    private Random rnd = new Random();

    private Asteroid currentAsteroid = null;
    private int asteroidThingCount = 0;

    private int playerSize = 50;
    private int robotSize = 50;
    private int alienSize = 50;
    private int gateSize = 50;

    /**
     * Draw image to the field planel
     * @param path  path of the image
     * @param x     x position in the panel
     * @param y     y position in the panel
     * @param w     drawn image width
     * @param h     drawn image height
     */
    private void drawImageAt(String path, int x, int y, int w, int h) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            fieldPanel.getGraphics().drawImage(image, x, y + asteroidThingCount++ * Asteroid.size, w, h, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Draw asteroid to the screen
     * @param a the asteroid that will be drawn
     */
    public void drawAsteroid(Asteroid a) {
        currentAsteroid = a;
        asteroidThingCount = 0;

        int x = a.GetX();
        int y = a.GetY();

        String path = a.GetCurrentPicture();

        drawImageAt(path, x, y, Asteroid.size, Asteroid.size);
    }

    /**
     * Draw the player to the screen,
     * The player will be around the asteroid it is on
     * @param p         the player that will be drawn
     * @param active    is the player currently active
     */
    public void drawPlayer(Player p, boolean active) {
        String imgPath = active ? "assets/spaceship_outlined.png" : "assets/spaceship.png";
        int x = currentAsteroid.GetX();
        int y = currentAsteroid.GetY();
        drawImageAt(imgPath, x, y, playerSize, playerSize);

        if (active) {
            drawPlayerInventory(p);
            drawPlayerGates(p);
        }
    }

    /**
     * Shows the given player's inventory on the screen
     * @param player the current player
     */
    private void drawPlayerInventory(Player player) {
        inventoryListModel.removeAllElements();
        List<Resource> inventory = player.GetInventory();
        inventory.forEach(item -> inventoryListModel.addElement(item.getClass().getSimpleName()));
    }

    /**
     * Shows the given player's gates on the screen
     * @param player the current player
     */
    private void drawPlayerGates(Player player) {
        gatesListModel.removeAllElements();
        List<TeleportGate> gates = player.GetTeleportGates();
        gates.forEach(gate -> {
            String s = "Gate " + gate.GetId() + " (Pair: " + gate.GetPairId() + ")";
            gatesListModel.addElement(s);
        });
    }

    /**
     * Draw the game current state to the field panel
     * @param activePlayer
     */
    public void update(Player activePlayer) {
        fieldPanel.getGraphics().clearRect(3, 3, fieldWidth, fieldHeight);

        Game game = Game.getInstance();
        for (int i = 0; i < numberOfAsteroids; i++) {
            game.drawRequiredForAsteroid(i);
        }
    }

    /**
     * Draw a teleport gate to the screen
     * The gate will be around the current asteroid
     */
    public void drawTeleportGate() {
        int x = currentAsteroid.GetX();
        int y = currentAsteroid.GetY();
        drawImageAt("assets/teleport_gate.png", x, y, gateSize, gateSize);
    }

    /**
     * Draw the given robot to the screen
     * The robot will be around the asteroid it is on
     * @param robot the robot that will be drawn
     */
    public void drawRobot(Robot robot) {
        int x = currentAsteroid.GetX();
        int y = currentAsteroid.GetY();
        drawImageAt("assets/robot.png", x, y, robotSize, robotSize);
    }

    /**
     * Draw the given alien to the screen
     * The alien will be around the asteroid it is on
     * @param alien the alien that will be drawn
     */
    public void drawAlien(Alien alien) {
        int x = currentAsteroid.GetX();
        int y = currentAsteroid.GetY();
        drawImageAt("assets/alien.png", x, y, alienSize, alienSize);
    }

    /**
     * Show a popup dialog with game won message, and close the program after the dialog is closed
     */
    public void showGameWonDialog() {
        showMessageAndExit("The base can be crafted, you won!");
    }

    /**
     * Show a popup dialog with game over message, and close the program after the dialog is closed
     */
    public void showGameOverDialog() {
        showMessageAndExit("Game over!");
    }

    /**
     * Show the given message in a dialog, and close the program after the dialog is closed
     * @param message   the message shown in the dialog
     */
    private void showMessageAndExit(String message) {
        JOptionPane.showMessageDialog(this,
                message);
        Runtime.getRuntime().exit(0);
    }

    /**
     * Writes the given string to the event list,
     * and scrolls to the bottom, so the new events
     * are seen
     * @param s the string that needs to be written out
     */
    public void WriteEvent(String s) {
        String[] lines = s.split("\n");

        for (String line : lines) {
            eventListModel.addElement(line);
        }

        int lastIndex = eventListModel.getSize() - 1;
        if (lastIndex >= 0 && eventList != null) {
            eventList.ensureIndexIsVisible(lastIndex);
        }
    }
}