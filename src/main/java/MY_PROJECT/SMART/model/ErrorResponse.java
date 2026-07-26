package MY_PROJECT.SMART.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class ErrorResponse {
        private String status;   // "error" atau "success"
        private int code;        // 400, 401, 403, 404, 500
        private String message;  // Pesan error
        private String timestamp; // Waktu error terjadi
    }
