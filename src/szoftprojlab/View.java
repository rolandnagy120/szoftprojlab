package szoftprojlab;

import szoftprojlab.entity.Alien;
import szoftprojlab.entity.Player;
import szoftprojlab.entity.Robot;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.BevelBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class View extends JFrame{
    private JPanel fieldPanel;

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

        JButton btnNewButton_1 = new JButton("Drill");
        btnNewButton_1.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton_1.setVerticalAlignment(SwingConstants.TOP);
        btnNewButton_1.addActionListener(e -> Main.AddCommand("Drill"));

        JButton btnNewButton = new JButton("Move");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton.addActionListener(e -> Main.AddCommand("start move"));

        JButton btnNewButton_2 = new JButton("CraftRobot");
        btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton_2.addActionListener(e -> Main.AddCommand("craft robot"));

        JButton btnNewButton_3 = new JButton("CraftGate");
        btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton_3.addActionListener(e -> Main.AddCommand("craft gates"));

        JButton btnNewButton_6 = new JButton("Mine");
        btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton_6.addActionListener(e -> Main.AddCommand("mine"));

        JList list = new JList();
        list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JLabel lblNewLabel = new JLabel("Inventory:");

        JLabel lblNewLabel_1 = new JLabel("Player Name");

        JList list_1 = new JList();
        list_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JLabel lblNewLabel_2 = new JLabel("Events:");

        fieldPanel = new JPanel();
        fieldPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JButton btnNewButton_7 = new JButton(" Place");
        btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });

        JList list_2 = new JList();
        list_2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

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
                                                                .addComponent(btnNewButton_7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_3, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(list, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_1, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
                                                                .addComponent(btnNewButton_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
                                                        .addGap(10))
                                                .addGroup(gl_panel_1.createSequentialGroup()
                                                        .addComponent(list_2, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                                                        .addPreferredGap(ComponentPlacement.RELATED)))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblNewLabel_3)
                                                .addPreferredGap(ComponentPlacement.RELATED)))
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                                        .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(fieldPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(list_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)))
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
                                                .addGap(68)
                                                .addComponent(lblNewLabel_3)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(list_2, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(lblNewLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(list, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addPreferredGap(ComponentPlacement.RELATED))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(fieldPanel, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(lblNewLabel_2)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(list_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
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

        setSize(900, 660);
        setVisible(true);

        setAlwaysOnTop(true);
        setResizable(false);
    }

    private int fieldWidth = 665;
    private int fieldHeight = 370;
//    private int fieldWidth = 735;
//    private int fieldHeight = 430;

    private Random rnd = new Random();

    public void clearField() {
        fieldPanel.repaint();
    }

    private void drawImageAtRandom(String path) {
        int x = rnd.nextInt(fieldWidth);
        int y = rnd.nextInt(fieldHeight);
        drawImageAt(path, x, y);
    }

    private void drawImageAt(String path, int x, int y) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            fieldPanel.getGraphics().drawImage(image, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawAsteroid(Asteroid a) {
        int x = a.GetX();
        int y = a.GetY();
        drawImageAt("assets/asteroid.png", x, y);
    }

    public void drawPlayer(Player p, boolean active) {
        String imgPath = active ? "assets/spaceship_outlined.png" : "assets/spaceship.png";
        drawImageAtRandom(imgPath);
    }

    public void update(Player activePlayer) {
        fieldPanel.repaint();

        Game game = Game.getInstance();
        for (int i = 0; i < numberOfAsteroids; i++) {
            game.drawRequiredForAsteroid(i);
        }
    }

    public void drawTeleportGate() {
        drawImageAtRandom("assets/teleport_gate.png");
    }

    public void drawRobot(Robot robot) {
        drawImageAtRandom("assets/robot.png");
    }

    public void drawAlien(Alien alien) {
        drawImageAtRandom("assets/alien.png");
    }
}