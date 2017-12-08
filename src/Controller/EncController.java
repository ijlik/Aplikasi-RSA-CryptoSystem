/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;
import javax.swing.JOptionPane;

/**
 *
 * @author ijlik
 */
public class EncController {

    private View.GenerateKey vG;
    private View.EncForm v;
    private int p;
    private int q;
    private long n;
    private int d;
    private int nilaiE;
    private int sM;

    public EncController(View.GenerateKey view) {
        vG = view;
        vG.setVisible(true);
        vG.disableGeneratePriv();
        vG.addPubKeyListener(new PublicButton());
        vG.addPrivKeyListener(new PrivateButton());
        vG.addNextListener(new NextButton());
    }

    public EncController(View.EncForm view, int d, int sM, int nilaiE, long n) {
        v = view;
        this.d = d;
        this.nilaiE = nilaiE;
        this.sM = sM;
        this.n = n;
        v.setVisible(true);
        v.addEncListener(new ButtonEnc());
        v.setPrivKey(Integer.toString(d));
        v.setPubKey(Integer.toString(nilaiE));
        v.setSysm(Long.toString(n));
        v.disableEdit();
    }

    private class ButtonEnc implements ActionListener {

        public ButtonEnc() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String messages = v.getMessages();
            char x[];
            x = messages.toCharArray();
            long plain[] = new long[x.length];
            long chipher[] = new long[x.length];
            char res[] = new char[x.length];
            BigInteger bP;
            BigInteger bPpowE;
            BigInteger bC[] = new BigInteger[x.length];
            BigInteger bN = BigInteger.valueOf(n);
            String s = "";
            String hasil = "";
            for (int i = 0; i < x.length; i++) {
                plain[i] = (int) x[i];
            }
            for (int i = 0; i < x.length; i++) {
                bP = BigInteger.valueOf(plain[i]);
                bPpowE = bP.pow(nilaiE);
                bC[i] = bPpowE.mod(bN);
            }
            for (int i = 0; i < x.length; i++) {
                res[i] = (char) Integer.parseInt(bC[i].toString());
            }
            for (int i = 0; i < x.length; i++) {
                s += bC[i].toString() + " ";
                hasil += res[i];
            }
            v.setResult(s);
            v.setResult1(hasil);
        }
    }

    private class NextButton implements ActionListener {

        public NextButton() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (Integer.parseInt(vG.getPrivKey()) < 0) {
                JOptionPane.showMessageDialog(vG, "Private Key tidakboleh negatif, silahkan pilih Public key yang lain sehingga Private Key tidak negatif");
            } else {
                Controller.EncController c = new Controller.EncController(new View.EncForm(), d, sM, nilaiE, n);
                vG.setVisible(false);
            }
        }
    }

    private class PrivateButton implements ActionListener {

        public PrivateButton() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            n = 100;
            nilaiE = Integer.parseInt(vG.getPubKey());
            stepFPB(sM, nilaiE);
            stepSol(sM, nilaiE);
            vG.setResult(Integer.toString(d));
            vG.disableGenerateKey();
        }
        public int n = 100;

        public int hitungFPB(int x, int y) {
            if (x > y) { //memeriksa apakah x lebih besar dari y
                if (x % y == 0) { //jika sisa bagi x dan y adalah 0
                    return y; //maka FPBnya adalah y
                } else { //jika sisa bagi x dan y tidak sama dengan 0
                    return hitungFPB(y, x % y); //fungsi rekursif yang memanggil fungsi itu sendiri
                }
            } else { //jika x tidak lebih besar dari y. atau x &lt; y
                if (y % x == 0) { //jika sisa bagi y dan x adalah 0
                    return x; //maka FPBnya adalah x
                } else { //jika sisa bagi y dan x tidak sama dengan 0
                    return hitungFPB(x, y % x); //fungsi rekursif yang memanggil fungsi itu sendiri
                }
            }
        }

        public void stepFPB(int x, int y) {
            int aX[] = new int[20];
            int aY[] = new int[20];
            int aMd[] = new int[20];
            int aDv[] = new int[20];
            aX[0] = x;
            aY[0] = y;
            aMd[0] = aX[0] % aY[0];
            aDv[0] = aX[0] / aY[0];
            for (int i = 1; i < n; i++) {
                aX[i] = aY[i - 1];
                aY[i] = aMd[i - 1];

                try {
                    aMd[i] = aX[i] % aY[i];
                    aDv[i] = aX[i] / aY[i];
                } catch (Exception e) {
                    n--;
                }
                if (aY[i] == 0) {
                    n = i;
                    break;
                }
            }
            for (int i = 0; i < n; i++) {
                System.out.println("Step fpb " + (i + 1) + " : " + aX[i] + " = " + aDv[i] + " x " + aY[i] + " + " + aMd[i]);
            }
        }

        public void stepSol(int x, int y) {

            int fpb = hitungFPB(x, y); // INPUT UNTUK SOLUTION
            int aX[] = new int[20];
            int aY[] = new int[20];
            int aMd[] = new int[20];
            int aDv[] = new int[20];
            aX[0] = x;
            aY[0] = y;
            aMd[0] = aX[0] % aY[0];
            aDv[0] = aX[0] / aY[0];
            for (int i = 1; i < n; i++) {
                aX[i] = aY[i - 1];
                aY[i] = aMd[i - 1];
                try {
                    aMd[i] = aX[i] % aY[i];
                    aDv[i] = aX[i] / aY[i];
                } catch (Exception e) {
                    n--;
                }

            }
            int pKn[] = new int[20];
            int pKr[] = new int[20];
            int as = n - 1;
            pKr[0] = 0;
            pKn[0] = (fpb - (aX[as] * pKr[0])) / aY[as];
            for (int i = 1; i < n; i++) {
                pKr[i] = pKn[i - 1];
                pKn[i] = (fpb - (aX[as - i] * pKr[i])) / aY[as - i];
            }

            for (int i = 0; i < n; i++) {
                System.out.println("Step " + (i + 1) + " : " + aX[as] + " * " + pKr[i] + " + " + aY[as] + " * " + pKn[i] + " = " + fpb);
                as--;
            }
            d = pKn[n - 1];
        }
    }

    private class PublicButton implements ActionListener {

        public PublicButton() {
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                p = Integer.parseInt(vG.getP());
            } catch (Exception a) {
                JOptionPane.showMessageDialog(vG, "nilai P yang anda Masukan bukan Integer");
            }
            try {
                q = Integer.parseInt(vG.getQ());
            } catch (Exception a) {
                JOptionPane.showMessageDialog(vG, "nilai Q yang anda Masukan bukan Integer");
            }

            if (!isPrime(p)) {
                JOptionPane.showMessageDialog(vG, "nilai P yang anda Masukan bukan Bilangan Prima");
            } else if (!isPrime(q)) {
                JOptionPane.showMessageDialog(vG, "nilai Q yang anda Masukan bukan Bilangan Prima");
            } else {
                n = p * q;
                sM = (p - 1) * (q - 1);
                int available[] = new int[sM];
                int in = 0;
                for (int i = 1; i < sM; i++) {
                    if (hitungFPB(sM, i) == 1) {
                        if (isPrime(i)) {
                            available[in] = i;
                            in++;
                        }
                    }
                }
                String s[] = new String[sM / 7];
                System.out.println("Available");
                try {
                    for (int i = 0; i < s.length; i++) {
                        if (available[i] > 0) {
                            s[i] = Integer.toString(available[i]);
                            System.out.print(available[i] + ", ");
                        }
                    }
                } catch (Exception n) {
                    JOptionPane.showMessageDialog(vG, "Nilai yang anda masukan terlalu kecil, Namun anda tetap bisa melanjutkanya");
                }
                vG.setVisible(false);
                vG = new View.GenerateKey(s);
                vG.setVisible(true);
                vG.addPubKeyListener(new PublicButton());
                vG.addPrivKeyListener(new PrivateButton());
                vG.addNextListener(new NextButton());
                vG.disableGenerateResult();
            }
        }

        private boolean isPrime(int num) // method isPrime
        {
            boolean isPrime = true;
            int temp;
            for (int i = 2; i <= num / 2; i++) {
                temp = num % i;
                if (temp == 0) {
                    isPrime = false;
                    break;
                }
            }
            return isPrime;
        }

        public int hitungFPB(int x, int y) {
            if (x > y) { //memeriksa apakah x lebih besar dari y
                if (x % y == 0) { //jika sisa bagi x dan y adalah 0
                    return y; //maka FPBnya adalah y
                } else { //jika sisa bagi x dan y tidak sama dengan 0
                    return hitungFPB(y, x % y); //fungsi rekursif yang memanggil fungsi itu sendiri
                }
            } else { //jika x tidak lebih besar dari y. atau x &lt; y
                if (y % x == 0) { //jika sisa bagi y dan x adalah 0
                    return x; //maka FPBnya adalah x
                } else { //jika sisa bagi y dan x tidak sama dengan 0
                    return hitungFPB(x, y % x); //fungsi rekursif yang memanggil fungsi itu sendiri
                }
            }
        }
    }
}
