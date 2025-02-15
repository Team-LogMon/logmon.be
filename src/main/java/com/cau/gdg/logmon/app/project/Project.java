package com.cau.gdg.logmon.app.project;

import com.google.cloud.firestore.annotation.DocumentId;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Getter
public class Project {

    public enum Status {
        ACTIVE, DEACTIVE
    }

    public enum Pricing {
        FREE, BASIC
    }

    @DocumentId
    private String id;
    private String title;
    private String description;
    private String owner;
    private List<String> collaborators = new ArrayList<>();
    private Status status;
    private Pricing pricing;
    private long createdAt;
    private long updatedAt;

    public static Project of (
            String title,
            String description,
            String owner,
            Pricing pricing
    ) {
        long now = System.currentTimeMillis();
        Project p = new Project();
        p.title = title;
        p.description = description;
        p.owner = owner;
        p.collaborators = Collections.emptyList();
        p.status = Status.ACTIVE;
        p.pricing = pricing;
        p.createdAt = now;
        p.updatedAt = now;
        return p;
    }
}
