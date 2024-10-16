package com.example.Library2.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="books_shivani")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String category;
    private String price;
    @Lob //large object
    private byte[] pdfFile;
    @ManyToOne // Relationship with User (author)
    @JoinColumn(name = "author_id", nullable = false) // Foreign key
    private User author;
}
