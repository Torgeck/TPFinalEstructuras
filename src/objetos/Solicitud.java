package objetos;

public class Solicitud {

    private int ciudadDestino;
    private String fechaSol;
    private String idCliente;
    private double metrosCubicos;
    private int cantBultos;
    private String domicilioRetiro;
    private String domicilioEntrega;
    private boolean estaPago;
    private int idSolicitud;
    private static int ID = 0;

    // Contructores
    public Solicitud(int ciudadDestino, String fechaSol, String idCliente, double metrosCubicos, int cantBultos,
            String domicilioRetiro, String domicilioEntrega, boolean estaPago) {
        this.ciudadDestino = ciudadDestino;
        this.fechaSol = fechaSol;
        this.idCliente = idCliente;
        this.metrosCubicos = metrosCubicos;
        this.cantBultos = cantBultos;
        this.domicilioRetiro = domicilioRetiro;
        this.domicilioEntrega = domicilioEntrega;
        this.estaPago = estaPago;
        this.idSolicitud = this.generarId();
    }

    public Solicitud() {
        this(-1, null, "", -1, -1, "", "", false);
    }

    // Getters/Setters

    public int getCiudadDestino() {
        return this.ciudadDestino;
    }

    public void setCiudadDestino(int ciudadDestino) {
        this.ciudadDestino = ciudadDestino;
    }

    public String getFechaSol() {
        return this.fechaSol;
    }

    public void setFechaSol(String fechaSol) {
        this.fechaSol = fechaSol;
    }

    public String getIdCliente() {
        return this.idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public double getMetrosCubicos() {
        return this.metrosCubicos;
    }

    public void setMetrosCubicos(double metrosCubicos) {
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

    public int getId() {
        return this.idSolicitud;
    }

    // Otros metodos
    public int generarId() {
        return ID++;
    }

    public boolean equals(Solicitud solicitud) {
        return this.idSolicitud == solicitud.getId();
    }

}
