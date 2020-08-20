import java.util.*;
import java.util.concurrent.TimeUnit;
import java.io.*;
import java.io.File;
import javax.swing.Timer;
import java.awt.event.*;
import java.net.URL;
import javax.sound.sampled.*;
import java.lang.*;




public class MyWorld implements ActionListener{
    //listas
	private ArrayList<GameObject> elements;  
	private static ArrayList<BoardPanel> paneles;
    private static ArrayList<BoardPanel> moves;

    public static ArrayList<GameObject> hand0;  // mano del jugador 0 tamanno maximo de 23
    public static ArrayList<GameObject> hand1;  // mano del jugador 1 tamanno maximo de 23

    public static ArrayList<GameObject> fichasIA_0;
    public static ArrayList<GameObject> fichasIA_1;

    //variables
	private Timer passingTime;                  // maneja los steps
	private double refreshPeriod;               // tiempo que transcurre entre cada step, medido en segundos
	private MyWorldView view;                   // manejador grafico
    public InfoView info;                      // inforamacion de ficha
	public static int player_color = 0;               // manejador de turno 0=negro 1=rojo
    public static int modoJuego = 0;           //Modo de juego, 0 = pvp; 1 = pvc; 2 = cvc
    public static int pvcIA = 1;               // Jugador que sera reemplazado por IA en pvc

    public Random rnd = new Random();           //azar de IA
    public static Commander target0 = null;            //objetivo a perseguir del jugador IA0
    public static Commander target1 = null;            //objetivo a perseguir del jugador IA1
    private double think = 0.7;                    //tiempo que piensa la IA en segundos
    private double cont_t = 0;                   //cuanto tiempo ha pasado pensando la IA
    public static int slowed = 1;               //para evitar que todo el programa se vuelva lento con interrupciones de IA
	public static int game_phase = 0;                 // manejador de fase de juego 0=fase inicial
    public static BoardPanel back_board = null; //para devolver la fich a su posicion anteriors
    private static int[] peon_fila = {0,0,0,0,0,0,0,0,0,
                                      0,0,0,0,0,0,0,0,0};   //primeros 9 valores son de fichas rojas
    private Clip backMusic = null;
    private static int sonando = 0;             //chequea si esta sonando backMusic

    /**
    * Constructor for MyWorld
    * Iniciates the lists and the Timer that runs the game
    */
   	public MyWorld(){
		refreshPeriod = 0.03;                     // cada 0.03 segundos habra un referesco de pantalla
		elements = new ArrayList<GameObject>();   // se inician las listas
		paneles = new ArrayList<BoardPanel>();
        moves = new ArrayList<BoardPanel>();
        hand0 = new ArrayList<GameObject>();
        hand1 = new ArrayList<GameObject>();
        fichasIA_0 = new ArrayList<GameObject>();
        fichasIA_1 = new ArrayList<GameObject>();

		view = null;
		passingTime = new Timer((int) (refreshPeriod*1000) ,this);
	}
    
    /**
    *   Adds an element (Game object) to the list of objects in the world
    *   @param e game object to add
    */
	public void addElement(GameObject e) {
		elements.add(e);
		view.repaint();
	}

    /**
    * Adds a panel to the list of panels in the game
    * @param bp board panel to add to the list
    */
	public static void addElement(BoardPanel bp) {
		paneles.add(bp);
	}
    
    /**
    *   Sets the graphics handler
    *   @param view MyWorldView is the class that manages the graphics
    */
	public void setView(MyWorldView view) {
		this.view = view;
	}


    /**
    *   Sets the Info View a class to display info about the movement of the pieces
    *   @param iv Infoview handler
    */
    public void setInfo(InfoView iv){
        info = iv;
    }

