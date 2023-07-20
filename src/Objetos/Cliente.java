package Objetos;

public class Cliente{
        private String tipoDocumento;
        private int numeroDocumento;
        private String nombre;
        private String apellido;
        private String fechaNacimiento;
        private String domicilio;
        private int numeroTelefono;

        public Cliente(String tipoDocumento, int numeroDocumento, String nombre, String apellido, String fechaNacimiento, String domicilio, int numeroTelefono) {
                this.tipoDocumento = tipoDocumento;
                this.numeroDocumento = numeroDocumento;
                this.nombre = nombre;
                this.apellido = apellido;
                this.fechaNacimiento = fechaNacimiento;
                this.domicilio = domicilio;
                this.numeroTelefono = numeroTelefono;
        }

        public String getTipoDocumento() {
                return tipoDocumento;
        }

        public void setTipoDocumento(String tipoDocumento) {
                this.tipoDocumento = tipoDocumento;
        }

        public int getNumeroDocumento() {
                return numeroDocumento;
        }

        public void setNumeroDocumento(int numeroDocumento) {
                this.numeroDocumento = numeroDocumento;
        }

        public String getNombre() {
                return nombre;
        }

        public void setNombre(String nombre) {
                this.nombre = nombre;
        }

        public String getApellido() {
                return apellido;
        }

        public void setApellido(String apellido) {
                this.apellido = apellido;
        }

        public String getFechaNacimiento() {
                return fechaNacimiento;
        }

        public void setFechaNacimiento(String fechaNacimiento) {
                this.fechaNacimiento = fechaNacimiento;
        }

        public String getDomicilio() {
                return domicilio;
        }

        public void setDomicilio(String domicilio) {
                this.domicilio = domicilio;
        }

        public int getNumeroTelefono() {
                return numeroTelefono;
        }

        public void setNumeroTelefono(int numeroTelefono) {
                this.numeroTelefono = numeroTelefono;
        }
}
