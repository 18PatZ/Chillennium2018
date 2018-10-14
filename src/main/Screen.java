package main;

import collision.Moveable;
import geometry.Line;
import geometry.Point2D;
import geometry.Point3D;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import level.LevelManager;
import lombok.Getter;
import object.*;
import util.POPair;
import util.Util;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Getter
public class Screen extends Application implements EventHandler<KeyEvent> {

    private GraphicsContext context;
    @Getter private static int width = 1000;
    @Getter private static int height = 1000;

    @Getter public static Screen instance;
    @Getter public static List<Line> lines;

    private HashSet<String> input = new HashSet<>();
    private Point2D offset = new Point2D(0, 0);
    private Point2D targetOffset = new Point2D(0, 0);
    private List<Objekt> objekts = new ArrayList<>();
    private List<Moveable> moveables = new ArrayList<>();
    private List<Objekt> destroyQueue = new ArrayList<>();
    private List<Objekt> addQueue = new ArrayList<>();
    private long lastTime = System.currentTimeMillis();

    private Image background;
    private Image border;//werewolf vigntte

    private int tick = 0;
    private int fps = 0;
    private int borderTick= 0;
    private boolean wolfLast = false;

    public void set(){
        add(new StaticObject(-4.2, 0, 2, "cowS.png", 200));
//        add(new StaticObject(2, 4, 0, "cubeT.png", 156, 1, 1));

        for(int i = 0; i < 10; i++)
            add(new StaticObject(4, 5 + i * 2, 0, "fruit_holder.png", 400, 3, 1));


        for(int i = 0; i < 3; i++) {
            add(new StaticObject(1 - i * 2, 4, 0, "cashier.png", 200, 1, 1.4));
            for (int j = 0; j < 2; j++)
                add(new StaticObject(1 - i * 2, 11 + j * 4, 0, "veggie_holder.png", 400, 1, 3));
        }

        for(int i = 0; i < 4; i++)
            for(int j = 0; j < 2; j++)
                    add(new StaticObject(-8 - j * 2, 4 + 4 * i, 0, "shelves.png", 400, 0.6, 3));

        for (int i = 0; i < 15; i++)
            add(new Passive(1,1,0,Color.GREEN, "person2.png"));

        add(new Player(2, 2, 0, Color.CADETBLUE, "troll.png", 0.5, 0.5));

        new Image(new File("images/liz.png").toURI().toString());
    }

    private void add(Objekt obj){
        objekts.add(obj);
        if(obj instanceof Moveable)
            moveables.add((Moveable) obj);
    }
    

    public boolean isPressed(String str){
        return input.contains(str.toLowerCase());
    }

    double ox = -14;
    double oy = -39;

