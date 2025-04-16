package com.example.mymemo;

public class Catatan {
    private String id;
    private String judul;
    private String description;
//    private String gambar;
    private String lokasi;

    public Catatan() {
        // Default constructor diperlukan oleh Firebase dan Retrofit
    }

    public Catatan(String id, String judul, String description, String lokasi) {
        this.id = id;
        this.judul = judul;
        this.description = description;
//        this.gambar = gambar;
        this.lokasi = lokasi;
    }

    // Getter dan Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getJudul() { return judul; }
    public void setJudul(String judul) { this.judul = judul; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
//    public String getGambar() { return gambar; }
//    public void setGambar(String gambar) { this.gambar = gambar; }
    public String getLokasi() { return lokasi; }
    public void setLokasi(String lokasi) { this.lokasi = lokasi; }
}
