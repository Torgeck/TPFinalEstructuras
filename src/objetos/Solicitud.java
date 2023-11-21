package objetos;

public class Solicitud {

    private int ciudadOrigen;
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
    public Solicitud(int ciudadOrigen, int ciudadDestino, String fechaSol, String idCliente, double metrosCubicos,
            int cantBultos,
            String domicilioRetiro, String domicilioEntrega, boolean estaPago) {
        this.ciudadOrigen = ciudadOrigen;
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
        this(-1, -1, null, "", -1, -1, "", "", false);
    }

    // Getters/Setters
    public int getCiudadOrigen() {
        return this.ciudadOrigen;
    }

    public void setCiudadOrigen(int ciudadOrigen) {
        this.ciudadOrigen = ciudadOrigen;
    }

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

    @Override
    public String toString() {
        return "Ciudad Origen: " + this.ciudadOrigen +
                "| Ciudad Destino:'" + this.ciudadDestino + "'" +
                "| Fecha:'" + this.fechaSol + "'" +
                "| ID Cliente:'" + this.idCliente + "'" +
                "| Cantidad metros cubicos:'" + this.metrosCubicos + "'" +
                "| Cantidad de bultos:'" + this.cantBultos + "'" +
                "| Dom. Retiro:'" + this.domicilioRetiro + "'" +
                "| Dom. Entrega:'" + this.domicilioEntrega + "'" +
                "| Pagado?:'" + this.estaPago + "'" +
                "| ID Solicitud:'" + this.idSolicitud + "'";
    }

}
