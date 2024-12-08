package com.vpis.imgUpAndDownloadSystem.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageData {
    private List<img> pageData;
    private Long total;
}
