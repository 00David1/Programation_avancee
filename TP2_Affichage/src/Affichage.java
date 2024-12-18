import java.io.*;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.BufferedReader;
import java.lang.String;
import java.util.concurrent.Semaphore;

class Exclusion{};
public class Affichage extends Thread{
	String texte;
	static semaphoreBinaire sem = new semaphoreBinaire(1);
        
	//static Exclusion exclusionMutuelle = new Exclusion();

	public Affichage (String txt){texte=txt;}
	
	public void run(){

		//Avec synchronized
		/*
		//synchronized (Exclusion exclusionMutuelle) {
	    synchronized (System.out) { //section critique
	    for (int i=0; i<texte.length(); i++){
		    System.out.print(texte.charAt(i));
		    try {sleep(100);} catch(InterruptedException e){};
		}
	    }
*/
		//Avec semaphore

		sem.syncWait();
		for (int i=0; i<texte.length(); i++){
			System.out.print(texte.charAt(i));
			try {sleep(100);} catch(InterruptedException e){};
		}
		sem.syncSignal();
	}
}
