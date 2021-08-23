package com.epam.esm.model.dto;

import com.epam.esm.model.Tag;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificateDto extends RepresentationModel<GiftCertificateDto> {
    private int id;
    private String description;
    @NotNull
    @Min(value = 1, message = "minimal price - 1")
    @Max(value = 10000, message = "maximum price - 10000")
    private double price;
    @NotNull
    @Min(value = 1, message = "minimal duration - 1")
    @Max(value = 100, message = "maximum duration - 100")
    private int duration;
    @NotNull
    @Size(min = 4, max = 100, message = "length should be between 4 and 100")
    private String name;
    private List<Tag> tags;
}
