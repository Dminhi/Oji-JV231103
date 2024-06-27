package com.example.ojt.model.dto.response;

import com.example.ojt.model.entity.LevelJob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LevelJobResponse {
    private Integer id;
    private String name;
    public LevelJobResponse(LevelJob levelJob) {
        this.name = levelJob.getName();

    }
}
