/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps.Grafos;

import gps.Graphviz.Grafos;
import gps.ui.InfoRuta;
import gps.ui.MenuPrincipal;
import java.io.File;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author sergio
 */
public class ControladorDeRutas {
    private ArrayList<Ruta> rutas;
    private MenuPrincipal jFrame;
    private boolean enVehiculo;
    private InfoRuta rutaSeleccionada;
    private ControladorGrafos cg;

    public ControladorDeRutas(ArrayList<Ruta> rutas, MenuPrincipal jFrame,boolean enVehiculo,ControladorGrafos cg) {
        this.rutas = rutas;
        this.jFrame = jFrame;
        this.enVehiculo=enVehiculo;
        this.cg=cg;
    }
    
    
    public void agregarRutas(){
        JPanel jpanel=jFrame.getPanelRutas();
         jpanel.removeAll();
         jpanel.setSize(150,800);
         jpanel.setPreferredSize(jpanel.getSize());
         
         Grafos gr = new Grafos();
        for (int i = 0; i < rutas.size(); i++) {    
            gr.crearGrafo(String.valueOf(i), cg.getUbicaciones(), rutas.get(i));
            InfoRuta infoRuta=new InfoRuta(rutas.get(i),enVehiculo,this,i);
           
           infoRuta.setSize(InfoRuta.ANCHO,InfoRuta.ALTO);
           infoRuta.setBounds(0, i*infoRuta.getHeight(),InfoRuta.ANCHO,InfoRuta.ALTO);
             
           jpanel.add(infoRuta); 
        infoRuta.setVisible(true);
            if (i==0) {
                seleccionarRuta(infoRuta,0);
            }
           
           
        }
    }
    
    
    public void seleccionarRuta(InfoRuta ruta,int index){
        if (!ruta.isSeleccionado()) {
            if (rutaSeleccionada!=null) {
                rutaSeleccionada.setSeleccionado(false);
            }
            File file = new File("./Grafos/"+index+".png");
            System.out.println(file.getPath());
            if (file.exists()) {
                System.out.println("existe");
                ImageIcon im = new ImageIcon(file.getPath());
                jFrame.setImageGrafos(im);
            }
            
        ruta.setSeleccionado(true);
        
        rutaSeleccionada=ruta;
        }
        
    }
    
    
}
