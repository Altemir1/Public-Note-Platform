package com.example.publicnotes.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Note {
    private String id;
    private String email;
    private String text;
    private List<String> tags;
    private Date createdAt;
} 