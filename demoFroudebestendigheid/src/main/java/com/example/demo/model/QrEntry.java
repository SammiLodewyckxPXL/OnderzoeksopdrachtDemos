package com.example.demo.model;

public class QrEntry {

    public enum Type {
        STATIC,
        DYNAMIC,
        TIMED,
        SIGNED
    }

    private String id;
    private Type type;
    private String data;
    private String signature;
    private Long expiry;
    private boolean used;

    public QrEntry(String id, Type type, String data, String signature, Long expiry) {
        this.id = id;
        this.type = type;
        this.data = data;
        this.signature = signature;
        this.expiry = expiry;
        this.used = false;
    }

    public String getId() { return id; }
    public Type getType() { return type; }
    public String getData() { return data; }
    public String getSignature() { return signature; }
    public Long getExpiry() { return expiry; }

    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }
}