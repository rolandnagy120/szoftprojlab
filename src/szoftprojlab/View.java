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

        JButton btnNewButton = new JButton("Move");
        btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });

        JButton btnNewButton_2 = new JButton("CreateRobot");
        btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 10));

        JButton btnNewButton_3 = new JButton("CreateGate");
        btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 10));

        JButton btnNewButton_4 = new JButton("PlaceRobot");
        btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 10));

        JButton btnNewButton_5 = new JButton("PlaceGate");
        btnNewButton_5.setFont(new Font("Tahoma", Font.PLAIN, 10));

        JButton btnNewButton_6 = new JButton("Mine");
        btnNewButton_6.setFont(new Font("Tahoma", Font.PLAIN, 10));

        JList list = new JList();
        list.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JLabel lblNewLabel = new JLabel("Inventory:");

        JLabel lblNewLabel_1 = new JLabel("Player Name");

        JList list_1 = new JList();
        list_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JLabel lblNewLabel_2 = new JLabel("Events:");

        fieldPanel = new JPanel();
        fieldPanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));

        JButton btnNewButton_7 = new JButton(" Place Resource");
        btnNewButton_7.setFont(new Font("Tahoma", Font.PLAIN, 10));
        btnNewButton_7.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
            }
        });
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addComponent(list, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewButton_2)
                                        .addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewButton_5, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewButton_6, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewButton_7, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
                                .addGap(18)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
                                                .addGap(696))
                                        .addGroup(Alignment.LEADING, gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
                                                .addComponent(fieldPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(list_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 742, Short.MAX_VALUE)))
                                .addContainerGap())
        );
        gl_panel_1.setVerticalGroup(
                gl_panel_1.createParallelGroup(Alignment.LEADING)
                        .addGroup(gl_panel_1.createSequentialGroup()
                                .addGap(8)
                                .addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(lblNewLabel_1)
                                                .addGap(9)
                                                .addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_2, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_4, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_5, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addGap(4)
                                                .addComponent(btnNewButton_6, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_7, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(btnNewButton_1, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
                                                .addGap(113)
                                                .addComponent(lblNewLabel)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(list, GroupLayout.PREFERRED_SIZE, 195, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(gl_panel_1.createSequentialGroup()
                                                .addComponent(fieldPanel, GroupLayout.PREFERRED_SIZE, 437, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(ComponentPlacement.RELATED)
                                                .addComponent(lblNewLabel_2)
                                                .addPreferredGap(ComponentPlacement.UNRELATED)
                                                .addComponent(list_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
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

        setSize(900, 660);
        setVisible(true);

        setAlwaysOnTop(true);
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
        try {
            BufferedImage image = ImageIO.read(new File(path));
            int x = rnd.nextInt(fieldWidth);
            int y = rnd.nextInt(fieldHeight);
            fieldPanel.getGraphics().drawImage(image, x, y, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void drawAsteroid(Asteroid a) {
        drawImageAtRandom("assets/asteroid.png");
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