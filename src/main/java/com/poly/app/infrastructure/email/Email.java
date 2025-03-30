package com.poly.app.infrastructure.email;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.File;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Email {
    private String[] toEmail;
    private String subject;
    private String body;
    private String titleEmail;
    private File pdfFile;
    private String fileName;
}
