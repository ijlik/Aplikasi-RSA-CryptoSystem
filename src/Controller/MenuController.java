/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author ijlik
 */
public class MenuController {
    View.Menu v;
    public MenuController(View.Menu view){
        v = view;
        v.setVisible(true);
        v.addDecListener(new DecButton());
        v.addEncListener(new EncButton());
        v.addAboutListener(new AboutButton());
    }

    private class AboutButton implements MouseListener {

        public AboutButton() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("tombol about");
            v.setVisible(false);
            new View.About().setVisible(true);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private class EncButton implements MouseListener {

        public EncButton() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("tombol enkripsi");
            Controller.EncController c = new Controller.EncController(new View.GenerateKey());
            v.setVisible(false);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }

    private class DecButton implements MouseListener {

        public DecButton() {
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            System.out.println("tombol dekripsi");
            Controller.DecController c = new Controller.DecController(new View.DecForm());
            v.setVisible(false);
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
}
