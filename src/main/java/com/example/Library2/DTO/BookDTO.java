package com.example.Library2.DTO;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class BookDTO {
    private String title;
    private String category;
    private String price;
    private byte[] pdfFile;
}
