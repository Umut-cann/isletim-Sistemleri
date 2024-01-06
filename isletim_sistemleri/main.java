
package isletim_sistemleri;
	import java.util.ArrayList;

public class main {
	    public static void main(String[] args) {
	    	
	    	
	    	
	        ArrayList<Integer> sayilar = new ArrayList<>();
	        for (int i = 1; i <= 1_000_000; i++) {
	            sayilar.add(i);
	        }
	      
	        int parcayaBolme = sayilar.size() / 4;

	        ArrayList<Integer> altListe1 = new ArrayList<>(sayilar.subList(0, parcayaBolme));
	        ArrayList<Integer> altListe2 = new ArrayList<>(sayilar.subList(parcayaBolme, 2 * parcayaBolme));
	        ArrayList<Integer> altListe3 = new ArrayList<>(sayilar.subList(2 * parcayaBolme, 3 * parcayaBolme));
	        ArrayList<Integer> altListe4 = new ArrayList<>(sayilar.subList(3 * parcayaBolme, sayilar.size()));
    
	        ArrayList<Integer> ciftSayilar = new ArrayList<>();
	        ArrayList<Integer> tekSayilar = new ArrayList<>();
	        ArrayList<Integer> asalSayilar = new ArrayList<>();

	       
	        Thread thread1 = new Thread(new SayiIsleyici(altListe1, ciftSayilar, tekSayilar, asalSayilar));
	        Thread thread2 = new Thread(new SayiIsleyici(altListe2, ciftSayilar, tekSayilar, asalSayilar));
	        Thread thread3 = new Thread(new SayiIsleyici(altListe3, ciftSayilar, tekSayilar, asalSayilar));
	        Thread thread4 = new Thread(new SayiIsleyici(altListe4, ciftSayilar, tekSayilar, asalSayilar));

	        thread1.start();
	        thread2.start();
	        thread3.start();
	        thread4.start();

	        try {
	          
	            thread1.join();
	            thread2.join();
	            thread3.join();
	            thread4.join();
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }

	        
	        
	    
	        
	        
     System.out.println("Tek Sayılar: " + tekSayilar);
	      //  System.out.println("Çift Sayılar: " + ciftSayilar);
	       
	   
	      // System.out.println("Asal Sayılar: " + asalSayilar);
	    }
	}


class SayiIsleyici implements Runnable {
	    private ArrayList<Integer> sayilar;
	    private ArrayList<Integer> ciftSayilar;
	    private ArrayList<Integer> tekSayilar;
	    private ArrayList<Integer> asalSayilar;

	    public SayiIsleyici(ArrayList<Integer> sayilar, ArrayList<Integer> ciftSayilar,
	                        ArrayList<Integer> tekSayilar, ArrayList<Integer> asalSayilar) {
	        this.sayilar = sayilar;
	        this.ciftSayilar = ciftSayilar;
	        this.tekSayilar = tekSayilar;
	        this.asalSayilar = asalSayilar;
	    }

	    @Override
	    public void run() {
	        for (int sayi : sayilar) {
	            if (ciftMi(sayi)) {
	                synchronized (ciftSayilar) {
	                    ciftSayilar.add(sayi);
	                }
	            } else {
	                synchronized (tekSayilar) {
	                    tekSayilar.add(sayi);
	                }
	            }

	            if (asalMi(sayi)) {
	                synchronized (asalSayilar) {
	                    asalSayilar.add(sayi);
	                }
	            }
	        }
	    }
	    private boolean  ciftMi(int sayi) {
	        return sayi % 2 == 0;
	    }

	    private boolean asalMi(int sayi) {
	        if (sayi < 2) {
	            return false;
	        }
	        for (int i = 2; i <= Math.sqrt(sayi); i++) {
	            if (sayi % i == 0) {
	                return false;
	            }   }
	        return true;
	    }
	}

