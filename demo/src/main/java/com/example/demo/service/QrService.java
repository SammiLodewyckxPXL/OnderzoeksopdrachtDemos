package com.example.demo.service;

import com.example.demo.model.QrEntry;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class QrService {

    private static final String SECRET = "superSecretKey";

    private final Map<String, QrEntry> db = new ConcurrentHashMap<>();

    public String generateStatic() {
        String id = UUID.randomUUID().toString();
        db.put(id, new QrEntry(id, QrEntry.Type.STATIC, "USER:12345", null, null));
        return id;
    }

    public String generateDynamic() {
        String id = UUID.randomUUID().toString();
        db.put(id, new QrEntry(id, QrEntry.Type.DYNAMIC, "USER:12345", UUID.randomUUID().toString(), null));
        return id;
    }

    public String generateTimed() {
        String id = UUID.randomUUID().toString();
        long exp = Instant.now().getEpochSecond() + 10;
        db.put(id, new QrEntry(id, QrEntry.Type.TIMED, "USER:12345", null, exp));
        return id;
    }

    public String generateSigned() throws Exception {
        String id = UUID.randomUUID().toString();
        String data = "USER:12345";
        String sig = sign(data);
        db.put(id, new QrEntry(id, QrEntry.Type.SIGNED, data, sig, null));
        return id;
    }

    public String validate(String id) throws Exception {

        QrEntry entry = db.get(id);
        if (entry == null) {
            return "X BESTAAT NIET";
        }

        switch (entry.getType()) {

            case STATIC:
                return " STATIC GELDIG";

            case DYNAMIC:
                if (entry.isUsed()){
                    return "X DYNAMIC ONGELDIG";
                }
                entry.setUsed(true);
                return " DYNAMIC GELDIG";

            case TIMED:
                long now = Instant.now().getEpochSecond();
                if (entry.getExpiry() != null && now > entry.getExpiry()) {
                    return "X VERLOPEN";
                }
                return " TIMED GELDIG";

        }
    }

    private String sign(String data) throws Exception {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest((data + SECRET).getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}