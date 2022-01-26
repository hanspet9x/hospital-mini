package org.oze.hospital.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DeleteRequest {
    private String startDate;
    private String endDate;
    private List<Integer> patientIds = new ArrayList<>();
}
