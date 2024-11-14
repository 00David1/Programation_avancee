public class Main {
    public static void main(String[] args) {
        BoiteAuLettre boite = new BoiteAuLettre();

        Producteur producteur = new Producteur(boite);
        Consommateur consommateur = new Consommateur(boite);

        Thread threadProducteur = new Thread(producteur);
        Thread threadConsommateur = new Thread(consommateur);

        threadProducteur.start();
        threadConsommateur.start();

        try {
            threadProducteur.join();
            threadConsommateur.join();
        } catch (InterruptedException e) {
            System.out.println("Erreur de synchronisation");
        }
    }
}
