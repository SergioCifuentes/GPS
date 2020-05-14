/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gps.Grafos;

import gps.ui.MenuPrincipal;
import java.util.ArrayList;

/**
 *
 * @author sergio
 */
public class ControladorGrafos {

    private ArrayList<Ubicacion> ubicaciones;

    public ControladorGrafos() {
        ubicaciones = new ArrayList<>();
    }

    public Ubicacion obtnerUbicacion(String nombre) {
        int posicion = binarySearch(nombre);
        if (posicion < ubicaciones.size()) {
            if (ubicaciones.get(posicion).getNombre().equals(nombre)) {
                return ubicaciones.get(posicion);
            }
        }

        return null;

    }

    public ArrayList<Ubicacion> getUbicaciones() {
        return ubicaciones;
    }

    public void addUbicacion(Ubicacion ubicacion) {
        if (ubicaciones.isEmpty()) {
            ubicaciones.add(ubicacion);
        } else {
            ubicaciones.add(binarySearch(ubicacion.getNombre()), ubicacion);
        }

    }

    private int binarySearch(String value) {
        int low = 0;
        int high = ubicaciones.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            String midVal = ubicaciones.get(mid).getNombre();
            int cmp = midVal.compareTo(value);

            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return low;
    }

    public Camino getArista(Ubicacion u1, Ubicacion u2,boolean enVehiculo) {
        Camino aux = null;
        if (isAdyacente(u1, u2)) {
            ArrayList<Camino> listaAristas = u1.getCaminos();
            for (int i = 0; i < listaAristas.size(); i++) {
                if (listaAristas.get(i).getDestino() == u2) {
                    aux = listaAristas.get(i);
                }
            }
        } else if (!enVehiculo&&isAdyacente(u2, u1)) {
            aux = getArista(u2, u1,enVehiculo);
        }
        return aux;
    }

    
    
    public boolean isAdyacente(Ubicacion origen, Ubicacion destino) {
        ArrayList<Camino> listaAristas = origen.getCaminos();
        if (listaAristas != null) {
            for (int i = 0; i < listaAristas.size(); i++) {
                if (listaAristas.get(i).getDestino() == destino) {
                    return true;
                }
            }
        }
        return false;
    }

