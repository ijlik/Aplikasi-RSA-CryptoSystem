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
public class DecController {

    private View.DecForm v;

    public DecController(View.DecForm view) {
        v = view;
        v.setVisible(true);
        v.addDekripListener(new DekripButton());
    }

    private class DekripButton implements ActionListener {

        public DekripButton() {
        }

        private boolean isPrime(long num) // method isPrime
        {
            boolean isPrime = true;
            long temp;
            for (int i = 2; i <= num / 2; i++) {
                temp = num % i;
                if (temp == 0) {
                    isPrime = false;
                    break;
                }
            }
            return isPrime;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            int d = Integer.parseInt(v.getPrivKey());
            long p = 0, q = 0;
            try {
                p = Long.parseLong(v.getP());
            } catch (Exception a) {
                JOptionPane.showMessageDialog(v, "nilai P yang anda Masukan bukan Integer");
            }
            try {
                q = Long.parseLong(v.getQ());
            } catch (Exception a) {
                JOptionPane.showMessageDialog(v, "nilai Q yang anda Masukan bukan Integer");
            }

            if (!isPrime(p)) {
                JOptionPane.showMessageDialog(v, "nilai P yang anda Masukan bukan Bilangan Prima");
            } else if (!isPrime(q)) {
                JOptionPane.showMessageDialog(v, "nilai Q yang anda Masukan bukan Bilangan Prima");
            } else {
                long n = p * q;
                long sM = (p - 1) * (q - 1);
                String s = v.getMesages();
                String hasil = "";
                String x[] = s.split(" ");
                char res[] = new char[x.length];
                long chipher[] = new long[x.length];
                try {
                    for (int i = 0; i < x.length; i++) {
                        chipher[i] = Long.parseLong(x[i]);
                    }
                } catch (Exception asai) {
                    JOptionPane.showMessageDialog(v, "Message yang anda masukan terdapat beberapa karakter non ascii");
                }
                BigInteger bM [] = new BigInteger[x.length];
                BigInteger bCpowD;
                BigInteger bC;
                BigInteger bN = BigInteger.valueOf(n);
                
                for (int i = 0; i < x.length; i++) {
                    bC = BigInteger.valueOf(chipher[i]);
                    bCpowD = bC.pow(d);
                    bM [i] = bCpowD.mod(bN);
                }
                
                for (int i = 0; i < x.length; i++) {
                    res[i] = (char) Integer.parseInt(bM[i].toString());
                }
                for (int i = 0; i < x.length; i++) {
                    hasil += res[i];
                }
                v.setResult(hasil);
            }
        }
    }
}
