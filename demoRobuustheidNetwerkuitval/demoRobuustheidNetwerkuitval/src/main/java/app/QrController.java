package app;

import java.util.Base64;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/qr")
public class QrController {

    @GetMapping("/generate")
    public String generate(@RequestParam String userId, @RequestParam boolean dynamic) throws Exception {
        long timestamp = dynamic ? System.currentTimeMillis() : 0;
        String content = userId + ":" + timestamp;

        QRCodeWriter writer = new QRCodeWriter();
        BitMatrix matrix = writer.encode(content, BarcodeFormat.QR_CODE, 200, 200);

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < 200; y++) {
            for (int x = 0; x < 200; x++) {
                sb.append(matrix.get(x, y) ? "1" : "0");
            }
        }
        return Base64.getEncoder().encodeToString(sb.toString().getBytes());
    }

    @PostMapping("/sync")
    public String sync(@RequestBody String data) {
        return "SYNCED:" + data;
    }
}