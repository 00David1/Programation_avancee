public class Consommateur implements Runnable {
    private final BoiteAuLettre boite;

    public Consommateur(BoiteAuLettre boite) {
        this.boite = boite;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String lettre = boite.retirer();
                System.out.println("Lettre retirée : " + lettre);

                if ("Q".equalsIgnoreCase(lettre)) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            System.out.println("Consommateur interrompu");
        }
    }
}