    public void agregarRutas(int indexOrigen, int indexDestino,MenuPrincipal jframe,boolean enVehiculo){
        ArrayList<Ruta> rutas = new ArrayList<>();
        Ubicacion origen = ubicaciones.get(indexOrigen);
        Ubicacion destino = ubicaciones.get(indexDestino);
        Ruta ruta = new Ruta(origen, destino);
        buscarRuta(origen, destino, ruta, rutas, enVehiculo);
        rutas=   ordenarRutas(rutas,enVehiculo);
        ControladorDeRutas cdr= new ControladorDeRutas(rutas, jframe,enVehiculo,this);
        
        cdr.agregarRutas();
        
         
       
    }
    private ArrayList<Ruta> ordenarRutas(ArrayList<Ruta> rutas,boolean enVehiculo){
        ArrayList<Ruta> rutasAux = new ArrayList<>();
        Ruta rutaDistanciaMenor=rutas.get(0);
        Ruta rutaDesgasteoGasolinaMenor=rutas.get(0);
        Ruta rutaPromedioMenor=rutas.get(0);
        Ruta rutaDistanciaMayor=rutas.get(0);
        Ruta rutaDesgasteoGasolinaMayor=rutas.get(0);
        Ruta rutaPromedioMayor=rutas.get(0);
        System.out.println("rutas "+rutas.size());
        for (int i = 1; i < rutas.size(); i++) {
            if (rutaDistanciaMenor.getDistanciaTotal()>rutas.get(i).getDistanciaTotal()) {
                rutaDistanciaMenor=rutas.get(i);
            }else if (rutaDistanciaMayor.getDistanciaTotal()<rutas.get(i).getDistanciaTotal()) {
                rutaDistanciaMayor=rutas.get(i);
            }
            
            if (enVehiculo) {
                if (rutaDesgasteoGasolinaMenor.getGasolinaTotal()>rutas.get(i).getGasolinaTotal()) {
                    rutaDesgasteoGasolinaMenor=rutas.get(i);
                }else   if (rutaDesgasteoGasolinaMayor.getGasolinaTotal()<rutas.get(i).getGasolinaTotal()) {
                    rutaDesgasteoGasolinaMayor=rutas.get(i);
                }
            }else{
                if (rutaDesgasteoGasolinaMenor.getDesgasteTotal()>rutas.get(i).getDesgasteTotal()) {
                    rutaDesgasteoGasolinaMenor=rutas.get(i);
                }else   if (rutaDesgasteoGasolinaMayor.getDesgasteTotal()<rutas.get(i).getDesgasteTotal()) {
                    rutaDesgasteoGasolinaMayor=rutas.get(i);
                }
            }
            
            if (rutaPromedioMenor.getPromedio(enVehiculo)>rutas.get(i).getPromedio(enVehiculo)) {
                rutaPromedioMenor=rutas.get(i);
            }else if (rutaPromedioMayor.getPromedio(enVehiculo)<rutas.get(i).getPromedio(enVehiculo)) {
                rutaPromedioMayor=rutas.get(i);
            }
            
            
        }
        rutaDistanciaMenor.setMejorDistancia(true);
        rutaDistanciaMayor.setPeorDistancia(true);
        rutaPromedioMenor.setMejorPromedio(true);
        rutaPromedioMayor.setPeorPromedio(true);
        rutaDesgasteoGasolinaMenor.setMejorDesgaste(true);
        rutaDesgasteoGasolinaMayor.setPeorDesgaste(true);
        ArrayList<Ruta> rutasPeores= new ArrayList<>();
        ArrayList<Ruta> rutasNormales= new ArrayList<>();
        rutasAux.add(null);
        rutasAux.add(null);
        rutasAux.add(null);
        rutasPeores.add(null);
        rutasPeores.add(null);
        rutasPeores.add(null);
        for (int i = 0; i < rutas.size(); i++) {
            if (rutas.get(i).isMejorPromedio()) {
             rutasAux.set(0,rutas.get(i));
            }else   if (rutas.get(i).isMejorDistancia()) {
             rutasAux.set(1,rutas.get(i));
            }else if (rutas.get(i).isMejorDesgaste()) {
             rutasAux.set(2,rutas.get(i));
            }else if (rutas.get(i).isPeorPromedio()) {
             rutasPeores.set(0,rutas.get(i));
            }else if (rutas.get(i).isPeorDistancia()) {
             rutasPeores.set(1,rutas.get(i));
            }else if (rutas.get(i).isPeorDesgaste()) {
             rutasPeores.set(2,rutas.get(i));
            }else{
                rutasNormales.add(rutas.get(i));
            }
        }
        for (int i = rutasAux.size()-1; i > 0; i--) {
            if (rutasAux.get(i)==null) {
                rutasAux.remove(i);
            }
        }
        for (int i = rutasPeores.size()-1; i > 0; i--) {
            if (rutasPeores.get(i)==null) {
                rutasPeores.remove(i);
            }
        }
        for (int i = 0; i < rutasNormales.size(); i++) {
            rutasAux.add(rutasNormales.get(i));
        }
        for (int i = 0; i < rutasPeores.size(); i++) {
            rutasAux.add(rutasPeores.get(i));
        }
        System.out.println(rutasAux.size());
        for (int i = 0; i < rutasAux.size(); i++) {
            System.out.println(rutasAux.get(i).getDistanciaTotal());
        }
        
        
        return rutasAux;
        
    }
    public void buscarRuta(Ubicacion actual,Ubicacion destino,Ruta rutaActual,ArrayList<Ruta> rutas,boolean enVehiculo) {
        ArrayList<Camino> caminosDelActual = actual.getCaminos();
        actual.setVisitado(true);
        for (int i = 0; i < caminosDelActual.size(); i++) {
            
            Ubicacion ubiSiguiente=caminosDelActual.get(i).getDestino();
            if (!ubiSiguiente.isVisistado()) {
                rutaActual.agregarCamino(caminosDelActual.get(i));
                if (ubiSiguiente.equals(destino)) {
                    
                    Ruta rutaAux= new Ruta(rutaActual.getOrigen(), destino);
                    rutaAux.getRuta().addAll(rutaActual.getRuta());
                    rutaAux.setAtributos(rutaActual.getDistanciaTotal(), rutaActual.getGasolinaTotal(),rutaActual.getDesgasteTotal()
                            , rutaActual.getTiempoEnVehiculoTotal(), rutaActual.getTiempoAPieTotal());
                    rutas.add(rutaAux);
                    rutaActual.removerCamino();
                    
                }else{
                    
                    buscarRuta(ubiSiguiente, destino, rutaActual, rutas,enVehiculo);
                }
            }
        }
        if (!enVehiculo) {
            caminosDelActual=actual.getCaminosAPieNoRepitentes();
            for (int i = 0; i < caminosDelActual.size(); i++) {
                Ubicacion ubiSiguiente=caminosDelActual.get(i).getOrigen();
                if (!ubiSiguiente.isVisistado()) {
                    rutaActual.agregarCamino(caminosDelActual.get(i));
                if (ubiSiguiente.equals(destino)) {
                    Ruta rutaAux= new Ruta(rutaActual.getOrigen(), destino);
                    rutaAux.getRuta().addAll(rutaActual.getRuta());
                    rutaAux.setAtributos(rutaActual.getDistanciaTotal(), rutaActual.getGasolinaTotal(),rutaActual.getDesgasteTotal()
                            , rutaActual.getTiempoEnVehiculoTotal(), rutaActual.getTiempoAPieTotal());
                    rutas.add(rutaAux);
                     rutaActual.removerCamino();
                }else{
                    
                    buscarRuta(ubiSiguiente, destino, rutaActual, rutas,enVehiculo);
                }
            }
             }
        }
        rutaActual.removerCamino();
        actual.setVisitado(false);
    }

}