    /**
    *   Method to iniciate the Timer and the program
    */
	public void start() {
		if(passingTime.isRunning()) return;
		passingTime.start();
//        if(sonando==0){
//            this.backSound();
//            sonando = 1;
//        }
	}

    
    public void actionPerformed (ActionEvent event) {
      	int mouse_x = MyWorldView.x;     //se guarda la variable x del mouse
		int mouse_y = MyWorldView.y;     //se guarda la variable y del mouse
//        if (backMusic!=null){
//            if (backMusic.isRunning()==false){
//                this.backSound();
//            }
//        }

        /*
        se recorre la lista de jugadores:
        y se verifican los efectos de expansion de rango de las piezas inmobiles
        */
		for (int i=0; i<elements.size();i++){
        	GameObject ele = elements.get(i);
        	if (ele instanceof PlayerMenu){
        		PlayerMenu pm = (PlayerMenu)ele;
                pm.updateImg();
        	}
            else if (ele instanceof InfoView){
                InfoView iv = (InfoView)ele;
            }
        }

        /*
        se recorre la lista de baldosas:
        */
    	for (int i=0; i<paneles.size();i++){

    		BoardPanel bbpp = paneles.get(i);         //obtenemos un panel del tablero
    		int pan_x = bbpp.getPosition().getX();    //obtenemos la posicion x e y del panel
    		int pan_y = bbpp.getPosition().getY();    
            int tier_size = bbpp.getTierSize();       //obtenemos el tamanno de la torre que sostiene el panel

            //si estamos dentro de la baldosa seleccionada
    		if (mouse_x>=pan_x && mouse_x<pan_x+70 && mouse_y>=pan_y && mouse_y<pan_y+70 && MyWorld.game_phase!=3){
                //si se hace click derecho en la baldosa mostramos la info de la ficha que se clickeo
                if (MyWorldView.click_derecho==1){
                    switch(tier_size){
                        case 0:{
                            info.updateImg(0,0,null);
                            break;
                        }
                        case 1: {
                            info.setPosition(new Vector2D(pan_x+70, pan_y-60));
                            info.updateImg(0,1, bbpp.getTier().get(0) );
                            break;
                        }
                        case 2: {
                            info.setPosition(new Vector2D(pan_x+70, pan_y-60));
                            if (mouse_y>pan_y+60){
                                info.updateImg(0,1, bbpp.getTier().get(0) );
                            }
                            else {
                                info.updateImg(1,1, bbpp.getTier().get(1) );
                            }
                            break;
                        }
                        case 3: {
                            info.setPosition(new Vector2D(pan_x+70, pan_y-60));
                            if (mouse_y>pan_y+60){
                                info.updateImg(0,1, bbpp.getTier().get(0) );
                            }
                            else if (mouse_y>pan_y+50) {
                                info.updateImg(1,1, bbpp.getTier().get(1) );
                            }
                            else
                            {
                                info.updateImg(2,1, bbpp.getTier().get(2) );
                            }
                            break;
                        }
                    }

                    MyWorldView.click_derecho = 0;
                }
                //si se hace click izquierdo en la baldosa y esta jugando un usuario
                if(MyWorldView.click_izquierdo==1 && ( modoJuego==0 || (modoJuego==1 && pvcIA!=player_color)) ){
                    //mientras tenemos una ficha en movimiento,(movimiento dentro del juego)
                    if (MyWorldView.ficha_move!=null){
                        Pawn ppp = (Pawn)MyWorldView.ficha_move;    //la ficha en movimiento
                        Pawn top_bp = null;                         //la ultima ficha de la torre en esta baldosa
                        //si existe alguna ficha en la baldosa
                        if(tier_size>0){
                            top_bp = (Pawn)bbpp.getTier().get(tier_size-1);
                        }

                        //si los movimientos de la ficha permiten jugarla en esta baldosa
                        if (moves.contains(bbpp)){
                            //verificamos si nos comemos una ficha oponente
                            if (top_bp!=null){
                                if (top_bp.player_color!=ppp.player_color && top_bp.getClass()!=Fortress.class && top_bp.getClass()!=Catapult.class){

                                    //nos comemos la fichaa oponente
                                    top_bp.in_play = 0;
                                    //y la movemos a nuestra mano

                                    bbpp.removeTier(tier_size-1);
                                    tier_size-=1;
                                    top_bp.getOut();
                                    if (top_bp.getClass() == Commander.class){
                                        MyWorld.game_phase = 3;
                                        MyWorldView.win =  MyWorld.player_color;
                                    }
                                    putPiece(ppp, bbpp, pan_x, pan_y-8*tier_size, tier_size,1);
                                         

                                    bbpp.updateImg(0);
                                    back_board.updateImg(0);
                                    back_board = null;
                                    moves.clear();

                                    //y terminamos nuestro turno
                                    this.turnEnd(MyWorld.player_color);
                                }
                                else if (top_bp.player_color==ppp.player_color){
                                    this.putPiece(ppp, bbpp, pan_x, pan_y-8*tier_size, tier_size,1);

                                    bbpp.updateImg(0);
                                    back_board.updateImg(0);
                                    back_board = null;
                                    moves.clear();

                                    //y terminamos nuestro turno
                                    this.turnEnd(MyWorld.player_color);
                                }
                            }
                            else{
                                //posicionamos nuestra ficha en la baldosa
                                this.putPiece(ppp, bbpp, pan_x, pan_y-8*tier_size, tier_size,1);

                                bbpp.updateImg(0);
                                back_board.updateImg(0);
                                back_board = null;
                                moves.clear();

                                //y terminamos nuestro turno
                                this.turnEnd(MyWorld.player_color);
                            }
                        }
                        //si queremos hacer entrar una ficha al juego
                        else if(MyWorldView.droping==1){
                            if ( (MyWorld.game_phase==0 && ( (MyWorld.player_color==0 && bbpp.getRow()>5) || (MyWorld.player_color==1 && bbpp.getRow()<3) ) ) || MyWorld.game_phase==1 ){
                                if (tier_size<3){
                                    top_bp = null;
                                    //si hay fichas en la baldosa
                                    if(tier_size>0){
                                        top_bp = (Pawn)bbpp.getTier().get(tier_size-1);
                                        ppp = (Pawn)MyWorldView.ficha_move;

                                        if (MyWorld.game_phase==0){
                                            //si la intentamos dejar sobre una ficha propia (permitido)
                                            if (top_bp.player_color==ppp.player_color && ppp.getClass()!=Fortress.class && ppp.getClass()!=Catapult.class && top_bp.getClass()!=Commander.class ){
                                                //si es peon verificamos la columna, para que no haya mas peones
                                                if (MyWorld.game_phase==0 && ppp.getClass()==Pawn.class && peon_fila[bbpp.getColumn()+9*ppp.player_color]==0){
                                                    //posicionamos la ficha
                                                    this.putPiece(ppp, bbpp, pan_x, pan_y-8*tier_size, tier_size,1);
                                                    ppp.in_play = 1;
                                                    MyWorldView.droping = 0;
                                                    //reordenamos las manos de los jugadores
                                                    if (ppp.player_color==0){
                                                        ordenar(hand0);
                                                    }
                                                    else{
                                                        ordenar(hand1);
                                                    }
                                                    //y terminamos nuestro turno
                                                    this.turnEnd(MyWorld.player_color);
                                                    //añadimos el peon, para 
                                                    if (ppp.getClass()==Pawn.class && MyWorld.game_phase == 0){
                                                        peon_fila[bbpp.getColumn()+9*ppp.player_color] = 1;
                                                    }
                                                }//si no es un peon
                                                else if (ppp.getClass()!=Pawn.class || MyWorld.game_phase==1){
                                                    //posicionamos la ficha
                                                    this.putPiece(ppp, bbpp, pan_x, pan_y-8*tier_size, tier_size,1);
                                                    ppp.in_play = 1;
                                                    MyWorldView.droping = 0;
                                                    //reordenamos las manos de los jugadores
                                                    if (ppp.player_color==0){
                                                        ordenar(hand0);
                                                    }
                                                    else{
                                                        ordenar(hand1);
                                                    }
                                                    //y terminamos nuestro turno
                                                    this.turnEnd(MyWorld.player_color);
                                                }
                                            }
                                            //si intetamos jugar la ficha sobre una ficha rival (no permitido)
                                            else{
                                                //devolvemos la ficha a la mano correspondiente
                                                ppp = (Pawn)MyWorldView.ficha_move;
                                                if (ppp.player_color==0){
                                                    hand0.add(ppp);
                                                    ordenar(hand0);
                                                }
                                                else{
                                                    hand1.add(ppp);
                                                    ordenar(hand1);
                                                }
                                                MyWorldView.ficha_move = null;
                                                MyWorldView.droping = 0;
                                            }
                                        }
                                    }
                                    //si no hay fichas en la baldosa
                                    else{
                                        //posicionamos la ficha en la baldosa directamente, y reordenamos las manos
                                        ppp = (Pawn)MyWorldView.ficha_move;
                                        if (MyWorld.game_phase==0 && ppp.getClass()==Pawn.class && peon_fila[bbpp.getColumn() + 9*ppp.player_color]==0 ){
                                            this.putPiece(ppp, bbpp, pan_x, pan_y-8*tier_size, tier_size,1);
                                            ppp.in_play = 1;
                                            MyWorldView.droping = 0;
                                            if (ppp.player_color==0){
                                                ordenar(hand0);
                                            }
                                            else{
                                                ordenar(hand1);
                                            }
                                            MyWorldView.ficha_move = null;
                                            //y terminamos nuestro turno
                                            this.turnEnd(MyWorld.player_color);
                                            if (ppp.getClass()==Pawn.class){
                                                peon_fila[bbpp.getColumn()+9*ppp.player_color] = 1;
                                            }
                                        }
                                        else if(ppp.getClass()!=Pawn.class || MyWorld.game_phase==1){
                                            this.putPiece(ppp, bbpp, pan_x, pan_y-8*tier_size, tier_size,1);
                                            ppp.in_play = 1;
                                            MyWorldView.droping = 0;
                                            if (ppp.player_color==0){
                                                ordenar(hand0);
                                            }
                                            else{
                                                ordenar(hand1);
                                            }
                                            MyWorldView.ficha_move = null;
                                            //y terminamos nuestro turno
                                            this.turnEnd(MyWorld.player_color);
                                        }
                                    }
                                }
                            }
                        }
                        //si los movimientos de la ficha no permiten dejarla en la baldosa, se devuelve a la posicion anterior
                        else{
                            if (back_board!=null){
                                int posbx = back_board.getPosition().getX();
                                int posby = back_board.getPosition().getY();
                                int posbl = back_board.getTierSize();
                                this.putPiece(ppp,back_board,posbx,posby-8*posbl, posbl,0);
                                back_board.updateImg(0);
                                back_board = null;
                                moves.clear();
                            }
                        }
                    }
                    //si estamos seleccionando una ficha del tablero, verificamos si la actual baldosa tiene fichas
                    else if (tier_size>0 && MyWorldView.ficha_move==null){
                        //si escogemos una ficha del tablero, calculamos sus posibles posiciones
                        GameObject fm = (Pawn) (bbpp.getTier()).get(tier_size-1);    //sacamos la ultima ficha de la torre
                        Pawn ppp = (Pawn) fm;
                        //si la ficha es nuestra
                        if (ppp.player_color == MyWorld.player_color && MyWorld.game_phase !=0){
                            moves = ppp.checkMoves();                  //chequeamos los movimientos de la ficha
                            //si la ficha es capaz de moverse
                            if (moves.size()>0 || ppp.getClass()==Pawn.class || ppp.getClass()==Spy.class){
                                //la seleccionamos para moverla y destacamos las casillas posibles
                                back_board = bbpp;                    //guardamos la posicion actual en caso de un movimiento erroneo
                                ppp.depth = 10000;
                                ppp.level = 0;
                                MyWorldView.ficha_move = fm;
                                bbpp.removeTier(tier_size-1);
                                this.destacar();
                            }
                        }
                    }
                    MyWorldView.click_izquierdo = 0;
                }

                //si el mouse esta sobre esta baldosa lo destacamos (SelectImage)
                if (bbpp.imagen !=2 && bbpp!=back_board){
                    bbpp.updateImg(1);
                }
    		}
            //si no estamos dentro de la baldosa, solo verificamos la imagen que debe tener
    		else{
                if (MyWorldView.ficha_move==null) {
                    bbpp.updateImg(0);   //si no esta seleccionado el panel, se deja la imagen normal del tablero
                }
                else if (bbpp.imagen==1){
                    bbpp.updateImg(0);   //si no esta seleccionado el panel, se deja la imagen normal del tablero
                }
    		}
        }
        /*
        se recorre la lista de la mano del jugador 0 (color negro)
        */
        if(modoJuego==0 || (modoJuego==1 && pvcIA==1)){
            for (int i=0; i<hand0.size(); i++) {
                Pawn p_mano = (Pawn) hand0.get(i);
                int xpm  = p_mano.getPosition().getX();
                int ypm  = p_mano.getPosition().getY();
                int mod  = 0;
                if (i%5 ==4  || i==(hand0.size()-1) ){
                    mod = 1;
                }
                if (mouse_x>xpm && mouse_x<xpm+50+20*mod && mouse_y>ypm && mouse_y<ypm+70 && MyWorld.game_phase!=3){
                    if (MyWorldView.click_derecho==1){
                        info.setPosition(new Vector2D(MyWorldView.WIDTH/2 -115, MyWorldView.HEIGHT/2 -63));
                        info.updateImg(-1,1, p_mano);
                        MyWorldView.click_derecho = 0;
                    }
                    if(MyWorldView.click_izquierdo==1 && player_color==0 && MyWorldView.ficha_move==null){
                        MyWorldView.ficha_move = p_mano;
                        MyWorldView.ficha_move.depth = 10000;
                        hand0.remove(p_mano);
                        MyWorldView.droping = 1;
                        MyWorldView.click_izquierdo = 0;
                    }
                }
            }
        }

        /*
        se recorre la lista de la mano del jugador 1 (color rojo)
        */
        if(modoJuego==0 || (modoJuego==1 && pvcIA==0)){
            for (int i=0; i<hand1.size(); i++) {
                Pawn p_mano2 = (Pawn) hand1.get(i);
                int xpm  = p_mano2.getPosition().getX();
                int ypm  = p_mano2.getPosition().getY();
                int mod  = 1;
                if (i%5 ==4  || i==(hand1.size()-1) ){
                    mod = 0;
                }
                if (mouse_x>xpm+20*mod && mouse_x<xpm+70 && mouse_y>ypm && mouse_y<ypm+70 && MyWorld.game_phase!=3){
                    if (MyWorldView.click_derecho==1){
                        info.setPosition(new Vector2D(MyWorldView.WIDTH/2 -115, MyWorldView.HEIGHT/2 -63));
                        info.updateImg(-1,1, p_mano2);
                        MyWorldView.click_derecho = 0;
                    }
                    if(MyWorldView.click_izquierdo==1 && player_color==1 && MyWorldView.ficha_move==null){
                        MyWorldView.ficha_move = p_mano2;
                        MyWorldView.ficha_move.depth = 10000;
                        hand1.remove(p_mano2);
                        MyWorldView.droping = 1;
                        MyWorldView.click_izquierdo = 0;
                    }
                }
            }
        }

        /*
        se verifica movimientos fuera de lo normal, como jugar una ficha fuera del tablero
        */
        if (MyWorldView.ficha_move!=null  && (modoJuego==0 || (modoJuego==1 && pvcIA!=player_color) ) ){
            //si tenemos tomada una ficha y esta siendo usada por un usuraio
            Vector2D vec= new Vector2D(MyWorldView.x-35,MyWorldView.y-35);
            MyWorldView.ficha_move.setPosition(vec);
            if(MyWorldView.click_izquierdo==1){
                //si es un peon o un espia, se puede devolver a la mano
                Pawn ppp = (Pawn)MyWorldView.ficha_move;
                if( (ppp.getClass()==Pawn.class || ppp.getClass()==Spy.class) && game_phase==1 && ppp.in_play==1){
                    moves = ppp.checkMoves();                  //chequeamos los movimientos de la ficha
                    //si la ficha es capaz de moverse
                    if (moves.size()==0){
                        //devolver la ficha a la mano
                        if (ppp.player_color==0){
                            hand0.add(ppp);
                            ordenar(hand0);
                        }
                        else{
                            hand1.add(ppp);
                            ordenar(hand1);
                        }
                        MyWorldView.ficha_move = null;
                        this.turnEnd(MyWorld.player_color);
                        moves.clear();
                    }
                    else if (back_board!=null && game_phase==1){
                        int posbx = back_board.getPosition().getX();
                        int posby = back_board.getPosition().getY();
                        int posbl = back_board.getTierSize();
                        this.putPiece(ppp,back_board,posbx,posby-8*posbl, posbl,0);
                        back_board.updateImg(0);
                        back_board = null;
                        moves.clear();
                    }
                }
                //si queremos soltar la ficha, tiene que ser donde se pueda soltar, sino se devuelve a donde estaba
                else if (back_board!=null){
                    int posbx = back_board.getPosition().getX();
                    int posby = back_board.getPosition().getY();
                    int posbl = back_board.getTierSize();
                    this.putPiece(ppp,back_board,posbx,posby-8*posbl, posbl,0);
                    back_board.updateImg(0);
                    back_board = null;
                    moves.clear();
                }
                else if(MyWorldView.droping==1){
                    //devolver la ficha a la mano
                    if (ppp.player_color==0){
                        hand0.add(ppp);
                        ordenar(hand0);
                    }
                    else{
                        hand1.add(ppp);
                        ordenar(hand1);
                    }
                    MyWorldView.ficha_move = null;
                    MyWorldView.droping = 0;
                }
                MyWorldView.click_izquierdo = 0;
            }
        }
        if (MyWorldView.click_derecho==1){
            info.updateImg(0,0,null);
            MyWorldView.click_derecho = 0;
        }
        /*
        InteligenciaArtificial
        */
        if ((modoJuego== 1 && player_color == pvcIA) || modoJuego == 2) {
            //si estamos en la fase inicial game_phase = 0
            if (cont_t>think){
                if (game_phase == 0) {
                    ArrayList<GameObject> manoIA = hand0;
                    int linea_fondo = 0;
                    int linea_medio = 7;
                    int linea_primera = 0;
                    //si el jugador IA es 0(negro)
                    if (pvcIA == 0) {
                        manoIA = hand0;
                        linea_fondo = 8;
                        linea_medio = 7;
                        linea_primera = 6;
                    }
                    else{
                        manoIA = hand1;
                        linea_fondo = 0;
                        linea_medio = 1;
                        linea_primera = 2;
                    }
                    GameObject dropIA = null;
                    //si hay peones en la mano
                    int encontro = 0;
                    for (int i=0; i<manoIA.size(); i++) {
                        GameObject p_mano = manoIA.get(i);
                        if (p_mano.getClass()==Pawn.class){
                            if (encontro==0){
                                dropIA = p_mano;
                                encontro = 1;
                                break;
                            }
                        }
                    }
                    for (int i=0; i<manoIA.size(); i++) {
                        GameObject p_mano = manoIA.get(i);
                        if (p_mano.getClass()==Fortress.class){
                            if (encontro==0){
                                dropIA = p_mano;
                                encontro = 1;
                                break;
                            }
                        }
                    }
                    for (int i=0; i<manoIA.size(); i++) {
                        GameObject p_mano = manoIA.get(i);
                        if (p_mano.getClass()==Catapult.class){
                            if (encontro==0){
                                dropIA = p_mano;
                                encontro = 1;
                                break;
                            }
                        }
                    }
                    for (int i=0; i<manoIA.size(); i++) {
                        GameObject p_mano = manoIA.get(i);   
                        if (p_mano.getClass()==Commander.class){
                            if (encontro==0){
                                dropIA = p_mano;
                                encontro = 1;
                                break;
                            }
                        }
                    }
                    for (int i=0; i<manoIA.size(); i++) {
                        GameObject p_mano = manoIA.get(i);
                        if (encontro==0){
                            dropIA = p_mano;
                            break;
                        }
                    }
                    if (dropIA!=null){
                        BoardPanel board = null;
                        for (int i=0; i<paneles.size(); i++) {
                            BoardPanel bbpp = paneles.get(i);
                            if (dropIA.getClass()==Pawn.class){
                                if (bbpp.getRow()==linea_primera && bbpp.getColumn()==rnd.nextInt(9)) {
                                    if(bbpp.getTierSize()==0){
                                        board = bbpp;
                                        break;
                                    }
                                }
                            }
                            else if(dropIA.getClass()==Fortress.class){
                                if (bbpp.getRow()==linea_fondo && bbpp.getColumn()==rnd.nextInt(4)) {
                                    if(bbpp.getTierSize()==0){
                                        board = bbpp;
                                        break;
                                    }
                                }
                            }
                            else if(dropIA.getClass()==Catapult.class){
                                if (bbpp.getRow()==linea_fondo && bbpp.getColumn()==5+rnd.nextInt(4)) {
                                    if(bbpp.getTierSize()==0){
                                        board = bbpp;
                                        break;
                                    }
                                }
                            }
                            else if(dropIA.getClass()==Commander.class){
                                if (bbpp.getRow()==linea_fondo && bbpp.getColumn()==rnd.nextInt(9)) {
                                    if(bbpp.getTierSize()==0){
                                        board = bbpp;
                                        break;
                                    }
                                }
                            }
                            else{
                                if ((bbpp.getRow()>=linea_primera && pvcIA==0 || bbpp.getRow()<=linea_primera && pvcIA==1) && bbpp.getColumn()==rnd.nextInt(9)) {
                                    int btsize = bbpp.getTierSize();
                                    int prob_base = 5;  //si el numero random es igual a este, se deja la ficha
                                    int prob = 0;
                                    if(bbpp.getRow()==linea_medio){
                                        prob = rnd.nextInt(7);
                                    }
                                    else{
                                        prob = rnd.nextInt(30);
                                    }
                                    if(prob_base==prob){
                                        if(btsize==0 || (btsize<3 && (bbpp.getTier().get(btsize-1)).getClass()!=Commander.class ) ){
                                            board = bbpp;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        if (board!=null){
                            int bx = board.getPosition().getX();
                            int by = board.getPosition().getY();
                            int tier_size = board.getTierSize();
                            Pawn ppp =(Pawn)dropIA;
                            putPiece(ppp, board, bx, by-8*tier_size, tier_size,1);//tier_size,1);
                            ppp.depth = by+8*(tier_size+1);
                            ppp.in_play = 1;
                            manoIA.remove(dropIA);
                            ordenar(manoIA);
                            turnEnd(player_color);

                            cont_t = 0;
                        }
                    }
                }
                else if (game_phase == 1){
                    ArrayList<GameObject> manoIA;
                    GameObject dropIA = null;
                    BoardPanel panel = null;
                    //Commander targ = null;
                    //si juega negro
                    if (pvcIA == 0){
                        manoIA = fichasIA_0;
                        //targ = target0;
                    }
                    else{
                        manoIA = fichasIA_1;
                        //targ = target1;
                    }
                    //int randficha = rnd.nextInt(manoIA.size());

                    //modificamos la valoracion de cada ficha
                    //y escogemos la ficha con mayor valoracion este turno 
                    int pre_valor = 0;
                    int valoracion = 0;
                    for (int i=0; i<manoIA.size(); i++) {
                        Pawn ppp = (Pawn) manoIA.get(i);
                        BoardPanel propio_panel = ppp.own_bp;
                        int tsize = propio_panel.getTierSize();
                        if (tsize>0){
                            if (ppp==(Pawn)propio_panel.getTier().get(tsize-1)){
                                moves = ppp.checkMoves();                  //chequeamos los movimientos de la ficha
                                //recorremos los posibles movimientos de la ficha
                                //si no tiene movimientos se descarta, valoracion = 0;
                                if (moves.size()>0){
                                    for (int k=0; k<moves.size(); k++) {
                                        BoardPanel bbpp = moves.get(k);
                                        valoracion = rnd.nextInt(46);
                                        //se verifica si el valor es mayor al de la ultima ficha seleccionada
                                        if(valoracion>pre_valor){
                                            pre_valor = valoracion;
                                            dropIA = ppp;
                                            panel = bbpp;
                                        }
                                    }
                                }
                                else{
                                    valoracion = 0;
                                }
                            }
                        }
                    }
                    if(dropIA!=null){
                        if(panel!= null){
                            Pawn ppp = (Pawn)dropIA;
                            BoardPanel propio_panel = ppp.own_bp;
                            propio_panel.removeTier(propio_panel.getTierSize()-1);
                            moves = ppp.checkMoves();
                            if (moves.contains(panel)){
                                //tenemos una ficha para mover!
                                int tier_size =  panel.getTierSize();
                                int px =panel.getPosition().getX();
                                int py =panel.getPosition().getY();
                                Pawn top_bp = null;                         //la ultima ficha de la torre en esta baldosa
                                //si existe alguna ficha en la baldosa
                                if(tier_size>0){
                                    top_bp = (Pawn)panel.getTier().get(tier_size-1);
                                }
                                //verificamos si nos comemos una ficha oponente
                                if (top_bp!=null){
                                    if (top_bp.player_color!=ppp.player_color && top_bp.getClass()!=Fortress.class && top_bp.getClass()!=Catapult.class){
                                        //nos comemos la fichaa oponente
                                        top_bp.in_play = 0;
                                        //y la movemos a nuestra mano

                                        panel.removeTier(tier_size-1);
                                        tier_size-=1;
                                        top_bp.getOut();
                                        if (top_bp.getClass() == Commander.class){
                                            MyWorld.game_phase = 3;
                                            MyWorldView.win =  MyWorld.player_color;
                                        }
                                        putPiece(ppp, panel, px, py-8*tier_size, tier_size,1);
                                        ppp.depth = py+8*(tier_size+1);

                                        cont_t = 0;
                                        panel.updateImg(0);
                                        moves.clear();

                                        //y terminamos nuestro turno
                                        this.turnEnd(MyWorld.player_color);
                                    }
                                    else if (top_bp.player_color==ppp.player_color){
                                        this.putPiece(ppp, panel, px, py-8*tier_size, tier_size,1);
                                        ppp.depth = py+8*(tier_size+1);

                                        panel.updateImg(0);
                                        moves.clear();

                                        cont_t = 0;
                                        //y terminamos nuestro turno
                                        this.turnEnd(MyWorld.player_color);
                                    }
                                }
                                else{
                                    //posicionamos nuestra ficha en la baldosa
                                    putPiece(ppp, panel, px, py-8*tier_size, tier_size,1);
                                    ppp.depth = py+8*(tier_size+1);

                                    panel.updateImg(0);
                                    moves.clear();

                                    cont_t = 0;
                                    //y terminamos nuestro turno
                                    turnEnd(MyWorld.player_color);
                                }
                            }
                        }
                    }
                }
            }
            else{
                cont_t += refreshPeriod;
            }
        }


        //se verifica si la fase inicial del juego ya termino
        if(MyWorld.game_phase ==0 && MyWorldView.ficha_move ==null){
            if (MyWorld.hand0.size()==0 && MyWorld.hand1.size()==0){
                MyWorld.game_phase = 1;
            }
        }
        //se vuelve a pintar todas las piezas del programa
     	view.repaint();
   	}

    /**
    * Highlights the movments f a given piece
    */
    public void destacar(){
        ArrayList<BoardPanel> elem = MyWorld.getBP();
        ArrayList<BoardPanel> mov = MyWorld.getMV();
        for (BoardPanel e:elem){
            if (mov.contains(e)){
                e.updateImg(2);
            }
            else{
                e.updateImg(0);
            }
        }
        if (back_board!=null){
            back_board.updateImg(3);
        }
    }

    /**
    *   Sorts the hand of the specified player
    *   @param lista_mano list to sort
    */
    public void ordenar(ArrayList<GameObject> lista_mano){
        for (int i =0;i<lista_mano.size() ;i++ ) {
            Pawn ppp = (Pawn)lista_mano.get(i);
            int hor_index = (i)%5;
            int ver_index = (i)/5;
            if (ppp.player_color==0){
                ppp.setPosition(new Vector2D(20 + 50*hor_index, 50+80*ver_index));
            }
            else{
                ppp.setPosition(new Vector2D(MyWorldView.WIDTH-90-50*hor_index, 304+80*ver_index));
            }
            ppp.depth = i;
        }
    }

  
    public void putPiece(Pawn ppp, BoardPanel bbpp, int nx, int ny, int n_level, int sound){
        Vector2D vec = new Vector2D(nx, ny);
        ppp.setPosition(vec);
        bbpp.addTier(ppp);
        ppp.depth = ny+8*bbpp.getTierSize();
        ppp.level = n_level;
        ppp.setRow(bbpp.getRow());
        ppp.setColumn(bbpp.getColumn());
        MyWorldView.ficha_move = null;
        ppp.own_bp = bbpp;

//        if (sound==1){
//            this.putSound();
//        }
        info.updateImg(0,0,null);
    }

	public void repaintView(){
		view.repaint();
	}

    /**
    * Returns in a public form the list of elements (players and pieces)
    * @return elements game objects
    */
	public ArrayList<GameObject> getGO(){
		return elements;
	}

    /**
    * Returns in a public form the list of panels in the game
    * @return paneles board panels
    */
	public static ArrayList<BoardPanel> getBP(){
		return paneles;
	}

 
    public static ArrayList<BoardPanel> getMV(){
        return moves;
    }
    

   
//    public void putSound() {
//        try {
//            // direccion del archivo
//            URL url = this.getClass().getClassLoader().getResource("src/put.wav");
//            AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
//            // utilizamos sound clip para reproducirlo
//            Clip clip = AudioSystem.getClip();
//            clip.open(audioIn);
//            clip.start();
//        } catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }

    /*
    *Plays the ambience music
    */
//    public void backSound() {
//        try {
//            // direccion del archivo
//            URL url = this.getClass().getClassLoader().getResource("src/AruarianDance.wav");
//            //AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
//            // utilizamos sound clip para reproducirlo
//            this.backMusic = AudioSystem.getClip();
//            this.backMusic.open(audioIn);
//            this.backMusic.start();
//        } catch (UnsupportedAudioFileException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (LineUnavailableException e) {
//            e.printStackTrace();
//        }
//    }

    /**
    *Method to end the current turn
    * @param actual_player actual player playing
    */
    public void turnEnd(int actual_player){
        if (actual_player == 0){
            MyWorld.player_color = 1;
        }
        else{
            MyWorld.player_color = 0;
        }
        //si estamos en IA vs IA cambiamos de Ia jugadora
        if(modoJuego == 2){
            if (pvcIA ==  0){
                pvcIA = 1;
            }
            else{
                pvcIA = 0;
            }
        }
    }

    /**
    * Method to reset the world and start a new game
    * @param modo_juego new game mode
    * @param color_pvc  IA starter color
    */
    public void reset(int modo_juego, int color_pvc){
        player_color = 0;
        game_phase = 0;
        back_board = null;
        moves.clear();

        MyWorld.modoJuego = modo_juego;     //resetear el mundo a un modo de juego en particular
        if (color_pvc == 0){
            MyWorld.pvcIA = 1;
        }
        else if (color_pvc == 1){
            MyWorld.pvcIA = 0;
        }
        MyWorldView.click_derecho = 0;
        MyWorldView.click_izquierdo = 0;
        MyWorldView.ficha_move = null;
        MyWorldView.droping = 0;
        MyWorldView.win = -1;

        //se devuelven las fichas a la mano
        for (int i=0; i<elements.size();i++){
            GameObject ele = elements.get(i);
            if (ele instanceof Pawn){

                Pawn fichapawn = (Pawn) ele;
                if (fichapawn.in_play==1){
                    fichapawn.in_play = 0;
                    fichapawn.setColumn(-1);
                    fichapawn.setRow(-1);
                    if(fichapawn.player_color==0){
                        if (fichapawn.changedHand == 0) {
                            hand0.add(fichapawn);
                            ordenar(hand0);
                        }
                        else{
                            hand1.add(fichapawn);
                            ordenar(hand1);
                        }
                    }
                    else{
                        if (fichapawn.changedHand == 0) {
                            hand1.add(fichapawn);
                            ordenar(hand1);
                        }
                        else{
                            hand0.add(fichapawn);
                            ordenar(hand0);
                        }
                    }
                }
            }
        }
        //int sizee = hand0.size();
        for (int i=hand0.size()-1; i>=0; i--) {
            Pawn p0 =(Pawn)hand0.get(i);
            if(p0.changedHand == 1){
                p0.changedHand = 0;
                p0.player_color = 1;
                p0.in_play = 0;
                p0.updateView();
                hand1.add(p0);
                hand0.remove(p0);
            }
        }
        for (int i=hand1.size()-1; i>=0; i--) {
            Pawn p0 =(Pawn)hand1.get(i);
            if(p0.changedHand == 1){
                p0.changedHand = 0;
                p0.player_color = 0;
                p0.in_play = 0;
                p0.updateView();
                hand0.add(p0);
                hand1.remove(p0);
            }
        }
        ordenar(hand0);
        ordenar(hand1);

        //se borran las fichas de los board panels
        for (int i=0; i<paneles.size();i++){
            BoardPanel bbpp = paneles.get(i);         //obtenemos un panel del tablero
            bbpp.tier_clear();
        }
        //se resetea los peones
        for(int i=0;i<18;i++){
            peon_fila[i] = 0;
        }
    }
}
