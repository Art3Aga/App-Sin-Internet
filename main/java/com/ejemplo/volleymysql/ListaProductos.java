package com.ejemplo.volleymysql;

public class ListaProductos {
    private int id;
    private String descripcion;
    private String barcode;
    private double costo;
    private double precio;
    private String image;

    public ListaProductos(int id, String descripcion, String barcode, double costo, double precio, String image) {
        this.id = id;
        this.descripcion = descripcion;
        this.barcode = barcode;
        this.costo = costo;
        this.precio = precio;
        this.image = image;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