    @Override
    public void start(Stage stage) {

        new LevelManager();

        background = new Image(new File("images/background.png").toURI().toString());
        border = new Image(new File("images/border2.png").toURI().toString());

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize(); // get screen size wrapper
        width = (int)dim.getWidth();
        height = (int)dim.getHeight();

        instance = this;

        Canvas canvas = new Canvas(width, height);                  // object representing onscreen canvas
        context = canvas.getGraphicsContext2D();                    // object that handles canvas manipulation

        Scene scene = new Scene(new Group(canvas));                 // create a scene with the canvas as the only node
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setTitle("Chillennium 2018");

        //make resizing work:
        stage.widthProperty().addListener((obs, oldVal, newVal) -> {
            width = (int)stage.getWidth();
        });
        stage.heightProperty().addListener((obs, oldVal, newVal) -> {
            height = (int)stage.getHeight();
        });
        stage.maximizedProperty().addListener((obs, oldVal, newVal) -> {
            if(!stage.isMaximized()){
                stage.setWidth(dim.getWidth()*2/3);
                stage.setHeight(dim.getHeight()*2/3);
            }
        });


        stage.show();

        scene.setOnKeyPressed(this);                                // Register oneself as the key press listener
        scene.setOnKeyReleased(this);

        set();

//        Point3D pp = Util.getPointOnScreen(10, 0, 0b
//        System.out.println(pp.getX() + " " + pp.getY() + " " + pp.getZ());
//        ox = 1548.2506003549065 - pp.getX();
//        oy = pp.getY() - 205;

        new AnimationTimer(){

            @Override
            public void handle(long arg) {

                tick++;

                lines = new ArrayList<>();

                context.setFill(Color.WHITESMOKE);
                context.fillRect(0, 0, width, height);

                List<Line> lines = new ArrayList<>();

//                if(isPressed("U"))
//                    ox++;
//                if(isPressed("Y"))
//                    ox--;
//                if(isPressed("O"))
//                    oy++;
//                if(isPressed("I"))
//                    oy--;
                if(isPressed("O")){
                    ox = 111;
                    oy = -14;
                    targetOffset.x-=100;
                }
//                System.out.println(ox + " " + oy);
                context.drawImage(background, offset.x + ox, offset.y + oy, 1920 * 1.5, 1920/1.7320875438 * 1.5);
                
                if(tick % 5 == 0)
                    fps = (int) (1.0 / (System.currentTimeMillis() - lastTime) * 1000);

                lastTime = System.currentTimeMillis();

                // grid stuff

                if(true) {

                    Color lCol = new Color(0.65, 0.65, 0.65, 1);

                    for(int i = 1; i <= 40; i++){

                        double[][] points = new double[][]{
                                {-20, 10},
                                {0, 0},
                                {i, i},
                        };

                        double[][] rot = Util.mult(Util.rotationMatrix(0, Math.toRadians(yaw), 0), points);

                        double[][] fin = Util.mult(Util.rotationMatrix(Math.toRadians(pitch), 0, 0),
                                Util.translate(rot, 0, 0, 0));

                        fin = Util.getDisplayed(fin);

                        Line line = new Line(Point3D.fromArray(fin[0]), Point3D.fromArray(fin[1]));
                        line.color = lCol;
                        lines.add(line);
                    }

                    for(int i = -20; i < 10; i++){

                        double[][] points = new double[][]{
                                {-40, 0},
                                {0, 0},
                                {i, i},
                        };

                        double[][] rot = Util.mult(Util.rotationMatrix(0, Math.toRadians(yaw + 90), 0), points);

                        double[][] fin = Util.mult(Util.rotationMatrix(Math.toRadians(pitch), 0, 0),
                                Util.translate(rot, 0, 0, 0));

                        fin = Util.getDisplayed(fin);

                        Line line = new Line(Point3D.fromArray(fin[0]), Point3D.fromArray(fin[1]));
                        line.color = lCol;
                        lines.add(line);
                    }
                }

                // end grid

                lines.add(makeLine(p(0, 0, 0), p(1, 0, 0), Color.RED));
                lines.add(makeLine(p(0, 0, 0), p(0, 0, 1), Color.GREEN));

                Color col = Color.LIGHTGRAY;
                lines.add(makeLine(p(-9, 0, 16), p(10, 0, 16), col));
                lines.add(makeLine(p(-9, 0, 0), p(-9, 0, 16), col));

                lines.add(makeLine(p(-9, 0, 0), p(10, 0, 0), Color.rgb(90, 45, 15)));
                lines.add(makeLine(p(10, 0, 0), p(10, 0, 16), Color.rgb(90, 45, 15)));

//                lines.add(makeLine(p(-5, 0, -5), p(-5, 2, -5), Color.BLUE));
//                lines.add(makeLine(p(-5, 0, 5), p(-5, 2, 5), Color.BLUE));
//                lines.add(makeLine(p(5, 0, -5), p(5, 2, -5), Color.BLUE));
//                lines.add(makeLine(p(5, 0, 5), p(5, 2, 5), Color.BLUE));
//
//                lines.add(makeLine(p(-5, 2, 5), p(5, 2, 5), Color.BLUE));
//                lines.add(makeLine(p(-5, 2, -5), p(5, 2, -5), Color.BLUE));
//                lines.add(makeLine(p(5, 2, -5), p(5, 2, 5), Color.BLUE));
//                lines.add(makeLine(p(-5, 2, -5), p(-5, 2, 5), Color.BLUE));

                lines.forEach(l -> {

                    context.setFill(l.color);

                    double p1 = width / 2.0 + l.p1.x + offset.x;
                    double p1y = height / 2.0 - l.p1.y + offset.y;

                    double p2 = width / 2.0 + l.p2.x + offset.x;
                    double p2y = height / 2.0 - l.p2.y + offset.y;

                    double angle = Util.getAngle(p2 - p1, p2y - p1y) - 90;

                    context.save();
                    context.rotate(angle);

                    double[] p1r = Util.rotate(p1, p1y, angle);

                    double mag = Util.dist(p1, p1y, p2, p2y);

                    context.fillRect(p1r[0], p1r[1] - 2, mag, l.color.equals(Color.rgb(90, 45, 15)) ? 8 : 4);

                    context.restore();

                });

//                List<POPair> pairs = new ArrayList<>();
                List<Objekt> pairs = new ArrayList<>();

                objekts.forEach(o -> {

                    o.tick();

//                    Point3D point = Util.getPointOnScreen(o.center);
//                    pairs.add(POPair.builder().object(o).screenPoint(point).build());
                    if(!(o instanceof Moveable))
                        pairs.add(o);

                });

                for(Moveable p1 : moveables){
                    Objekt player = (Objekt) p1;
                    for(int j = 0; j <= pairs.size(); j++) {
                        if(j == pairs.size()) {
                            pairs.add(player);
                            break;
                        }
                        else {
                            Objekt p = pairs.get(j);
                            if (!(p instanceof Moveable) && player.center.getX() > p.center.getX() && player.center.getZ() < p.center.getZ()) {
                                pairs.add(j, player);
                                break;
                            }
                        }
                    }
                }

//                Collections.sort(pairs);

                pairs.forEach(p -> {

//                    Objekt o = p.getObject();
//                    Point3D point = p.getScreenPoint();
                    Objekt o = p;
                    Point3D point = Util.getPointOnScreen(o.center);

                    context.setFill(o.color);
                    context.fillRoundRect(point.x - 10, point.y - 10, 20, 20, 20, 20);

                    if(o.image != null) {
                        if(!o.flipped)
                            context.drawImage(o.image.getImage(), point.x - o.image.getWidth() / 2, point.y - o.image.getHeight(), o.image.getWidth(), o.image.getHeight());
                        else
                            context.drawImage(o.image.getImage(), point.x + o.image.getWidth() / 2, point.y - o.image.getHeight(), -o.image.getWidth(), o.image.getHeight());
                    }

                    o.postTick(point);

                });

                if(tick == 360)
                    LevelManager.getInstance().nextWave();

                if(tick >= 360)
                    LevelManager.getInstance().getCurrentWave().tick();

                objekts.removeAll(destroyQueue);
                moveables.removeAll(destroyQueue);
                destroyQueue.clear();

                addQueue.forEach(Screen.this::add);
                addQueue.clear();

                double damp = 7.0;
                offset.x += (targetOffset.x - offset.x) / damp;
                offset.y += (targetOffset.y - offset.y) / damp;

                double sp = 360.0 / 240;
                if(isPressed("LEFT"))
                    yaw += sp;
                if(isPressed("RIGHT"))
                    yaw -= sp;
                if(isPressed("UP"))
                    pitch += sp;
                if(isPressed("DOWN"))
                    pitch -= sp;

//                if(tick % 360 <= 60get
//                    yaw += 360.0 / 60.0;
//
//                if(tick % 360 - 180 >= 0 && tick % 360 - 180 <= 60)
//                    pitch += 360.0 / 60.0;

// border image
    if(Player.getInstance().isWolf()){
        if()
        if(borderTick<60){
            context.setGlobalAlpha(((double)borderTick)/60);
            context.drawImage(border, 0, 0,width,height);
            borderTick++;
        }
        else{
            context.drawImage(border, 0, 0,width,height);
        }
        wolfLast = true;
    }
    if(!Player.getInstance().isWolf()){
        if(borderTick>1){
            context.setGlobalAlpha(((double)borderTick)/60);
            context.drawImage(border, 0, 0,width,height);
            context.setGlobalAlpha(1);
            borderTick--;
        }

    }


                Screen.getInstance().getContext().setFill(Color.GRAY);
                Screen.getInstance().getContext().setFont(Font.font("Verdana", FontWeight.EXTRA_BOLD, 24));
                Screen.getInstance().getContext().fillText("FPS: " + fps, 40, 40);
                Screen.getInstance().getContext().fillText("Wolf: " + Player.getInstance().isWolf(), 40, 60);

            }

        }.start();

    }

