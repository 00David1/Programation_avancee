import java.util.Scanner;

public class Producteur implements Runnable {
    private final BoiteAuLettre boite;

    public Producteur(BoiteAuLettre boite) {
        this.boite = boite;
    }

    @Override
    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            while (true) {
                System.out.print("Entrez une lettre à déposer : ");
                String lettre = scanner.nextLine();

                boite.deposer(lettre);
                System.out.println("Lettre déposée : " + lettre);

                if ("Q".equalsIgnoreCase(lettre)) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Producteur interrompu");
        }
    }
}
