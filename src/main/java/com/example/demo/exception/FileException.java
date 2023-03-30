package com.example.demo.exception;

public class FileException extends BaseException{

    public FileException(String code){
        super("user." + code);
    }
    public static FileException fileNull(){
        return new FileException("upload.file.null");
    }
    public static FileException maxFileSize(){
        return new FileException("upload.file.size");
    }
    public static FileException unsupportedFileType(){
        return new FileException("upload.file.unsupportedType");
    }

}
