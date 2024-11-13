import java.awt.*;
import javax.swing.*;

class UnMobile extends JPanel implements Runnable
{
    int saLargeur, saHauteur, sonDebDessin;
    final int sonPas = 10, sonTemps=50, sonCote=40;
    static SemaphoreGenerale sem1 = new SemaphoreGenerale(1);

    UnMobile(int telleLargeur, int telleHauteur)
    {
        super();
        saLargeur = telleLargeur;
        saHauteur = telleHauteur;
        setSize(telleLargeur, telleHauteur);
    }

    public void run()
    {
        for (sonDebDessin=0; sonDebDessin < saLargeur - sonPas; sonDebDessin+= sonPas)
        {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }

        sem1.syncWait();
        for (sonDebDessin= saLargeur - sonPas; sonDebDessin < 2*(saLargeur) - sonPas; sonDebDessin+= sonPas)
        {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }
        sem1.syncSignal();

        for (sonDebDessin= 2*(saLargeur) - sonPas; sonDebDessin < 3*(saLargeur) - sonPas; sonDebDessin+= sonPas)
        {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }

        for (sonDebDessin= 3*(saLargeur) - sonPas; sonDebDessin > 2*(saLargeur) - sonPas; sonDebDessin-= sonPas)
        {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }

        sem1.syncWait();
        for (sonDebDessin= 2*(saLargeur) - sonPas; sonDebDessin > saLargeur - sonPas; sonDebDessin-= sonPas)
        {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }
        sem1.syncSignal();

        for (sonDebDessin= saLargeur - sonPas; sonDebDessin > 0; sonDebDessin-= sonPas)
        {
            repaint();
            try{Thread.sleep(sonTemps);}
            catch (InterruptedException telleExcp)
            {telleExcp.printStackTrace();}
        }

        run();
    }

    public void paintComponent(Graphics telCG)
    {
        super.paintComponent(telCG);
        telCG.fillRect(sonDebDessin, saHauteur/2, sonCote, sonCote);
    }
}