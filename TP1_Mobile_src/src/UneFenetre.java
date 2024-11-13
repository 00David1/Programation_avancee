import java.awt.*;
import javax.swing.*;

class UneFenetre extends JFrame
{
    UnMobile sonMobile;
    UnMobile sonMobile2;
    UnMobile sonMobile3;
    UnMobile sonMobile4;
    //Button sonBouton;
    private final int LARG=400, HAUT=250;

    public UneFenetre()
    {
        super("Le Mobile");
        Container leconteneur = getContentPane();
        leconteneur.setLayout(new GridLayout(4,1));

        sonMobile = new UnMobile(LARG, HAUT);
        sonMobile2 = new UnMobile(LARG, HAUT);
        sonMobile3 = new UnMobile(LARG, HAUT);
        sonMobile4 = new UnMobile(LARG, HAUT);

        //sonBouton = new Button();

        leconteneur.add(sonMobile);
        leconteneur.add(sonMobile2);
        leconteneur.add(sonMobile3);
        leconteneur.add(sonMobile4);

        //leconteneur.add(sonBouton);
        Thread laTache = new Thread(sonMobile);
        Thread laTache2 = new Thread(sonMobile2);
        Thread laTache3 = new Thread(sonMobile3);
        Thread laTache4 = new Thread(sonMobile4);

        laTache.start();
        laTache2.start();
        laTache3.start();
        laTache4.start();

        setSize(3*LARG, 3*HAUT);
        setVisible(true);

        // TODO
        // ajouter sonMobile a la fenetre
        // creer une thread laThread avec sonMobile
        // afficher la fenetre
        // lancer laThread
    }
}