    private double[] p(double... d){
        return d;
    }

    private double yaw = 45;
    private double pitch = 30;

    public Line makeLine(double[] p1, double[] p2, Color color){
        double[][] points = new double[][]{
                {p1[0], p2[0]},
                {p1[1], p2[1]},
                {p1[2], p2[2]},
        };

        double[][] rot = Util.mult(Util.rotationMatrix(0, Math.toRadians(yaw), 0), points);

        double[][] fin = Util.mult(Util.rotationMatrix(Math.toRadians(pitch), 0, 0),
                Util.translate(rot, 0, 0, 0));

        fin = Util.getDisplayed(fin);

        Line line = new Line(Point3D.fromArray(fin[0]), Point3D.fromArray(fin[1]));
        line.color = color;
        return line;
    }

    public void markForDestruction(Objekt obj){
        destroyQueue.add(obj);
    }

    /**
     * Listens for escape key press and exits
     */
    @Override
    public void handle(KeyEvent event) {

        if(event.getCode().getName().equals("Esc"))
            System.exit(0);

        if(event.getEventType() == KeyEvent.KEY_PRESSED)
            input.add(event.getCode().toString().toLowerCase());
        else if(event.getEventType() == KeyEvent.KEY_RELEASED)
            input.remove(event.getCode().toString().toLowerCase());

    }
}
