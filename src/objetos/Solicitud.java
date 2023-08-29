package objetos;

import java.time.LocalDate;

public class Solicitud {

    private String ciudadDestino;
    private LocalDate fechaSol; // ! Ver como resolver el tema de entrada de este parametro si lo resuelve el
                                // constructor, o se delega la responsabilidad afuera
    private Cliente cliente;
    private int metrosCubicos;
    private int cantBultos;
    private String domicilioRetiro;
    private String domicilioEntrega;
    private boolean estaPago;

    // Contructores
    public Solicitud(String ciudadDestino, LocalDate fechaSol, Cliente cliente, int metrosCubicos, int cantBultos,
            String domicilioRetiro, String domicilioEntrega, boolean estaPago) {
        this.ciudadDestino = ciudadDestino;
        this.fechaSol = fechaSol;
        this.cliente = cliente;
        this.metrosCubicos = metrosCubicos;
        this.cantBultos = cantBultos;
        this.domicilioRetiro = domicilioRetiro;
        this.domicilioEntrega = domicilioEntrega;
        this.estaPago = estaPago;
    }

    public Solicitud() {
        this.ciudadDestino = "";
        this.fechaSol = null;
        this.cliente = null;
        this.metrosCubicos = -1;
        this.cantBultos = -1;
        this.domicilioRetiro = "";
        this.domicilioEntrega = "";
        this.estaPago = false;
    }

    // Getters/Setters

    public String getCiudadDestino() {
        return this.ciudadDestino;
    }

    public void setCiudadDestino(String ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public LocalDate getFechaSol() {
        return this.fechaSol;
    }

    public void setFechaSol(LocalDate fechaSol) {
        this.fechaSol = fechaSol;
    }

    public Cliente getCliente() {
        return this.cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public int getMetrosCubicos() {
        return this.metrosCubicos;
    }

    public void setMetrosCubicos(int metrosCubicos) {
        this.metrosCubicos = metrosCubicos;
    }

    public int getCantBultos() {
        return this.cantBultos;
    }

    public void setCantBultos(int cantBultos) {
        this.cantBultos = cantBultos;
    }

    public String getDomicilioRetiro() {
        return this.domicilioRetiro;
    }

    public void setDomicilioRetiro(String domicilioRetiro) {
        this.domicilioRetiro = domicilioRetiro;
    }

    public String getDomicilioEntrega() {
        return this.domicilioEntrega;
    }

    public void setDomicilioEntrega(String domicilioEntrega) {
        this.domicilioEntrega = domicilioEntrega;
    }

    public boolean isEstaPago() {
        return this.estaPago;
    }

    public boolean getEstaPago() {
        return this.estaPago;
    }

    public void setEstaPago(boolean estaPago) {
        this.estaPago = estaPago;
    }

}
