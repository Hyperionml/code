package com.example.myfolderapp.controller;

import com.example.myfolderapp.pojo.Result;
import com.example.myfolderapp.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FolderController {

    @Autowired
    private FolderService folderService;

    @GetMapping("/get")
    public Result getFolderContents(@RequestParam String datetime) {
        return folderService.getFolderContents(datetime);
    }
}