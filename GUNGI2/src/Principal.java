import javax.swing.JFrame;
import javax.swing.*;
import java.awt.Container;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.*;
import java.awt.event.*;


/**
 *  Gungi
 *  <p>
 *  Gungi is a oriental board game based in a older (and more simple) game
 *	called shogi, Gungi distinguishes of other board games in the posibility to
 *	make towers of a maximum 3 pieces, and recover troops winning over oponent's troops
 */
public class Principal{

	public static void main(String[] args) {
		Principal_GUI gui = new Principal_GUI();
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gui.setExtendedState(JFrame.MAXIMIZED_BOTH);
  		gui.setVisible(true);
	}
}



class Principal_GUI extends JFrame implements ActionListener{
	MyWorld world;
	ButtonGroup mode_group;
	JRadioButtonMenuItem pvp;
	JRadioButtonMenuItem pvc;
	JRadioButtonMenuItem cvc;
	JCheckBoxMenuItem colorNoIA;
	JMenuItem rules;
	public static int pvcColor = 0;

	public static int gameMode = 0;	//Modo de juego, 0 = pvp; 1 = pvc; 2 = cvc

	
	public Principal_GUI(){
				
		setTitle("Inicio");

		JMenuBar menubar = new JMenuBar();
		setJMenuBar(menubar);
		
		JMenu modo_menu = new JMenu("Modo de Juego");
		JMenu ayuda_menu = new JMenu("Ayuda");
		menubar.add(modo_menu);
		menubar.add(ayuda_menu);

		rules =  new JMenuItem("Reglas");
		ayuda_menu.add(rules);
		rules.addActionListener(this);

		pvp = new JRadioButtonMenuItem("Jugador vs Jugador", true);
		pvc = new JRadioButtonMenuItem("Jugador vs IA", false);
		cvc = new JRadioButtonMenuItem("IA vs IA", false);

		colorNoIA = new JCheckBoxMenuItem("Color del Jugador",true);
		colorNoIA.setOpaque(true);
		colorNoIA.setBackground(Color.BLACK);

		modo_menu.add(pvp);
		modo_menu.addSeparator();
		modo_menu.add(pvc);
		modo_menu.add(colorNoIA);
		modo_menu.addSeparator();
		modo_menu.add(cvc);
		
		mode_group = new ButtonGroup();
		mode_group.add(pvp);
		mode_group.add(pvc);
		mode_group.add(cvc);

		pvp.addActionListener(this);
		pvc.addActionListener(this);
		cvc.addActionListener(this);
		colorNoIA.addActionListener(this);

		setSize(MyWorldView.WIDTH, MyWorldView.HEIGHT); 

		world = new MyWorld();
		MyWorldView  worldView = new MyWorldView(world);
		
		world.setView(worldView);
		add(worldView);
		
		createConfiguration(world);

		for (int i=0; i<9; i++) {
			Pawn ficha =  new Pawn(0, 0, -1, -1, 0);// crea un peon negro
			world.addElement(ficha);
			world.hand0.add(ficha);
			MyWorld.fichasIA_0.add(ficha);
			ficha =  new Pawn(0, 0, -1, -1, 1);// crea un peon rojo
			world.addElement(ficha);
			world.hand1.add(ficha);
			MyWorld.fichasIA_1.add(ficha);
		}
		for (int i=0;i<3;i++){
			Spy ficha4 =  new Spy(0, 0, -1, -1, 0);// crea un espia negro
			world.addElement(ficha4);
			world.hand0.add(ficha4);
			MyWorld.fichasIA_0.add(ficha4);
			ficha4 =  new Spy(0, 0, -1, -1, 1);// crea un peon rojo
			world.addElement(ficha4);
			world.hand1.add(ficha4);
			MyWorld.fichasIA_1.add(ficha4);
		}
		for (int i=0;i<2;i++){
			Bow ficha3 =  new Bow(0, 0, -1, -1, 0);// crea un arquero negro
			world.addElement(ficha3);
			world.hand0.add(ficha3);
			MyWorld.fichasIA_0.add(ficha3);
			ficha3 =  new Bow(0, 0, -1, -1, 1);// crea un arquero rojo
			world.addElement(ficha3);
			world.hand1.add(ficha3);
			MyWorld.fichasIA_1.add(ficha3);
		}
		for (int i=0;i<2;i++){
			Samurai ficha2 =  new Samurai(0, 0, -1, -1, 0);// crea un samurai negro
			world.addElement(ficha2);
			world.hand0.add(ficha2);
			MyWorld.fichasIA_0.add(ficha2);
			ficha2 =  new Samurai(0, 0, -1, -1, 1);// crea un samurai rojo
			world.addElement(ficha2);
			world.hand1.add(ficha2);
			MyWorld.fichasIA_1.add(ficha2);
		}
		for (int i=0;i<2;i++){
			Captain ficha8 =  new Captain(0, 0, -1, -1, 0);// crea una fortaleza negro
			world.addElement(ficha8);
			world.hand0.add(ficha8);
			MyWorld.fichasIA_0.add(ficha8);
			ficha8 =  new Captain(0, 0, -1, -1, 1);// crea un peon rojo
			world.addElement(ficha8);
			world.hand1.add(ficha8);
			MyWorld.fichasIA_1.add(ficha8);
		}
		HiddenDragon ficha7 =  new HiddenDragon(0, 0, -1, -1, 0);// crea un hidden dragon negro
		world.addElement(ficha7);
		world.hand0.add(ficha7);
		MyWorld.fichasIA_0.add(ficha7);
		ficha7 =  new HiddenDragon(0, 0, -1, -1, 1);// crea un peon rojo
		world.addElement(ficha7);
		world.hand1.add(ficha7);
		MyWorld.fichasIA_1.add(ficha7);

		Prodigy ficha9 =  new Prodigy(0, 0, -1, -1, 0);// crea un hidden dragon negro
		world.addElement(ficha9);
		world.hand0.add(ficha9);
		MyWorld.fichasIA_0.add(ficha9);
		ficha9 =  new Prodigy(0, 0, -1, -1, 1);// crea un peon rojo
		world.addElement(ficha9);
		world.hand1.add(ficha9);
		MyWorld.fichasIA_1.add(ficha9);

		Fortress ficha5 =  new Fortress(0, 0, -1, -1, 0);// crea una fortaleza negro
		world.addElement(ficha5);
		world.hand0.add(ficha5);
		MyWorld.fichasIA_0.add(ficha5);
		ficha5 =  new Fortress(0, 0, -1, -1, 1);// crea un peon rojo
		world.addElement(ficha5);
		world.hand1.add(ficha5);
		MyWorld.fichasIA_1.add(ficha5);

		Catapult ficha10 =  new Catapult(0, 0, -1, -1, 0);// crea una fortaleza negro
		world.addElement(ficha10);
		world.hand0.add(ficha10);
		MyWorld.fichasIA_0.add(ficha10);
		ficha10 =  new Catapult(0, 0, -1, -1, 1);// crea un peon rojo
		world.addElement(ficha10);
		world.hand1.add(ficha10);
		MyWorld.fichasIA_1.add(ficha10);

		Commander ficha6 =  new Commander(0, 0, -1, -1, 0);// crea una fortaleza negro
		world.addElement(ficha6);
		world.hand0.add(ficha6);
		MyWorld.fichasIA_0.add(ficha6);
		MyWorld.target1 = ficha6;
		ficha6 =  new Commander(0, 0, -1, -1, 1);// crea un peon rojo
		world.addElement(ficha6);
		world.hand1.add(ficha6);
		MyWorld.fichasIA_1.add(ficha6);
		MyWorld.target0 = ficha6;

		world.ordenar(world.hand0);
		world.ordenar(world.hand1);

		world.repaintView();
		world.start();		
	}

	
	private void createConfiguration(MyWorld world) {
		PlayerMenu player = new PlayerMenu(0, 1, 10, MyWorldView.HEIGHT-314);
		PlayerMenu player2 = new PlayerMenu(1, 0, MyWorldView.WIDTH-304, 10);
		InfoView infov = new InfoView(0);
		world.addElement(player);
		world.addElement(player2);
		world.setInfo(infov);
		
		for (int i=0;i<9;i++){
			for (int j=0;j<9;j++) {
				Vector2D vec = new Vector2D(i,j);
				BoardPanel b = new BoardPanel( (MyWorldView.WIDTH -630)/2 + 70*j, (MyWorldView.HEIGHT - 630)/2 + 70*i, vec);
				world.addElement(b);
			}
		}
   	}

   	
   	public void actionPerformed(ActionEvent e) {
		JMenuItem source = (JMenuItem)(e.getSource());
		int prev_gameMode = gameMode;
		if (MyWorldView.ficha_move==null){
			if (source.getText() == "Jugador vs Jugador"){
				if(JOptionPane.showOptionDialog(this, "Nuevo Juego de Jugador vs Jugador. Seguro?", "Nuevo Juego", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{" SI "," NO "},"NO")==0){
					gameMode = 0;
					world.reset(gameMode, pvcColor);
				}
				else{
					switch(prev_gameMode){
						case 0:{
							mode_group.clearSelection();
							pvp.setSelected(true);
							break;
						}
						case 1:{
							mode_group.clearSelection();
							pvc.setSelected(true);
							break;
						}
						case 2:{
							mode_group.clearSelection();
							cvc.setSelected(true);
							break;
						}
					}
				}
			}
			else if (source.getText() == "Jugador vs IA"){
				if(JOptionPane.showOptionDialog(this, "Nuevo Juego de Jugador vs IA. Seguro?", "Nuevo Juego", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{" SI "," NO "},"NO")==0){
					gameMode = 1;
					world.reset(gameMode, pvcColor);
				}
				else{
					switch(prev_gameMode){
						case 0:{
							mode_group.clearSelection();
							pvp.setSelected(true);
							break;
						}
						case 1:{
							mode_group.clearSelection();
							pvc.setSelected(true);
							break;
						}
						case 2:{
							mode_group.clearSelection();
							cvc.setSelected(true);
							break;
						}
					}
				}
			}
			else if (source.getText() == "IA vs IA"){
				if(JOptionPane.showOptionDialog(this, "Nuevo Juego de IA vs IA. Seguro?", "Nuevo Juego", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[]{" SI "," NO "},"NO")==0){
					gameMode = 2;
					world.reset(gameMode, 1);
				}
				else{
					switch(prev_gameMode){
						case 0:{
							mode_group.clearSelection();
							pvp.setSelected(true);
							break;
						}
						case 1:{
							mode_group.clearSelection();
							pvc.setSelected(true);
							break;
						}
						case 2:{
							mode_group.clearSelection();
							cvc.setSelected(true);
							break;
						}
					}
				}
			}
			else if (source.getText() == "Color del Jugador"){
				if (Principal_GUI.pvcColor == 0) {
					Principal_GUI.pvcColor = 1;
					colorNoIA.setBackground(Color.RED);
				}
				else if (Principal_GUI.pvcColor == 1) {
					Principal_GUI.pvcColor = 0;
					colorNoIA.setBackground(Color.BLACK);
				}
			}
			else if (source.getText() == "Reglas"){
				JFrame reglas_window = new JFrame("Reglas");
				reglas_window.setVisible(true);
				reglas_window.setBounds(0,0,1024,MyWorldView.HEIGHT);
				reglas_window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				
				JPanel container = new JPanel();
		        ImageIcon image = new ImageIcon("src/Rules.png");
				JLabel label = new JLabel("", image, JLabel.CENTER);

				JScrollPane scrPane = new JScrollPane(container);
				container.add( label, BorderLayout.CENTER );
		        reglas_window.getContentPane().add(scrPane);
			}
		}
	}
}
